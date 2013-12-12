package com.spoton.alienrunner;

import android.media.MediaPlayer;

public class MediaPlayerRunnable implements Runnable {
	MediaPlayer mp;
	
	public MediaPlayerRunnable(MediaPlayer mp){
		this.mp=mp;
	}

	@Override
	public void run() {
		mp.start();
	}

}
