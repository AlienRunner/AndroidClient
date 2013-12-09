package com.spoton.alienrunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ClientListener extends Thread {
	private InputStream is;
	private Socket sock;
	private MapHandler mh;
	private Context context;

	public ClientListener(Socket s, MapHandler mh, Context context)
			throws IOException {
		this.sock = s;
		this.mh = mh;
		this.is = sock.getInputStream();
		this.context = context;
	}

	public void run() {
		try {
			System.out.println("In ClientListener run beginning");
			BufferedReader in = new BufferedReader(new InputStreamReader(is));
			while (sock.isConnected()) {
				System.out.println("Socket is connected, waiting for input...");
				String answer = in.readLine() + System.getProperty("line.separator");
				System.out.println("Client Recieved1: " + answer + " ");
				char c = answer.charAt(0);
				if (c == '[') {
					mh.updatedOponentList = jsonToUser(answer);
				} else {
					Toast customToast = new Toast(context);
					customToast = Toast.makeText(context, answer,
							Toast.LENGTH_SHORT);
					customToast.setGravity(Gravity.CENTER | Gravity.CENTER, 0,
							0);
					customToast.show();
				}
				System.out.println("Client Recieved2: " + answer + " ");
			}
		} catch (IOException e) {
			System.out.println("Exception:" + e);
			e.printStackTrace();
		}
	}

	private ArrayList<User> jsonToUser(String answer) {
		String databaseString = answer;
		System.out.println("______jSonToUser_" + databaseString);
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
}