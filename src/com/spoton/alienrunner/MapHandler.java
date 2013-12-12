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
			userIcon, currUserIcon, evacIcon;
	private Marker myMarker;
	public GoogleMap map;
	public User myUser;
	private ArrayList<User> oldList;
	private ArrayList<User> newList;
	private HashMap<User, Marker> markersList;
	private Context context;
	public String test;

	MapHandler(GoogleMap theMap, User myUser, Context context) {
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
		evacIcon = R.drawable.purple_point;
		if (myUser.isAlien()) {
			this.userIcon = alienUserIcon;
		} else {
			this.userIcon = marinesUserIcon;
		}
	}

	// Is run by the listener eachtime a gps update is made.
	public void gpsUpdate(Location location) {
		myUser.setxCoord(location.getLatitude());
		myUser.setyCoord(location.getLongitude());
		if (myMarker != null) {
			myMarker.setPosition(new LatLng(location.getLatitude(), location
					.getLongitude()));
		} else {
			myMarker = map.addMarker(new MarkerOptions()
					.position(
							new LatLng(location.getLatitude(), location
									.getLongitude())).title("You are here")
					.snippet("Your last recorded location")
					.icon(BitmapDescriptorFactory.fromResource(this.userIcon)));
		}
	}

	// Fetch Updates from server and update all players and markers;
	@SuppressWarnings("unchecked")
	public void updatePlayers() {
		if (newList != null) {
			// 1. Remove players from map and from hashmap.
			ArrayList<User> copyOldList = (ArrayList<User>) oldList.clone();
			// JUST A COPY
			ArrayList<User> copyNewList = (ArrayList<User>) newList.clone();
			copyOldList.removeAll(copyNewList);
			// NOW copyOponentList only contains users that have left the game!!
			if (copyOldList.size() > 0) {
				Iterator<User> it = copyOldList.iterator();
				while (it.hasNext()) {
					User aUser = it.next();
					Marker aMarker = markersList.get(aUser);
					aMarker.remove();
					markersList.remove(aUser);
				}
			}
			// 2. Update players position that all ready exist.
			copyNewList = (ArrayList<User>) newList.clone();
			// SAVES OBJECT THAT EXIST IN BOTH LISTS
			copyOldList = (ArrayList<User>) oldList.clone();
			copyNewList.retainAll(copyOldList);
			Iterator<User> it = copyNewList.iterator();
			while (it.hasNext()) {
				User aUser = it.next();
				Marker aMarker = markersList.get(aUser);
				LatLng coords = new LatLng(aUser.getxCoord(), aUser.getyCoord());
				aMarker.setPosition(coords);
			}

			// 3. Add new players to Gmap and HashMap
			copyNewList = (ArrayList<User>) newList.clone();
			copyOldList = (ArrayList<User>) oldList.clone();
			copyNewList.removeAll(copyOldList);
			if (copyNewList.size() > 0) {
				Iterator<User> it2 = copyNewList.iterator();
				User aUser;
				while (it2.hasNext()) {
					// Display new Marker on Gmap
					aUser = it2.next();

					if (aUser.isAlien()) {
						this.currUserIcon = alienIcon;
					} else if (aUser.isMarine()) {
						this.currUserIcon = marinesIcon;
					} else {
						this.currUserIcon = evacIcon;
					}
					Marker newMarker = map.addMarker(new MarkerOptions()
							.position(
									new LatLng(aUser.getxCoord(), aUser
											.getyCoord()))
							.title(aUser.getUserId())
							.snippet(aUser.getRace())
							.icon(BitmapDescriptorFactory
									.fromResource(currUserIcon)));

					// Add marker to hashmap
					markersList.put(aUser, newMarker);
				}
			}
			// Make list update;
			oldList = newList;
		}
	}

	public void setUpdatedOponenList(ArrayList<User> newList) {
		this.newList = newList;
	}

	public User getClosestMarine() {
		Iterator<User> iter = oldList.iterator();
		double dist = Double.MAX_VALUE;
		double x1 = myUser.getxCoord();
		double y1 = myUser.getyCoord();
		User listUser = null;
		while (iter.hasNext()) {
			User tempUser = iter.next();
			if (tempUser.isMarine()) {
				double x2 = tempUser.getxCoord();
				double y2 = tempUser.getyCoord();
				double tempDist = getDistance(x1, y1, x2, y2);
				if (tempDist < dist) {
					dist = tempDist;
					listUser = tempUser;
				}
			}
		}
		return listUser;
	}

	public User getClosestEvac() {
		Iterator<User> iter = oldList.iterator();
		double dist = Double.MAX_VALUE;
		double x1 = myUser.getxCoord();
		double y1 = myUser.getyCoord();
		User listUser = null;
		while (iter.hasNext()) {
			User tempUser = iter.next();
			System.out.println("COUNTING CLOSEST EVAC..." + tempUser.encrypt());
			if (tempUser.isEvac()) {
				double x2 = tempUser.getxCoord();
				double y2 = tempUser.getyCoord();
				double tempDist = getDistance(x1, y1, x2, y2);
				if (tempDist < dist) {
					dist = tempDist;
					listUser = tempUser;
				}
			}
		}
		return listUser;
	}

	// @return distance to the closest person on the map.
	// in Meter!
	public double getLeastDistance() {
		if (myUser.getRace().equals("Alien")) {
			return getClosestMarineDistance();
		} else
			return getClosestAlienDistance();
	}

	public double getClosestMarineDistance() {
		Iterator<User> iter = oldList.iterator();
		double dist = Double.MAX_VALUE;
		double x1 = myUser.getxCoord();
		double y1 = myUser.getyCoord();
		while (iter.hasNext()) {
			User tempUser = iter.next();
			if (tempUser.getRace().equals("Marines")) {
				double x2 = tempUser.getxCoord();
				double y2 = tempUser.getyCoord();
				double tempDist = getDistance(x1, y1, x2, y2);
				if (tempDist < dist) {
					dist = tempDist;
				}
			}
		}
		return dist;
	}

	public double getClosestEvacDistance() {
		Iterator<User> iter = oldList.iterator();
		double dist = Double.MAX_VALUE;
		double x1 = myUser.getxCoord();
		double y1 = myUser.getyCoord();
		while (iter.hasNext()) {
			User tempUser = iter.next();
			if (tempUser.getRace().equals(("Evac"))) {

				double x2 = tempUser.getxCoord();
				double y2 = tempUser.getyCoord();
				double tempDist = getDistance(x1, y1, x2, y2);
				if (tempDist < dist) {
					dist = tempDist;
				}
			}
		}
		return dist;
	}

	public double getClosestAlienDistance() {
		Iterator<User> iter = oldList.iterator();
		double dist = Double.MAX_VALUE;
		double x1 = myUser.getxCoord();
		double y1 = myUser.getyCoord();
		while (iter.hasNext()) {
			User tempUser = iter.next();
			if (tempUser.getRace().equals("Alien")) {
				double x2 = tempUser.getxCoord();
				double y2 = tempUser.getyCoord();
				double tempDist = getDistance(x1, y1, x2, y2);
				if (tempDist < dist) {
					dist = tempDist;
				}
			}
		}
		return dist;
	}

	private double getDistance(double x1, double y1, double x2, double y2) {
		double theDistance = (Math.sin(Math.toRadians(x1))
				* Math.sin(Math.toRadians(x2)) + Math.cos(Math.toRadians(x1))
				* Math.cos(Math.toRadians(x2))
				* Math.cos(Math.toRadians(y1 - y2)));
		return Math
				.round((Math.toDegrees(Math.acos(theDistance)) * 69.09 * 1.6093 * 1000));
	}

	public void getList(ClientListener clientListener) {
		newList = clientListener.fetchTheList();
	}

	public void setDirections(Location location) {

	}

	public void centerCamera() {
		map.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(myUser
				.getxCoord(), myUser.getyCoord())), 3000, null);
	}

}
