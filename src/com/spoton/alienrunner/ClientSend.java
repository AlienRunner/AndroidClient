package com.spoton.alienrunner;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import android.os.Looper;

public class ClientSend extends Thread{
		private OutputStream os;
		private Socket sock;
		private OutputStreamWriter out;
		private User myUser;
		private String userInfo;
		private String tempUserInfo;

		public ClientSend(Socket s, User myUser) throws IOException {
			this.sock = s;
			this.myUser = myUser;
			this.os = sock.getOutputStream();
		}

		@SuppressWarnings("static-access")
		public void run() {
			try {
				out = new OutputStreamWriter(os);
				while (sock.isConnected()) {
					Looper.prepare();
					userInfo = myUser.encrypt();
					System.out.println("UserInfo" + userInfo);
					if(userInfo.equals(tempUserInfo) == false){
						tempUserInfo = userInfo;
						System.out.println("___Client Sends: " + userInfo);
						out.write(userInfo + System.getProperty("line.separator"));
						out.flush();
						System.out.println("___Client Send done: " + userInfo);
					}
//					try {
//						this.sleep(2000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					Looper.loop();
					
				}
			} catch (IOException e) {
				System.out.println("Exception:" + e);
				e.printStackTrace();
			}
		}
	}

