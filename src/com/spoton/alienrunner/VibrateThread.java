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
		vib = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
	}

	public void run() {
		double distance = mh.getLeastDistance();
		try {
			if (distance > 200) {
				Thread.sleep(8000);
			} else if (distance > 150) {
				Thread.sleep(4000);
			} else {
				Thread.sleep((long) distance * 26);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		vib.vibrate(500);
		handler.postDelayed(this, MARKER_UPDATE_INTERVAL);
	}

}
