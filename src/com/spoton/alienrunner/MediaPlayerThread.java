package com.spoton.alienrunner;

import java.util.Random;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Handler;
import android.os.Vibrator;

public class MediaPlayerThread extends Thread implements OnCompletionListener{
	Vibrator vib;
	Context context;
	final int MARKER_UPDATE_INTERVAL = 2000;
	Handler handler;
	private MapHandler mh;
	private static MediaPlayer mp;

	public MediaPlayerThread(Context context, Handler handler, MapHandler mg) {
		this.context = context;
		this.handler = handler;
		this.mh = mg;
	}

	public void run() {
		mp = MediaPlayer.create(context, R.raw.tracker_active);
		mp.setOnCompletionListener(this);
		handler.post(new MediaPlayerRunnable(mp));
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		
		long time = (long) mh.getLeastDistance();
		
		if(time > 1000){
			time = 1000;
		}
		playSound(mp, time);
		
	}
	
	private void playSound(MediaPlayer mp, Long time){
		handler.postDelayed(new MediaPlayerRunnable(mp), time*10);
	}

}
