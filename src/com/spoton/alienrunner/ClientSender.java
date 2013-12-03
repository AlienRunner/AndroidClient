package com.spoton.alienrunner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class ClientSender extends AsyncTask<String, Void, Socket> {
    //private Socket socket;
    private Socket socket;
    private static String SERVER_IP = "213.67.75.254";
    private String answer;
    private Context context;
    private BufferedWriter out;
    private BufferedReader in;


    public ClientSender(Context context){
        this.context = context;
        socket = null;

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