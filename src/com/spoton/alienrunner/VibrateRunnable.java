package com.spoton.alienrunner;

import android.content.Context;
import android.os.Vibrator;

public class VibrateRunnable implements Runnable {
 private Context context;
  
 public VibrateRunnable(Context context){
  this.context = context;
 }
 @Override
 public void run() {
  Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
  vibrator.vibrate(100);
 }

}

