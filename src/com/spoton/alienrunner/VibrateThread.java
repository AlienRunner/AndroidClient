package com.spoton.alienrunner;

import android.content.Context;
import android.os.Handler;
import android.os.Vibrator;

public class VibrateThread extends Thread {
	Vibrator vib;
	Context context;
	final int MARKER_UPDATE_INTERVAL = 2000;
	Handler handler;
	private MapHandler mh;

	public VibrateThread(Context context, Handler handler, MapHandler mg) {
		this.context = context;
		this.handler = handler;
		this.mh = mg;
	}

	public void run() {
		while(true){
			
		double distance = mh.getLeastDistance();
		try {
			if (distance > 200) {
				Thread.sleep(8000);
			} else if (distance > 150) {
				Thread.sleep(4000);
			} else if (distance > 0){
				Thread.sleep((long) distance * 26);
			}else{
				Thread.sleep(2000);
			}
				
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		handler.postAtFrontOfQueue(new VibrateRunnable(context));
		}
	
	
	}

}