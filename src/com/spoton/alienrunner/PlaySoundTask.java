package com.spoton.alienrunner;

import java.io.IOException;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v4.content.AsyncTaskLoader;

public class PlaySoundTask extends AsyncTask<ClientSender, Void, Boolean>{


	@Override
	protected Boolean doInBackground(ClientSender... params) {
		ClientSender cs = params[0];
		String distance = cs.getLeastDistance();
		int timeToSleep = timeToSleep(distance);
		try {
			Thread.sleep(timeToSleep);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MediaPlayer mp = new MediaPlayer();
		try {
			mp.setDataSource("R.raw.tracker_active.wav");
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mp.start();
		return true;
	}
	
	//Return a value in seconds
	private int timeToSleep(String distance){
		if(Integer.getInteger(distance) > 700){
			return 7000; 
		}else{
			return Integer.parseInt(distance)*10;
		}
		
		
	}

	
}
