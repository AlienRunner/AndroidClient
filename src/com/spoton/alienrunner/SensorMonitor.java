package com.spoton.alienrunner;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

public class SensorMonitor implements SensorEventListener {
	private Sensor accelerometer;
	private SensorManager sm;
	private boolean isChanging = false;
	private long paus;
	private Context context;
	private MapHandler mh;
	private User user;
	private Toast customToast;

	public SensorMonitor(MapHandler mh, Context context, User user) {
		this.mh = mh;
		this.context = context;
		this.user = user;
		sm = (SensorManager) context.getSystemService(Activity.SENSOR_SERVICE);
		accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sm.registerListener(this, accelerometer,
				SensorManager.SENSOR_DELAY_NORMAL);
		accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		this.customToast = new Toast(context);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@SuppressLint("NewApi")
	@Override
	public void onSensorChanged(SensorEvent event) {
		if (user.isAlien()) {
			// SENSOR IS MOVING ENOUGH (red -> green)
			if (event.values[2] > 19 && !isChanging) {
				int dist = (int) mh.getLeastDistance();
				isChanging = true;
				if (dist < 20) {
					customToast = Toast.makeText(context,
							"YEAH! YOU JUST ATE A MARINE!",
							Toast.LENGTH_SHORT);
					customToast.setGravity(Gravity.CENTER | Gravity.CENTER, 0,
							0);
					customToast.show();
				} else {
					customToast = Toast.makeText(context,
							"Try to get closer to the Marine! \n           Still  " + dist + " m left.",
							Toast.LENGTH_SHORT);
					customToast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0,
							0);
					customToast.show();
				}
				paus = System.currentTimeMillis();
			}
			// SENSOR IS STILL MOVING BUT SHOULDN'T DO ANYTHING (changing)
			if (isChanging && event.values[2] < 11
					&& System.currentTimeMillis() - paus > 800) {
				isChanging = false;
			}
			// SENSOR IS NOT MOVING ENOUGH (red->green)
			if (event.values[2] > 19 && !isChanging) {
				isChanging = true;
				paus = System.currentTimeMillis();
			}
		}
	}
}
