package com.spoton.alienrunner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    private String message;
  //  private ClientSender clientSender;
    private Context context;
    private Socket socket;
    private static String SERVER_IP = "213.67.75.254";
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        context = this.getApplicationContext();
        socket = null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void sendMessage(View view){
	      EditText editText = (EditText) findViewById(R.id.edit_message);
	      message = editText.getText().toString() + System.getProperty("line.separator"); //Line separator or the server's BufferedReader in.readLine() in will hang forever
	        //Toast.makeText(MainActivity.this, "hi", Toast.LENGTH_LONG).show();
	      new ClientSender(context).execute(message);
	      //clientSender.execute(message);
	}
	
	public void openMap(View view){
		Intent mapScreen = new Intent(getApplicationContext(), MyMapActivity.class);
		startActivity(mapScreen);
		mapScreen.putExtra("key", "value");
		mapScreen.putExtra("email", "myemail@gmail.com");
	}

	private class ClientSender extends AsyncTask<String, Void, Socket> {
        //private Socket socket;
        private String answer;
        private Context context;
        private BufferedWriter out;
        private BufferedReader in;


        public ClientSender(Context context){
            this.context = context;

        }

        @Override
        protected Socket doInBackground(String... params) {
            try{

                if (socket == null){
                    socket = new Socket(SERVER_IP, 21101);
                }
                out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out.write(params[0]);
                out.flush();

                answer = in.readLine() + System.getProperty("line.separator");
                return socket;

            }catch (IOException e){
                e.printStackTrace();
            }
            return socket;
        }
        protected void onPostExecute(Socket socket) {
            if (socket != null) {
                Toast.makeText(context, answer, Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(context, "Can't connect to server!",
                        Toast.LENGTH_LONG).show();
            }

        }
    }
}
