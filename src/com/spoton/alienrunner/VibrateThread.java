package com.spoton.alienrunner;

import android.content.Context;
import android.os.Handler;
import android.os.Vibrator;

public class VibrateThread extends Thread{
	Vibrator vib;
	Context context;
	final int MARKER_UPDATE_INTERVAL = 2000;
	Handler handler;
	
	public VibrateThread(Context context, Handler handler){
		this.context = context;
		this.handler = handler;
		vib = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
	}
	public void run(){
		vib.vibrate(1000);
		handler.postDelayed(this, MARKER_UPDATE_INTERVAL);
	}

}
