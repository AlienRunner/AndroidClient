package com.spoton.alienrunner;

import android.location.Location;
import android.os.Bundle;

public class alienLocationListener implements android.location.LocationListener{
	private MapHandler handler;
		
	public alienLocationListener(MapHandler mapHandler){
		this.handler = mapHandler;
	}
	@Override
	public void onLocationChanged(Location location) {
			handler.gpsUpdate(location);
	}
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

}
