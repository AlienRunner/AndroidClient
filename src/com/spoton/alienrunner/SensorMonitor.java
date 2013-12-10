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
import android.widget.TextView;

public class SensorMonitor implements SensorEventListener{
	Sensor accelerometer;
	SensorManager sm;
	boolean isChanging = false;
	long paus;
	Context context;
	Boolean didUse;
//	ColorDrawable cd;
//	TextView acceleration;
	
	public SensorMonitor(Context context, Boolean didUse){
		this.didUse = didUse;
		this.context = context;
//		this.cd =cd;
		sm=(SensorManager)context.getSystemService(Activity.SENSOR_SERVICE);
		accelerometer=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		accelerometer=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		//		acceleration=(TextView)((Activity)context).findViewById(R.id.acceleration);

	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@SuppressLint("NewApi")
	@Override
	public void onSensorChanged(SensorEvent event) {
//		acceleration.setText("X: "+event.values[0]+
//				"\nY: "+event.values[1]+
//				"\nZ: "+event.values[2]);
		if(!didUse && event.values[2] > 16 && !isChanging){
//			cd.setColor(Color.GREEN);
			didUse = true;
			isChanging = true;
			Log.d("hi","byt från röd till grön");
			paus = System.currentTimeMillis();
		}
		if(isChanging && event.values[2] < 11 && System.currentTimeMillis() - paus > 800){
			isChanging = false;
			Log.d("hi","inne i is changing");
			didUse=false;
		}
			
		if(!didUse && event.values[2] > 16 && !isChanging){
//			cd.setColor(Color.RED);
			isChanging = true;
			didUse = true;
			Log.d("hi","byt från grön till röd");
			paus = System.currentTimeMillis();
		}
	}
}

