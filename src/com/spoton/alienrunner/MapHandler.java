package com.spoton.alienrunner;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.content.Context;
import android.location.Location;
import android.os.StrictMode;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapHandler {
	private int marinesIcon, alienIcon, alienUserIcon, marinesUserIcon, userIcon;
	private Marker myMarker;
	private GoogleMap map;
	public User myUser;
	private ArrayList<User> oponentList;
	public ArrayList<User> updatedOponentList;
	private HashMap<User, Marker> markersList;
	public static String SERVER_IP = "213.67.75.254";
	public Socket socket;
	private ClientSend cs;
	private ClientListener cl;
	private Context context;

	MapHandler(GoogleMap theMap, User myUser, Context context) {
		System.out.println("___THIS IS THE MAPHANDLER CONSTRUCTOR BEGINNING:____");
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		this.map = theMap;
		this.myUser = myUser;
		markersList = new HashMap<User, Marker>();
		oponentList = new ArrayList<User>();
		updatedOponentList = new ArrayList<User>();
		this.context = context;
        marinesIcon = R.drawable.marines_point;
        alienIcon = R.drawable.alien_point;
        alienUserIcon = R.drawable.broodmother_point;
		marinesUserIcon = R.drawable.arnold_point;
		System.out.println("___THIS IS THE MAPHANDLER CONSTRUCOT AND THE CURR RACE:____" + myUser.getRace());
		if (myUser.getRace() == "Alien") {
			System.out.println("___Now ALIEN:____" + myUser.getRace());
			this.userIcon = alienUserIcon;
		}else{
			System.out.println("___Now ARNOLD:____" + myUser.getRace());
			this.userIcon = marinesUserIcon;
		}
		
		System.out.println("Doing update Players");
		try {
			if (socket == null) {
				socket = new Socket(SERVER_IP, 21101);
				System.out.println("Socket created and connected!");
			}else{
				System.out.println("Socket not null!");
			}
			if(socket.isConnected()){
				System.out.println("Socket connected!");
				System.out.println("Creates listener!");
				this.cl = new ClientListener(socket, this, context);
				System.out.println("Creates sender!");
				this.cs = new ClientSend(socket, myUser);
				System.out.println("Starting client threads!");
				cl.start();
				cs.start();
			}else{
				System.out.println("Socket not ready!");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			System.out.println("___Now setting icontype:____" + this.userIcon);
			myMarker = map.addMarker(new MarkerOptions()
					.position(new LatLng(location.getLatitude(), location.getLongitude()))
					.title("You are here")
					.snippet("Your last recorded location")
					.icon(BitmapDescriptorFactory.fromResource(this.userIcon))
					);
		}
		System.out.println("___THIS IS THE CURR RACE:____" + myUser.getRace());
		if (myUser.getRace().equals( "Alien")) {
			myMarker.setIcon(BitmapDescriptorFactory.fromResource( alienUserIcon));
		}else{
			myMarker.setIcon(BitmapDescriptorFactory.fromResource(marinesUserIcon));
		}
		// Make camera focus on my new position
		map.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location
				.getLatitude(), location.getLongitude())), 3000, null);
		updatePlayers();
	}

	// Fetch Updates from server and update all players and markers;
	@SuppressWarnings("unchecked")
	public void updatePlayers() {

		// Collects all players from database at server.
//		updatedOponentList = cs.setAndFetch(myUser);
		Log.d("updatedOponentList", "Got updatedOponentList:"
				+ updatedOponentList);
		Log.d("updatedOponentList SIZE",
				String.valueOf(updatedOponentList.size()));
		if (updatedOponentList != null) {
			System.out.println("inne i if satts");
			// 1. Remove players from map and from hashmap.
			ArrayList<User> copyOponentList = (ArrayList<User>) oponentList.clone();
			Log.d("Size of copyOponentlist before remove",
					String.valueOf(copyOponentList.size()));

			// JUST A COPY
			ArrayList<User> temp1 = (ArrayList<User>) updatedOponentList.clone();

			copyOponentList.removeAll(temp1);
			Log.d("Size of copyOponentlist after remove",
					String.valueOf(copyOponentList.size()));
			// NOW copyOponentList only contains users that have left the game!!
			if (copyOponentList.size() > 0) {
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
			// SAVES OBJECT THAT EXIST IN BOTH LISTS
			// TEMP2
			ArrayList<User> temp2 = (ArrayList<User>) oponentList.clone();
			Log.d("SIZE BEFORE RETAIN ALL copyUpdatedOponnentList",
					String.valueOf(copyUpdatedtOponentList.size()));
			copyUpdatedtOponentList.retainAll(temp2);
			Log.d("SIZE AFTER RETAIN ALL copyUpdatedOponnentList",
					String.valueOf(copyUpdatedtOponentList.size()));

			Iterator<User> it = copyUpdatedtOponentList.iterator();
			while (it.hasNext()) {
				User aUser = it.next();
				Log.d("UPDATING MARKER", aUser.getUserId());
				Marker aMarker = markersList.get(aUser);
				LatLng coords = new LatLng(aUser.getxCoord(), aUser.getyCoord());
				aMarker.setPosition(coords);
				int race = checkIconType(aUser);
				aMarker.setIcon(BitmapDescriptorFactory.fromResource(race));
			}

			// 3. Add new players to Gmap and HashMap
			Log.d("NUMBER3", "NUMBER3");

			Log.d("Size OF UpdatedtOponentList BEFORE remove ALL",
					String.valueOf(updatedOponentList.size()));

			copyUpdatedtOponentList = (ArrayList<User>) updatedOponentList.clone();
			Log.d("Size OF copyUpdatedtOponentList BEFORE remove ALL",
					String.valueOf(copyUpdatedtOponentList.size()));

			copyUpdatedtOponentList.removeAll(oponentList);

			Log.d("Size OF copyUpdatedtOponentList After remove ALL",
					String.valueOf(copyUpdatedtOponentList.size()));

			Iterator<User> it2 = copyUpdatedtOponentList.iterator();

			while (it2.hasNext()) {
				// Display new Marker on Gmap

				User aUser = it2.next();
				Log.d("ADDS NEW PLATER TO MAP", aUser.getUserId());
				int race = checkIconType(aUser);
				Marker newMarker = map
						.addMarker(new MarkerOptions()
								.position(
										new LatLng(aUser.getxCoord(), aUser
												.getyCoord()))
								.title("User:" + aUser.getUserId())
								// TODO Get icon working(Arnold?)
								.snippet(aUser.getRace()));

				newMarker.setIcon(BitmapDescriptorFactory.fromResource(race));
				// Add marker to hashmap
				markersList.put(aUser, newMarker);
			}

			// Make list update;
			oponentList = updatedOponentList;

		} else {
			System.out.println("No List was sent to device");
		}

	}
	
	private int checkIconType(User user){
		int icon;
		if (user.getRace().equals("Alien"))
		{
			icon = alienIcon;
		}else{
			icon = marinesIcon;
		}
		return icon;
	}
	
	public User getClosestUser() {
		Iterator<User> iter = oponentList.iterator();
		double dist = Double.MAX_VALUE;
		double x1 = myUser.getxCoord();
		double y1 = myUser.getyCoord();
		User listUser = null;
		while (iter.hasNext()) {
			User tempUser = iter.next();
			double x2 = tempUser.getxCoord();
			double y2 = tempUser.getyCoord();
			double tempDist = getDistance(x1, y1, x2, y2);
			if (tempDist < dist) {
				dist = tempDist;
				listUser = tempUser;
			}
		}
		return listUser;
	}

	public double getLeastDistance() {
		Iterator<User> iter = oponentList.iterator();
		double dist = Double.MAX_VALUE;
		double x1 = myUser.getxCoord();
		double y1 = myUser.getyCoord();
		while (iter.hasNext()) {
			User tempUser = iter.next();
			double x2 = tempUser.getxCoord();
			double y2 = tempUser.getyCoord();
			double tempDist = getDistance(x1, y1, x2, y2);
			if (tempDist < dist) {
				dist = tempDist;
			}
		}
		return dist;
	}

	private double getDistance(double x1, double y1, double x2, double y2) {
		double theDistance = (Math.sin(Math.toRadians(x1))
				* Math.sin(Math.toRadians(x2)) + Math.cos(Math.toRadians(x1))
				* Math.cos(Math.toRadians(x2))
				* Math.cos(Math.toRadians(y1 - y2)));
		return (Math.toDegrees(Math.acos(theDistance)) * 69.09 * 1.6093);
		// return (Math.toDegrees(Math.acos(theDistance)) * 69.09 * 1.6093);
	}
}
