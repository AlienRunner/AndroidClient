package com.spoton.alienrunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapHandler {

	private Marker myMarker;
	private GoogleMap map;
	private LocationManager locMan;
	private alienLocationListener locationListener;
	private Location currentLocation;
	private ClientSender cs;
	private User myUser;
	private ArrayList<User> oponentList;
	private ArrayList<User> updatedOponentList;
	private HashMap<User, Marker> markersList;

	MapHandler(GoogleMap theMap, ClientSender clientSender, User myuser) {

		this.map = theMap;
		this.cs = clientSender;
		this.myUser = myuser;
		markersList = new HashMap<User, Marker>();
		oponentList = new ArrayList<User>();
		updatedOponentList = new ArrayList<User>();

	}

	// Is run by the listener eachtime a gps update is made.
	public void gpsUpdate(Location location) {
		System.out.println("Latitude: " + location.getLatitude());
		System.out.println("Longitude " + location.getLongitude());
		// Fetch gps cordinates updating myUser object
		myUser.setxCoord(location.getLatitude());
		myUser.setyCoord(location.getLongitude());

		// If myMarker exist remove from map and Update the map with his new
		// myMarker containing his new position;
		if (myMarker != null) {
			myMarker.setPosition(new LatLng(location.getLatitude(), location
					.getLongitude()));
		} else {
			myMarker = map.addMarker(new MarkerOptions()
			.position(
					new LatLng(location.getLatitude(), location
							.getLongitude())).title("You are here")
							// TODO Get icon working
							.snippet("Your last recorded location"));
		}
		// Make camera focus on my new position
		map.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location
				.getLatitude(), location.getLongitude())), 3000, null);

		updatePlayers();
	}

	// Fetch Updates from server and update all players and markers;
	public void updatePlayers() {

		// Collects all players from database at server.
		updatedOponentList = cs.setAndFetch(myUser);
		Log.d("updatedOponentList", "Got updatedOponentList:" +updatedOponentList);
		Log.d("updatedOponentList SIZE", String.valueOf(updatedOponentList.size()));
		if (updatedOponentList != null) {
			System.out.println("inne i if satts");
			// 1. Remove players from map and from hashmap.
			ArrayList<User> copyOponentList = (ArrayList<User>) oponentList.clone();
			Log.d("Size of copyOponentlist before remove", String.valueOf(copyOponentList.size()));
			
			
			//JUST A COPY
			ArrayList<User> temp1 = (ArrayList<User>) updatedOponentList.clone();
			
			copyOponentList.removeAll(temp1);
			Log.d("Size of copyOponentlist after remove", String.valueOf(copyOponentList.size()));
			// NOW copyOponentList only contains users that have left the game!!
			if(copyOponentList.size() > 0){
			Iterator<User> it = copyOponentList.iterator();
			while (it.hasNext()) {
				System.out.println("ITTERARER");
				User aUser = it.next();
				Marker aMarker = markersList.get(aUser);
				aMarker.remove();
				markersList.remove(aUser);
			
			}
			}
			// 2. Update players position that all ready exist.
			System.out.println("NUMBER 2 UPDATE PLAYERS");
			ArrayList<User> copyUpdatedtOponentList = (ArrayList<User>) updatedOponentList.clone();
			//SAVES OBJECT THAT EXIST IN BOTH LISTS
			//TEMP2
			ArrayList<User> temp2 = (ArrayList<User>) oponentList.clone();
			Log.d("SIZE BEFORE RETAIN ALL copyUpdatedOponnentList", String.valueOf(copyUpdatedtOponentList.size()) );
			copyUpdatedtOponentList.retainAll(temp2);
			Log.d("SIZE AFTER RETAIN ALL copyUpdatedOponnentList", String.valueOf(copyUpdatedtOponentList.size()) );
			
			Iterator<User> it = copyUpdatedtOponentList.iterator();
			while (it.hasNext()) {
				User aUser = it.next();
				Log.d("UPDATING MARKER", aUser.getUserId());
				Marker aMarker = markersList.get(aUser);
				LatLng coords = new LatLng(aUser.getxCoord(), aUser.getyCoord());
				aMarker.setPosition(coords);
			}

			// 3. Add new players to Gmap and HashMap
			Log.d("NUMBER3", "NUMBER3");
			
			Log.d("Size OF UpdatedtOponentList BEFORE remove ALL" , String.valueOf(updatedOponentList.size()));
			
			copyUpdatedtOponentList = (ArrayList<User>) updatedOponentList.clone();
			Log.d("Size OF copyUpdatedtOponentList BEFORE remove ALL" , String.valueOf(copyUpdatedtOponentList.size()));
			
			copyUpdatedtOponentList.removeAll(oponentList);
			
			Log.d("Size OF copyUpdatedtOponentList After remove ALL" , String.valueOf(copyUpdatedtOponentList.size()));
				
			Iterator<User> it2 = copyUpdatedtOponentList.iterator();
			
			while (it2.hasNext()) {
				// Display new Marker on Gmap
				
				User aUser = it2.next();
				Log.d("ADDS NEW PLATER TO MAP", aUser.getUserId());
				Marker newMarker = map
						.addMarker(new MarkerOptions()
						.position(
								new LatLng(aUser.getxCoord(), aUser
										.getyCoord()))
										.title("User:" + aUser.getUserId())
										// TODO Get icon working(Arnold?)
										.snippet(aUser.getRace()));

				// Add marker to hashmap
				markersList.put(aUser, newMarker);
			}

			// Make list update;
			oponentList = updatedOponentList;
			

		} else {
			System.out.println("No List was sent to device");
		}

	}

}
