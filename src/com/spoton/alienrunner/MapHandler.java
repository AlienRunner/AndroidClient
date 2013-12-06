package com.spoton.alienrunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

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
	private Location currentLocation;
	private ClientSender cs;
	private User myUser;
	private ArrayList<User> oponentList;
	private HashMap<User, Marker> markersList;
	private Context context;

	MapHandler(GoogleMap theMap, ClientSender clientSender, User myuser, Context cntxt) {
		this.context = cntxt;
		this.map = theMap;
		this.cs = clientSender;
		this.myUser = myuser;
		System.out.println("Trying to intiatate a listner");
		markersList = new HashMap<User, Marker>(); 
		// Activates an listener that runs each time a new gps location is
		// provided
		System.out.println("LocationListener Initaiated");
		GPSTracker tracker = new GPSTracker(context, this);
		gpsUpdate(tracker.getLocation());
		
	}

	// Is run by the listener each time a gps update is made.
	public void gpsUpdate(Location location) {
		System.out.println("Latitude: " + location.getLatitude());
		System.out.println("Longitude " + location.getLongitude());
		// Fetch gps cordinates updating myUser object
		myUser.setxCoord(location.getLatitude());
		myUser.setyCoord(location.getLongitude());
		
		
		System.out.println("OBJECT_myUser:" +myUser);
		System.out.println("OBJECT_map:" +map);
		System.out.println("OBJECT_cs:" +cs);
		
		// If myMarker exist remove from map and Update the map with his new
		// myMarker containing his new position;
		if (myMarker != null) {
			System.out.println("ENTERING myMarker !=Null");
			myMarker.setPosition(new LatLng(location.getLatitude(), location
					.getLongitude()));
		} else {
			System.out.println("ENTERING myMarker ELSE");
			myMarker = map.addMarker(new MarkerOptions()
			.position(
					new LatLng(location.getLatitude(), location
							.getLongitude())).title("You are here")
							// TODO Get icon working
							.snippet("Your last recorded location"));
		}
		// Make camera focus on my new position
		System.out.println("Animating CAMERA");
		map.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location
				.getLatitude(), location.getLongitude())), 3000, null);
		System.out.println("DOING UPDATE PLAYERS");
		updatePlayers();
	}

	// Fetch Updates from server and update all players and markers;
	public void updatePlayers() {

		// Collects all players from database at server.
		ArrayList<User> updatedOponentList = cs.setAndFetch(myUser);
		if (updatedOponentList != null) {

			// 1. Remove players from map and from hashmap.
			ArrayList<User> copyOponentList = oponentList;
			copyOponentList.removeAll(updatedOponentList);
			Iterator<User> it = copyOponentList.iterator();
			while (it.hasNext()) {
				User aUser = it.next();
				Marker aMarker = markersList.get(aUser);
				aMarker.remove();
				markersList.remove(aUser);
			}

			// 2. Update players position that all ready exist.
			ArrayList<User> copyUpdatedtOponentList = updatedOponentList;
			copyUpdatedtOponentList.retainAll(oponentList);

			it = copyUpdatedtOponentList.iterator();
			while (it.hasNext()) {
				User aUser = it.next();
				Marker aMarker = markersList.get(aUser);

				LatLng coords = new LatLng(aUser.getxCoord(), aUser.getyCoord());
				aMarker.setPosition(coords);
			}

			// 3. Add new players to Gmap and HashMap
			copyUpdatedtOponentList = updatedOponentList;
			copyUpdatedtOponentList.removeAll(oponentList);
			it = copyUpdatedtOponentList.iterator();
			while (it.hasNext()) {
				// Display new Marker on Gmap
				User aUser = it.next();
				Marker newMarker = map
						.addMarker(new MarkerOptions()
						.position(
								new LatLng(aUser.getxCoord(), aUser
										.getyCoord()))
										.title("User:" + aUser.getUserId())
										// TODO Get icon working(Arnold?)
										.snippet("A Snippet information"));

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
