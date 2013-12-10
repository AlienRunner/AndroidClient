package com.spoton.alienrunner;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import android.util.Log;

public class ClientSend extends Thread {
	private OutputStream os;
	private Socket sock;
	private OutputStreamWriter out;
	private User myUser;
	private String userInfo;

	public ClientSend(Socket s, User myUser) throws IOException {
		this.sock = s;
		this.myUser = myUser;
		this.os = sock.getOutputStream();
	}

	public void run() {
		out = new OutputStreamWriter(os);
		while (sock.isConnected()) {
			userInfo = myUser.encrypt();
			System.out.println("___Client Sends: " + userInfo);
			try {
//				userInfo = new User("Pelle", 66, 99,"Alien").encrypt();
				out.write(userInfo + System.getProperty("line.separator"));
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("___Client Send done: " + userInfo);

			try {
				Log.d("TAG", "local Thread sleeping");
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				Log.e("TAG", "local Thread error", e);
			}
		}
	}
}
