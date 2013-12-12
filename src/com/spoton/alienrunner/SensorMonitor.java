package com.spoton.alienrunner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

public class SensorMonitor implements SensorEventListener {
	private Sensor accelerometer;
	private SensorManager sm;
	private boolean isChanging = false;
	private long paus;
	private long lastTime;
	private Context context;
	private MapHandler mh;
	private User user;
	private Toast customToast;
	private int dist = 0;

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
	
		// IF ALIEN
		if (user.isAlien()) {
			// SENSOR IS MOVING ENOUGH (red -> green)
			if (event.values[2] > 19 && !isChanging) {
				this.dist = (int) mh.getClosestMarineDistance();
				if (mh.getClosestMarine() != null) {
					User target = mh.getClosestMarine();
					isChanging = true;
					if (target != null) {
						if (dist < 20) {
							customToast = Toast.makeText(context,
									"YEAH! YOU JUST ATE A MARINE!",
									Toast.LENGTH_LONG);
							customToast.setGravity(Gravity.CENTER
									| Gravity.CENTER, 0, 0);
							customToast.show();
						} else {
							customToast = Toast.makeText(context,
									"Try to get closer to the Marine! \n\n                 "
											+ dist + " m left.",
									Toast.LENGTH_SHORT);
							customToast.setGravity(Gravity.CENTER
									| Gravity.CENTER, 0, 0);
							customToast.show();
						}
						mh.map.animateCamera(CameraUpdateFactory
								.newLatLng(new LatLng(target.getxCoord(),
										target.getyCoord())), 3000, null);
						paus = System.currentTimeMillis();
					}
				} else {
					customToast = Toast.makeText(context,
							"THERE IS NO MARINE!!!", Toast.LENGTH_SHORT);
					customToast.setGravity(Gravity.CENTER | Gravity.CENTER, 0,
							0);
					customToast.show();
				}
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
			// IF MARINE
		} else {
			if (event.values[2] > 19 && !isChanging) {
				this.dist = (int) mh.getClosestEvacDistance();
				isChanging = true;
				User target = mh.getClosestEvac();
				if (target != null) {

					if (dist < 20) {
						customToast = Toast.makeText(context,
								"YEAH! YOU JUST GOT EVACUATED!",
								Toast.LENGTH_LONG);
						customToast.setGravity(Gravity.CENTER | Gravity.CENTER,
								0, 0);
						customToast.show();
					} else {
						customToast = Toast.makeText(context,
								"GET TO THE CHOPPA!!! \n\n       " + dist
										+ " m left.", Toast.LENGTH_SHORT);
						customToast.setGravity(Gravity.CENTER | Gravity.CENTER,
								0, 0);
						customToast.show();
					}
					mh.map.animateCamera(CameraUpdateFactory
							.newLatLng(new LatLng(target.getxCoord(), target
									.getyCoord())), 3000, null);
					paus = System.currentTimeMillis();
				} else {
					customToast = Toast.makeText(context,
							"THERE IS NO CHOPPA!!!", Toast.LENGTH_SHORT);
					customToast.setGravity(Gravity.CENTER | Gravity.CENTER, 0,
							0);
					customToast.show();
				}
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
//		vibrate();
	}

	private void vibrate() {
		Vibrator vib = (Vibrator) context
				.getSystemService(Context.VIBRATOR_SERVICE);
		if ( dist != 0 && dist < 100) {
			if (System.currentTimeMillis() - this.lastTime > 2000) {
				customToast = Toast.makeText(context,
						"YEAH! YOU GETTING CLOSE!", Toast.LENGTH_LONG);
				customToast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
				customToast.show();
				vib.vibrate(500);
				this.lastTime = System.currentTimeMillis();
			}
		} else {
			if (System.currentTimeMillis() - this.lastTime > 9000) {
				customToast = Toast.makeText(context,
						"YEAH! YOU STILL FAR AWAY!", Toast.LENGTH_LONG);
				customToast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
				customToast.show();
				vib.vibrate(1000);
				this.lastTime = System.currentTimeMillis();
			}
		}
	}
}
