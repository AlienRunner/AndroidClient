package com.spoton.alienrunner;

import java.util.ArrayList;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.Menu;
import android.widget.Toast;

public class MyMapActivity extends FragmentActivity implements LocationListener {
	private GoogleMap theMap;
	private LocationManager locMan;
	private MapHandler mapHandler;
	private ClientSender cs;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_map);
		context = this.getApplicationContext();

		Intent i = getIntent();
		String name = i.getStringExtra("name");
		System.out.println("________NAME: " + name);
		String race = i.getStringExtra("race");
		System.out.println("________Race: " + race);
		locMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Location lastLoc = locMan
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		double lat = lastLoc.getLatitude();
		double lng = lastLoc.getLongitude();
		User myUser = new User(name, lat, lng, race);

		if (theMap == null) {
			FragmentManager fmanager = getSupportFragmentManager();
			Fragment fragment = fmanager.findFragmentById(R.id.map);
			SupportMapFragment supportmapfragment = (SupportMapFragment) fragment;
			theMap = supportmapfragment.getMap();
		}else{
			System.out.println("KARTAN €R LADDAD!");
			theMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		}
		
		cs = new ClientSender(context);
		mapHandler = new MapHandler(theMap, cs, myUser);
		System.out.println("Doing update Players");
		mapHandler.gpsUpdate(lastLoc);
	}

	@Override
	protected void onResume() {
		super.onResume();
		System.out.println("FLIPPED THAT S");
		locMan.requestLocationUpdates(locMan.GPS_PROVIDER, 400, 1, this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		locMan.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location location) {
		System.out.println("LOCATION CHANGED, LONG:" + location.getLongitude()
				+ " LAT: " + location.getLatitude());

		Toast customToast = new Toast(getApplicationContext());
		customToast = Toast.makeText(getApplicationContext(),
				String.valueOf(location.getLongitude()), Toast.LENGTH_SHORT);
		customToast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
		customToast.show();
		mapHandler.gpsUpdate(location);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		Toast.makeText(this, "Enabled new provider " + provider,
				Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(this, "Disabled provider " + provider,
				Toast.LENGTH_SHORT).show();
	}
}
