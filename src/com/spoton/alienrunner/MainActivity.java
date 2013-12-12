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
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText inputName;
	Button b1;
	Button b2;
	String race = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_main);
		b1 = (Button) findViewById(R.id.button_alien);
		b2 = (Button) findViewById(R.id.button_human);

		this.inputName = (EditText) findViewById(R.id.name);
		MediaPlayer mp = MediaPlayer.create(MainActivity.this, R.raw.maktone);
		mp.setLooping(true);
		mp.start();
	}

	public void selectRoleAlien(View view) {
		if (b1.isSelected() == false) {
			b1.setSelected(true);
			b2.setSelected(false);
			this.race = "Alien";
			b1.setBackgroundResource(R.drawable.alien_button_selected);
			b2.setBackgroundResource(R.drawable.human_button);
		}
	}

	public void selectRoleHuman(View view) {
		if (b2.isSelected() == false) {
			b1.setSelected(false);
			b2.setSelected(true);
			this.race = "Marines";
			b1.setBackgroundResource(R.drawable.alien_button);
			b2.setBackgroundResource(R.drawable.human_button_selected);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void openMap(View view) {
		if (this.race == null) {
			Toast customToast = new Toast(getApplicationContext());
			customToast = Toast.makeText(getApplicationContext(),
					"Select your race first!", Toast.LENGTH_SHORT);
			customToast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
			customToast.show();
		} else {
			Intent mapScreen = new Intent(getApplicationContext(),
					MyMapActivity.class);
			mapScreen.putExtra("race", this.race);
			mapScreen.putExtra("name", inputName.getText().toString());
			startActivity(mapScreen);
		}
	}

}
