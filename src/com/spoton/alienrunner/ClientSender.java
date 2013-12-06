package com.spoton.alienrunner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class ClientSender extends AsyncTask<String, Void, String> implements
		Serializable {

	private static final long serialVersionUID = 1L;
	private Socket socket;
	public static String SERVER_IP = "213.67.75.254";
	private Context context;
	private BufferedWriter out;
	private BufferedReader in;

	public ClientSender(Context context) {
		this.context = context;
		socket = null;
	}

	public String sendMessage(String txt) {
		try {
			return this.execute(txt + System.getProperty("line.separator"))
					.get();
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	public ArrayList<User> setAndFetch(User myUser) {
		String userString = "[" + myUser.getUserId() + "," + myUser.getxCoord()
				+ "," + myUser.getyCoord() + "," + myUser.getRace() + "]";
		String answer = sendMessage(userString);
		System.out.println("__This was the answer:__" + answer);

		return jsonToUser(answer);

	}

	//
	// public void insertUser(String userName, String race) {
	// String userString = "1" + "[" + userName + "," + "0.0" + "," + "0.0"
	// + "," + race + "]";
	// sendMessage(userString);
	// }

	// på något sätt få strängen som skickats ifrån servern till sträng
	// databaseString
	private ArrayList<User> jsonToUser(String answer) {
		String databaseString = answer;
		System.out.println("_______" + databaseString);
		// String databaseString =
		// "{menu:{\"1\":\"sql\", \"2\":\"android\", \"3\":\"mvc\"}}";
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

	@Override
	protected String doInBackground(String... params) {
		String answer = null;
		try {

			if (socket == null) {
				socket = new Socket(SERVER_IP, 21101);
			}
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out.write(params[0]);
			out.flush();

			answer = in.readLine() + System.getProperty("line.separator");
			// System.out.println("____Answer:___" + answer);
			// fetch(answer);
			return answer;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return answer;
	}

	protected void onPostExecute(String response) {
		if (response != null) {
			System.out.println("ONPOSTEXCECUTE" + response);
			Toast.makeText(context, "Connected to server!",
					Toast.LENGTH_LONG).show();

		} else {
			System.out.println("ONPOSTEXCECUTEERROR" + response);
			Toast.makeText(context, "Can't connect to server!",
					Toast.LENGTH_LONG).show();
		}

	}

	public User getClosestUser(User currUser, ArrayList<User> userList) {
		Iterator<User> iter = userList.iterator();
		double dist = Double.MAX_VALUE;
		double x1 = currUser.getxCoord();
		double y1 = currUser.getyCoord();
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

	public double getLeastDistance(User currUser, ArrayList<User> userList) {
		Iterator<User> iter = userList.iterator();
		double dist = Double.MAX_VALUE;
		double x1 = currUser.getxCoord();
		double y1 = currUser.getyCoord();
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
		return dist;
	}

	private double getDistance(double x1, double y1, double x2, double y2) {
		double theDistance = (Math.sin(Math.toRadians(x1))
				* Math.sin(Math.toRadians(x2)) + Math.cos(Math.toRadians(x1))
				* Math.cos(Math.toRadians(x2))
				* Math.cos(Math.toRadians(y1 - y2)));
		return (Math.toDegrees(Math.acos(theDistance)) * 69.09 * 1.6093);
//		return (Math.toDegrees(Math.acos(theDistance)) * 69.09 * 1.6093);
	}
}
