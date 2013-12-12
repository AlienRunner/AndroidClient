package com.spoton.alienrunner;

import java.io.IOException;
import java.net.Socket;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;

public class MyMapActivity extends FragmentActivity implements LocationListener {
	private GoogleMap theMap;
	private LocationManager locMan;
	public MapHandler mapHandler;
	private Context context;
	public User myUser;
	public static String SERVER_IP = "213.67.75.254";
	public Socket socket;
	private ClientSend cs;
	final int MARKER_UPDATE_INTERVAL = 2000;
	final Handler handler = new Handler(Looper.getMainLooper());
	Marker marker;
	SensorMonitor sm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_map);
		context = this.getApplicationContext();

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		Intent i = getIntent();
		String name = i.getStringExtra("name");
		String race = i.getStringExtra("race");
		locMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Location lastLoc = locMan
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		double lat = lastLoc.getLatitude();
		double lng = lastLoc.getLongitude();

		if (theMap == null) {
			FragmentManager fmanager = getSupportFragmentManager();
			Fragment fragment = fmanager.findFragmentById(R.id.map);
			SupportMapFragment supportmapfragment = (SupportMapFragment) fragment;
			theMap = supportmapfragment.getMap();
			if (theMap != null) {
				System.out.println("KARTAN €R LADDAD!");
				theMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
				theMap.setMyLocationEnabled(true);
				theMap.setBuildingsEnabled(true);
			}
		}

		this.myUser = new User(name, lat, lng, race);
		this.mapHandler = new MapHandler(theMap, myUser, context);
		mapHandler.gpsUpdate(lastLoc);
		mapHandler.centerCamera();

		System.out.println("Creating socket...");
		try {
			if (socket == null) {
				socket = new Socket(SERVER_IP, 21101);
				System.out.println("Socket created and connected!");
			}
			if (socket.isConnected()) {

				System.out.println("Starting listener and sender threads...");
				this.cs = new ClientSend(socket, myUser);
				ClientListener updateMarker = new ClientListener(theMap,
						handler, socket, mapHandler);
				VibrateThread vt = new VibrateThread(context, handler,
						mapHandler);
				vt.start();
				// handler.postDelayed(vt, MARKER_UPDATE_INTERVAL);
				handler.postDelayed(updateMarker, MARKER_UPDATE_INTERVAL);
				cs.start();
			} else {
				System.out.println("Socket not ready!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		Toast customToast = new Toast(context);
		if (this.myUser.isAlien()) {
			// customToast = Toast.makeText(context,
			// "Try to catch a marine!\n\n      Wip the phone!",
			// Toast.LENGTH_LONG);
			// customToast =
			// Toast.makeText(context,"Try to catch a marine!\n\n      Wip the phone!",
			// Toast.LENGTH_LONG);
			customToast = Toast.makeText(context, "\nTry to catch a marine!\n",
					Toast.LENGTH_LONG);
			customToast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
			customToast.show();
		} else {
			customToast = Toast.makeText(context, "\nGET TO THE CHOPPA!!\n",
					Toast.LENGTH_LONG);
			customToast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
			customToast.show();
			MediaPlayer mp = MediaPlayer.create(context, R.raw.choppa);
			mp.start();
		}
		customToast = Toast.makeText(context, "\nWip the phone!\n",
				Toast.LENGTH_LONG);
		customToast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
		customToast.show();
		this.sm = new SensorMonitor(mapHandler, context, myUser);
		MediaPlayerThread mt = new MediaPlayerThread(context, handler, mapHandler);
		mt.start();
	}

	@Override
	protected void onResume() {
		super.onResume();
		locMan.requestLocationUpdates(locMan.GPS_PROVIDER, 3000, 0, this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		locMan.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location location) {
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

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			System.out.println("!!!!!!!!!!!!CLOSING ACTIVITY!!!!!!!!");
			myUser = null;
			theMap.clear();
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}
