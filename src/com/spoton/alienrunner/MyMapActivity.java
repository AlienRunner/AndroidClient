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
import android.location.Location;
import android.location.LocationManager;
import android.content.Context;
import android.content.Intent;
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
	private User myUser;
	private Context context;
	private LocationManager manager;
	ClientSender cs;
	ArrayList<User> userList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		myUser = new User("Mitt anvNamn", 0, 0, "Alien");
		locMan = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_map);
		
		context = this.getApplicationContext();
        cs = new ClientSender(context);
		
		userIcon = R.drawable.arnold_point;
		alienIcon = R.drawable.alien_point;
		foodIcon = R.drawable.red_point;
		drinkIcon = R.drawable.blue_point;
		shopIcon = R.drawable.green_point;
		otherIcon = R.drawable.purple_point;
		Intent i = getIntent();
		String name = i.getStringExtra("name");
		System.out.println("________NAME: " + name);
		User myUser = new User("Johan", 99, 88, "Alien");
		userList = cs.setAndFetch(myUser);
		System.out.println("________USERLIST: " + userList);
		System.out.println("Creating MapHandler");
		manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		MapHandler handler = new MapHandler(theMap, cs, myUser);
		alienLocationListener listener = new alienLocationListener(handler);
		manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
		
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
