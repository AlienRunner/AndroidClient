package com.spoton.alienrunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.content.Context;
import android.location.Location;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapHandler {
	private int marinesIcon, alienIcon, alienUserIcon, marinesUserIcon,
			userIcon;
	private Marker myMarker;
	private GoogleMap map;
	public User myUser;
	private ArrayList<User> oldList;
	private ArrayList<User> newList;
	private HashMap<User, Marker> markersList;
	private Context context;
	public String test;
	
	private Marker userMarker;
	private Marker emilMarker;
	private Marker johanMarker;
	private Marker perMarker;
	private Marker jockeMarker;
	
	int i;

	MapHandler(GoogleMap theMap, User myUser, Context context) {
		System.out
				.println("___THIS IS THE MAPHANDLER CONSTRUCTOR BEGINNING:____");
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		this.map = theMap;
		this.myUser = myUser;
		markersList = new HashMap<User, Marker>();
		oldList = new ArrayList<User>();
		newList = new ArrayList<User>();
		this.context = context;
		marinesIcon = R.drawable.marines_point;
		alienIcon = R.drawable.alien_point;
		alienUserIcon = R.drawable.broodmother_point;
		marinesUserIcon = R.drawable.arnold_point;
		System.out
				.println("___THIS IS THE MAPHANDLER CONSTRUCOT AND THE CURR RACE:____"
						+ myUser.getRace());
		if (myUser.getRace() == "Alien") {
			System.out.println("___Now ALIEN:____" + myUser.getRace());
			this.userIcon = alienUserIcon;
		} else {
			System.out.println("___Now ARNOLD:____" + myUser.getRace());
			this.userIcon = marinesUserIcon;
		}
		this.i = 0;
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
					.position(
							new LatLng(location.getLatitude(), location
									.getLongitude())).title("You are here")
					.snippet("Your last recorded location")
					.icon(BitmapDescriptorFactory.fromResource(this.userIcon)));
		}
		System.out.println("___THIS IS THE CURR RACE:____" + myUser.getRace());
		if (myUser.getRace().equals("Alien")) {
			myMarker.setIcon(BitmapDescriptorFactory
					.fromResource(alienUserIcon));
		} else {
			myMarker.setIcon(BitmapDescriptorFactory
					.fromResource(marinesUserIcon));
		}
		// Make camera focus on my new position
		map.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location
				.getLatitude(), location.getLongitude())), 3000, null);
		
		updatePlayers();
	}

	// Fetch Updates from server and update all players and markers;
	@SuppressWarnings("unchecked")
	public void updatePlayers() {
		System.out.println("__STARTING UPDATEOKAYERS___");
		Iterator<User> iter = newList.iterator();
		System.out.println("__PRINTS NEW LIST___");
		while(iter.hasNext()){
			System.out.println("NEW: " + iter.next().encrypt());
		}
		Iterator<User> iterOld = oldList.iterator();
		System.out.println("__PRINTS OLD LIST___");
		while(iterOld.hasNext()){
			System.out.println("NEW: " + iterOld.next().encrypt());
		}
		System.out.println("__PRINTING PLAYER LISTS DONE___");
		
		Toast customToast = new Toast(context);
		customToast = Toast.makeText(context, "UPDATED LIST",
				Toast.LENGTH_SHORT);
		customToast.setGravity(Gravity.CENTER | Gravity.CENTER, 0,
				0);
		customToast.show();
		
//		switch(this.i){
//		case 1:
//			// EmilLocation
			if(emilMarker!=null) emilMarker.remove();
			emilMarker = map.addMarker(new MarkerOptions()
			.position(new LatLng(55.715339,13.210391))
			.title("Emil is here")
			.icon(BitmapDescriptorFactory.fromResource(alienIcon))
			.snippet("Emils fake location"));			
//			i++;
//			break;
//		case 2:
//			// JohanLocation
//			if(johanMarker!=null) johanMarker.remove();
//			johanMarker = map.addMarker(new MarkerOptions()
//			    .position(new LatLng(55.712994,13.210584))
//			    .title("Johan is here")
//			    .icon(BitmapDescriptorFactory.fromResource(alienIcon))
//			    .snippet("Johans fakelocation"));
//			i++;
//			break;
//		case 3:
//			// PerLocation
//			if(perMarker!=null) perMarker.remove();
//			perMarker = map.addMarker(new MarkerOptions()
//			.position(new LatLng(55.715278,13.214339))
//			.title("Per is here")
//			.icon(BitmapDescriptorFactory.fromResource(alienIcon))
//			.snippet("Pers fake location"));
//			i++;
//			break;
//		case 4:
//			//JockeLocation
//			if(jockeMarker!=null) jockeMarker.remove();
//			jockeMarker = map.addMarker(new MarkerOptions()
//			.position(new LatLng(55.713163,13.214897))
//			.title("Jocke is here")
//			.icon(BitmapDescriptorFactory.fromResource(alienIcon))
//			.snippet("Jockes fake location"));
//			i++;
//			break;
//			}
		
		

		
		
		
		if (newList != null) {
//			// 1. Remove players from map and from hashmap.
//			ArrayList<User> copyOponentList = (ArrayList<User>) oldList.clone();
//
//			// JUST A COPY
//			ArrayList<User> temp1 = (ArrayList<User>) newList.clone();
//
//			copyOponentList.removeAll(temp1);
//
//			// NOW copyOponentList only contains users that have left the game!!
//			if (copyOponentList.size() > 0) {
//				Iterator<User> it = copyOponentList.iterator();
//				while (it.hasNext()) {
//					System.out.println("ITTERARER");
//					User aUser = it.next();
//					Marker aMarker = markersList.get(aUser);
//					aMarker.remove();
//					markersList.remove(aUser);
//				}
//			}
//			// 2. Update players position that all ready exist.
//			System.out.println("NUMBER 2 UPDATE PLAYERS");
//			ArrayList<User> copyUpdatedtOponentList = (ArrayList<User>) newList
//					.clone();
//			// SAVES OBJECT THAT EXIST IN BOTH LISTS
//			ArrayList<User> temp2 = (ArrayList<User>) oldList.clone();
//			copyUpdatedtOponentList.retainAll(temp2);
//			Iterator<User> it = copyUpdatedtOponentList.iterator();
//			while (it.hasNext()) {
//				User aUser = it.next();
//				Log.d("UPDATING MARKER", aUser.getUserId());
//				Marker aMarker = markersList.get(aUser);
//				LatLng coords = new LatLng(aUser.getxCoord(), aUser.getyCoord());
//				aMarker.setPosition(coords);
//				int race = checkIconType(aUser);
//				aMarker.setIcon(BitmapDescriptorFactory.fromResource(race));
//			}
//
//			// 3. Add new players to Gmap and HashMap
//			copyUpdatedtOponentList = (ArrayList<User>) newList.clone();
//			copyUpdatedtOponentList.removeAll(oldList);
//			Iterator<User> it2 = copyUpdatedtOponentList.iterator();
//
//			while (it2.hasNext()) {
//				// Display new Marker on Gmap
//				User aUser = it2.next();
//				System.out.println("Users copyOfnew:" + aUser.encrypt());
//				int race = checkIconType(aUser);
//				Marker newMarker = map.addMarker(new MarkerOptions()
//				.position(
//						new LatLng(aUser.getxCoord(), aUser.getyCoord())).title("Oponent:" + aUser.getUserId())
//				.snippet("Your last recorded location")
//				.icon(BitmapDescriptorFactory.fromResource(this.userIcon)));
//
//				newMarker.setIcon(BitmapDescriptorFactory.fromResource(race));
//				// Add marker to hashmap
//				markersList.put(aUser, newMarker);
//			}
//
//			// Make list update;
//			oldList = newList;
		} else {
			System.out.println("No List has yes been updated");
		}
		System.out.println("__Done UPDATEOKAYERS___");

	}
	
	public void setUpdatedOponenList(ArrayList<User> newList){
		this.newList = newList;
	}

	private int checkIconType(User user) {
		int icon;
		if (user.getRace().equals("Alien")) {
			icon = alienIcon;
		} else {
			icon = marinesIcon;
		}
		return icon;
	}

	public User getClosestUser() {
		Iterator<User> iter = oldList.iterator();
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
		Iterator<User> iter = oldList.iterator();
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

	public void getList(ClientListener clientListener) {
		 newList = clientListener.fetchTheList();
		 System.out.println("TESTING TO PDISAMLK_DS_");		
	}
}
