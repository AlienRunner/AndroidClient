package com.spoton.alienrunner;
 
 import java.util.ArrayList;
 import java.util.Iterator;
 
 import com.google.android.gms.maps.model.LatLng;
 
 import android.os.AsyncTask;
 
 public class IfCatchTask extends AsyncTask<ArrayList<User>, Void, Boolean> {
 
   @Override
   protected Boolean doInBackground(ArrayList<User>... params) {
     
     ArrayList<User> list = params[0];
     User myUser = list.get(0);
     list.remove(0);
     
     Iterator<User> it = list.iterator();
     float temp=100000000;
     float distance;
     
     while(it.hasNext()){
       User opponent = it.next();
       
     distance = calcDistance((float)myUser.getxCoord(),(float) myUser.getyCoord(),(float)opponent.getxCoord(),(float)opponent.getyCoord());
     if(distance <10){
       return true;
     }
       
     }
     
     return false;
     
     
     
   }
   
   public static int calcDistance(float latA, float longA, float latB, float longB) {
 
       double theDistance = (Math.sin(Math.toRadians(latA)) *
               Math.sin(Math.toRadians(latB)) +
               Math.cos(Math.toRadians(latA)) *
               Math.cos(Math.toRadians(latB)) *
               Math.cos(Math.toRadians(longA - longB)));
       
     return new Double((Math.toDegrees(Math.acos(theDistance))) * 
             69.09*1.6093).intValue();
 
 }
   
 }
 