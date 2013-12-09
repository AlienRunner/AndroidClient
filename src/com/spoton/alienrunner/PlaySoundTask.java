package com.spoton.alienrunner;

import java.io.IOException;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

public class PlaySoundTask extends AsyncTask<Void, Void, Boolean>{
	private MapHandler mapHandler;
	
	@Override
	protected Boolean doInBackground(Void... params) {
		//mapHandler = params[0];
		//double leastdistance = mapHandler.getLeastDistance();
		//double timeToSleep = timeToSleep(leastdistance);
		Log.d("INITIERAR", "SKAPAR TASK");
		
		android.os.SystemClock.sleep(3000);
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
	
	
	  protected void onPostExecute() {
//	  new PlaySoundTask().execute(mapHandler);	 
		  new PlaySoundTask().execute();	 
		  
	  }
	
	
	//Return a value in seconds
	private double timeToSleep(double distance){
		if(distance > 700){
			return 7000; 
		}else{
			return (distance*10);
		}
		
		
	}

	
}
