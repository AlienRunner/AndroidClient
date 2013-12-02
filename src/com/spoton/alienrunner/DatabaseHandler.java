package com.spoton.alienrunner;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

<<<<<<< HEAD
=======
import com.google.gson.*;

>>>>>>> 2a2d11565c64dd2944f43705c9549a408371b4c6
public class DatabaseHandler implements DatabaseHandlerInterface {
	private String message;

	public DatabaseHandler() {
		this.message = message;

	}

	@Override
	//Behöver en metod som skicker strängen som skapas i denna metoden till servern
	public ArrayList<User> setAndFetch(User myUser) {
		String userString =  "["+myUser.getUserId()+","+myUser.getxCoord()+","+myUser.getyCoord()+"]";
	//	sendToServer(userString);
		return jsonToUser();
	}
	//på något sätt få strängen som skickats ifrån servern till sträng databaseString
	private ArrayList<User> jsonToUser(){
		String databaseString = "{menu:{\"1\":\"sql\", \"2\":\"android\", \"3\":\"mvc\"}}";
		databaseString = databaseString.replace("[", "");
		databaseString = databaseString.replace("]", "");
		databaseString = databaseString.replace("\"", "");
		String[] b = databaseString.split(",");
		ArrayList<User> list = new ArrayList<User>();
		for(int i =0; i<b.length-1; i+=3){
			User u = new User(b[i],Double.parseDouble(b[i+1]),Double.parseDouble(b[i+2]));
			list.add(u);
		}
		return list;
		}
}
