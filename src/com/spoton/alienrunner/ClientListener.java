package com.spoton.alienrunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ClientListener extends Thread {
	private InputStream is;
	private BufferedReader in;
	private MapHandler mh;
	private ArrayList<User> theList;
	final int MARKER_UPDATE_INTERVAL = 2000;
	Marker marker;
	Handler handler;
	GoogleMap theMap;

	public ClientListener(GoogleMap theMap, Handler handler, Socket s,
			MapHandler mh) {
		// TODO Auto-generated constructor stub
		this.theMap = theMap;
		this.handler = handler;
		this.mh = mh;
		try {
			this.is = s.getInputStream();
			this.in = new BufferedReader(new InputStreamReader(is));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
		this.theList = new ArrayList<User>();
		String answer;
		try {
			System.out.println("Client waiting for input...");
			answer = in.readLine() + System.getProperty("line.separator");
			System.out.println("Client Recieved: " + answer + " ");
			char c = answer.charAt(0);
			if (c == '[') {
				theList = jsonToUser(answer);
				mh.getList(this);
				mh.updatePlayers();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		handler.postDelayed(this, MARKER_UPDATE_INTERVAL);
	}

	private ArrayList<User> jsonToUser(String answer) {
		String databaseString = answer;
		databaseString = databaseString.replace("[", "");
		databaseString = databaseString.replace("]", "");
		databaseString = databaseString.replace("\"", "");
		String[] b = databaseString.split(",");
		ArrayList<User> list = new ArrayList<User>();
		for (int i = 0; i < b.length - 1; i += 4) {
			User u = new User(b[i], Double.parseDouble(b[i + 1]),
					Double.parseDouble(b[i + 2]), b[i + 3]);
			list.add(u);
		}
		return list;
	}

	public ArrayList<User> fetchTheList() {
		return theList;
	}
}
