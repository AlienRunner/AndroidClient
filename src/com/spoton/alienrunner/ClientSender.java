package com.spoton.alienrunner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class ClientSender extends AsyncTask<String, Void, String> implements Serializable {
    private Socket socket;
    public static String SERVER_IP = "213.67.75.254";
    private Context context;
    private BufferedWriter out;
    private BufferedReader in;
    private String leastDistance;
//    private String answer;


    public ClientSender(Context context){
        this.context = context;
        socket = null;
        leastDistance = "701";
    }
    
    public String sendMessage(String txt){
                 try{
                         return this.execute(txt + System.getProperty("line.separator")).get();
                 }catch(Exception e){
                        e.printStackTrace();
                        return "error";
                 }
    }
    
        public ArrayList<User> setAndFetch(User myUser) {
                String userString = "2" + "["+myUser.getUserId()+","+myUser.getxCoord()+","+myUser.getyCoord()+"]";
                String answer = sendMessage(userString);
                System.out.println("__This was the answer:__" + answer);
//                return null;

                return jsonToUser(answer);
                
        }
        public void insertUser(User myUser,String race ){
                String userString = "1" + "["+myUser.getUserId()+","+myUser.getxCoord()+","+myUser.getyCoord()+race+"]";
                sendMessage(userString);
                
        }
        

        //på något sätt få strängen som skickats ifrån servern till sträng databaseString
        private ArrayList<User> jsonToUser(String answer){
                String databaseString = answer;
                System.out.println("_______" + databaseString);
//                String databaseString = "{menu:{\"1\":\"sql\", \"2\":\"android\", \"3\":\"mvc\"}}";
                databaseString = databaseString.replace("[", "");
                databaseString = databaseString.replace("]", "");
                databaseString = databaseString.replace("\"", "");
                String[] b = databaseString.split(",");
                ArrayList<User> list = new ArrayList<User>();
                for(int i =0; i<b.length-1; i+=4){
                        User u = new User(b[i],Double.parseDouble(b[i+1]),Double.parseDouble(b[i+2]), b[i+3]);
                        list.add(u);
                }
                return list;
                }

    @Override
    protected String doInBackground(String... params) {
        String answer = null;
            try{

            if (socket == null){
                socket = new Socket(SERVER_IP, 21101);
            }
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.write(params[0]);
            out.flush();

            answer = in.readLine() + System.getProperty("line.separator");
//            System.out.println("____Answer:___" + answer);
//            fetch(answer);
            return answer;

        }catch (IOException e){
            e.printStackTrace();
        }
        return answer;
    }
    protected void onPostExecute(String response) {
        if (response != null) {
                System.out.println("ONPOSTEXCECUTE" + response);
//                this.answer = response;
//            Toast.makeText(context, answer, Toast.LENGTH_LONG).show();

        } else {
                System.out.println("ONPOSTEXCECUTEERROR" + response);
            Toast.makeText(context, "Can't connect to server!",
                    Toast.LENGTH_LONG).show();
        }

    }
    public String getLeastDistance(){
            return leastDistance;
    }
    public void setLeastDistance(String Distance){
            leastDistance = Distance;
            
    }
    
}