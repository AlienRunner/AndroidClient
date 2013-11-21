package com.spoton.alienrunner;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyMapActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen2);
        
        TextView txtName = (TextView) findViewById(R.id.txtName);
        TextView txtEmail = (TextView) findViewById(R.id.txtEmail);
        Button btnClose = (Button) findViewById(R.id.btnClose);
        
        Intent i = getIntent();
        // Receiving the Data
        String name = i.getStringExtra("name");
        String email = i.getStringExtra("email");
        
        // Displaying Received data
        txtName.setText(name);
        txtEmail.setText(email);
        
        // Binding Click event to Button
        btnClose.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				//Closing SecondScreen Activity
				finish();
			}
		});
        
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}