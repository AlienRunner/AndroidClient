package com.spoton.alienrunner;

import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.location.Location;
import android.location.LocationManager;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;

public class MyMapActivity extends FragmentActivity {
	private int userIcon, alienIcon, foodIcon, drinkIcon, shopIcon, otherIcon;
	private GoogleMap theMap;
	private LocationManager locMan;
	private Marker userMarker;
	private Marker emilMarker;
	private Marker johanMarker;
	private Marker perMarker;
	private Marker jockeMarker;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_map);
		
		userIcon = R.drawable.arnold_point;
		alienIcon = R.drawable.alien_point;
		foodIcon = R.drawable.red_point;
		drinkIcon = R.drawable.blue_point;
		shopIcon = R.drawable.green_point;
		otherIcon = R.drawable.purple_point;
		
		if(theMap==null){
//		    //map not instantiated yet
			FragmentManager fmanager = getSupportFragmentManager();
			Fragment fragment = fmanager.findFragmentById(R.id.map);
	        SupportMapFragment supportmapfragment = (SupportMapFragment)fragment;
	        theMap = supportmapfragment.getMap();
//            theMap.addMarker(new MarkerOptions()
//            .position(new LatLng(32.1275701, 34.7983432))
//            .title("Hello world"));
			if(theMap != null){
				theMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
				updatePlaces();
			}
		}
	}

	private void updatePlaces(){
		//update location
		locMan = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		Location lastLoc = locMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		double lat = lastLoc.getLatitude();
		double lng = lastLoc.getLongitude();
		LatLng lastLatLng = new LatLng(lat, lng);
		
		// UserLocation
		if(userMarker!=null) userMarker.remove();
		userMarker = theMap.addMarker(new MarkerOptions()
		    .position(lastLatLng)
		    .title("You are here")
		    .icon(BitmapDescriptorFactory.fromResource(userIcon))
		    .snippet("Your last recorded location"));
		theMap.animateCamera(CameraUpdateFactory.newLatLng(lastLatLng), 3000, null);
		
		// EmilLocation
		if(emilMarker!=null) emilMarker.remove();
		emilMarker = theMap.addMarker(new MarkerOptions()
		    .position(new LatLng(55.715339,13.210391))
		    .title("Emil is here")
		    .icon(BitmapDescriptorFactory.fromResource(alienIcon))
		    .snippet("Emils location"));
		
		// JohanLocation
		if(johanMarker!=null) johanMarker.remove();
		johanMarker = theMap.addMarker(new MarkerOptions()
		    .position(new LatLng(55.712994,13.210584))
		    .title("Johan is here")
		    .icon(BitmapDescriptorFactory.fromResource(alienIcon))
		    .snippet("Johans location"));
		
		// PerLocation
		if(perMarker!=null) perMarker.remove();
		perMarker = theMap.addMarker(new MarkerOptions()
		    .position(new LatLng(55.715278,13.214339))
		    .title("Per is here")
		    .icon(BitmapDescriptorFactory.fromResource(alienIcon))
		    .snippet("Pers location"));
		
		//JockeLocation
		if(jockeMarker!=null) jockeMarker.remove();
		jockeMarker = theMap.addMarker(new MarkerOptions()
		    .position(new LatLng(55.713163,13.214897))
		    .title("Jocke is here")
		    .icon(BitmapDescriptorFactory.fromResource(alienIcon))
		    .snippet("Jockes location"));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
