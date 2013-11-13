package com.example.alienrunner;

import android.app.Activity;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MainActivity extends Activity {
    //private static final String TAG = "MainActivity";
    private String message;
    private ClientSender clientSender;
    private Context context;
    private Socket socket;
    private static String SERVER_IP = "192.168.1.30";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();
        socket = null;
       //clientSender = new ClientSender(context);


        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                   .add(R.id.container, new PlaceholderFragment())
                   .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }


    public void sendMessage(View view){
        EditText editText = (EditText) findViewById(R.id.edit_message);
        message = editText.getText().toString() + System.getProperty("line.separator"); //Line separator or the server's BufferedReader in.readLine() in will hang forever
        //Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
        new ClientSender(context).execute(message);
        //clientSender.execute(message);
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
                    socket = new Socket(SERVER_IP, 21111);
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