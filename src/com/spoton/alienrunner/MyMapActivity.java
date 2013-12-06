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
	private Context context;
	ClientSender cs;
	ArrayList<User> userList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
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
		//TABORT
		name = "Johan";
		System.out.println("________NAME: " + name);	
		User myUser = new User("Johan", 99, 88, "Alien");

		userList = cs.setAndFetch(myUser);
		System.out.println("________USERLIST: " + userList);
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
			}
		}
		
		MapHandler handler = new MapHandler(theMap, cs, myUser, context);		
	}


	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
