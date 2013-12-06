package com.spoton.alienrunner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import com.google.android.gms.maps.model.LatLng;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity{
//    private Context context;
	EditText inputName;
//	EditText inputEmail;
//	private LocationManager locMan;
//	ClientSender cs;
//	DatabaseHandler dh;
	
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//        context = this.getApplicationContext();
//        cs = new ClientSender(context);
//        dh = new DatabaseHandler(cs);
		this.inputName = (EditText) findViewById(R.id.name);
//		inputEmail = (EditText) findViewById(R.id.email);
//		Button btnNextScreen = (Button) findViewById(R.id.btnNextScreen);
		
		
		//Listening to button event
//		btnNextScreen.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View arg0) {
//				//Starting a new Intent
//				Intent nextScreen = new Intent(getApplicationContext(), MyMailActivity.class);
//				
//				//Sending data to another Activity
//				nextScreen.putExtra("name", inputName.getText().toString());
////				nextScreen.putExtra("email", inputEmail.getText().toString());
//				
//				// starting new activity
//				startActivity(nextScreen);
//				
//			}
//		});
	}
	
	public void selectRole(View view){
		System.out.println("___Selecting role!__");
		findViewById(R.id.mainLayout).requestFocus();
		Button b1 = (Button) findViewById(R.id.button_alien);
		Button b2 = (Button) findViewById(R.id.button_human);
//		R.id.button_human
		Toast.makeText(getApplicationContext(), "b1.isselected lolno",   Toast.LENGTH_LONG).show();
		if(b1.isSelected() == true ){
			System.out.println("Alien button selected");
			Toast.makeText(getApplicationContext(), "b1.isselected",   Toast.LENGTH_LONG).show();
		}if(b2.isSelected() == true){
			System.out.println("Human button selected");
			Toast.makeText(getApplicationContext(), "b2.isselected",   Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void sendMessage(View view) {
		// EditText editText = (EditText) findViewById(R.id.edit_message);
		// message = editText.getText().toString() +
		// System.getProperty("line.separator"); //Line separator or the
		// server's BufferedReader in.readLine() in will hang forever
//		locMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//		Location lastLoc = locMan
//				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//		double lat = lastLoc.getLatitude();
//		double lng = lastLoc.getLongitude();
//		LatLng lastLatLng = new LatLng(lat, lng);
//		message = lastLatLng.toString() + System.getProperty("line.separator");
//		dh.sendMessage("___HŠr kommer ett meddelande!___");
//		new ClientSender(context).execute(message);
		// clientSender.execute(message);
//		cs.sendMessage("THIS IS message_______");
//		Toast.makeText(MainActivity.this, "hi", Toast.LENGTH_LONG).show();
//		User myUser = new User("Johan", 55, 44);
//		cs.setAndFetch(myUser);
	}
	
	public void openMap(View view){
		System.out.println("___Opening map!__");
		Intent mapScreen = new Intent(getApplicationContext(), MyMapActivity.class);
		mapScreen.putExtra("name", inputName.getText().toString());
		startActivity(mapScreen);
	}

}
