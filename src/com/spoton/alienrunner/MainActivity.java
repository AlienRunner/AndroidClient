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

public class MainActivity extends Activity {
    private String message;
  //  private ClientSender clientSender;
    private Context context;
	EditText inputName;
	EditText inputEmail;
	private LocationManager locMan;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        context = this.getApplicationContext();
        
		inputName = (EditText) findViewById(R.id.name);
		inputEmail = (EditText) findViewById(R.id.email);
		Button btnNextScreen = (Button) findViewById(R.id.btnNextScreen);
		
		
		//Listening to button event
		btnNextScreen.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				//Starting a new Intent
				Intent nextScreen = new Intent(getApplicationContext(), MyMailActivity.class);
				
				//Sending data to another Activity
				nextScreen.putExtra("name", inputName.getText().toString());
				nextScreen.putExtra("email", inputEmail.getText().toString());
				
				// starting new activity
				startActivity(nextScreen);
				
			}
		});
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
		locMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Location lastLoc = locMan
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		double lat = lastLoc.getLatitude();
		double lng = lastLoc.getLongitude();
		LatLng lastLatLng = new LatLng(lat, lng);
		message = lastLatLng.toString() + System.getProperty("line.separator");
		// Toast.makeText(MainActivity.this, "hi", Toast.LENGTH_LONG).show();
		new ClientSender(context).execute(message);
		// clientSender.execute(message);
	}
	
	public void openMap(View view){
		Intent mapScreen = new Intent(getApplicationContext(), MyMapActivity.class);
		startActivity(mapScreen);
	}

}
