package com.spoton.alienrunner;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

	public class DatabaseHandler implements DatabaseHandlerInterface {
//		private String userString;
		private ClientSender cs;
	public DatabaseHandler(ClientSender cs) {
		this.cs = cs;
	}
	@Override
	public ArrayList<User> setAndFetch(User myUser) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	//Beh�ver en metod som skicker str�ngen som skapas i denna metoden till servern
//	public ArrayList<User> setAndFetch(User myUser) {
//		String userString =  "["+myUser.getUserId()+","+myUser.getxCoord()+","+myUser.getyCoord()+"]";
//		String fromServer = cs.sendMessage("H�R KOMMER ETT MEDDELANDE!");
//		return jsonToUser(fromServer);
//	}
//	//p� n�got s�tt f� str�ngen som skickats ifr�n servern till str�ng databaseString
//	private ArrayList<User> jsonToUser(){
//		String databaseString = "{menu:{\"1\":\"sql\", \"2\":\"android\", \"3\":\"mvc\"}}";
//		databaseString = databaseString.replace("[", "");
//		databaseString = databaseString.replace("]", "");
//		databaseString = databaseString.replace("\"", "");
//		String[] b = databaseString.split(",");
//		ArrayList<User> list = new ArrayList<User>();
//		for(int i =0; i<b.length-1; i+=3){
//			User u = new User(b[i],Double.parseDouble(b[i+1]),Double.parseDouble(b[i+2]));
//			list.add(u);
//		}
//		return list;
//		}
}
