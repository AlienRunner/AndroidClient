//package com.spoton.alienrunner;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.AsyncTask;
//
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.Polyline;
//import com.google.android.gms.maps.model.PolylineOptions;
//
//class GetDirection extends AsyncTask<String, String, String> {
//
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//        pDialog = new ProgressDialog(PolyMap.this);
//        pDialog.setMessage("Loading route. Please wait...");
//        pDialog.setIndeterminate(false);
//        pDialog.setCancelable(false);
//        pDialog.show();
//    }
//
//    protected String doInBackground(String... args) {
//        Intent i = getIntent();
//        String startLocation = i.getStringExtra("startLoc");
//        String endLocation = i.getStringExtra("endLoc");
//                    startLocation = startLocation.replace(" ", "+");
//            endLocation = endLocation.replace(" ", "+");;
//        String stringUrl = "http://maps.googleapis.com/maps/api/directions/json?origin=" + startLocation + ",+dublin&destination=" + endLocation + ",+dublin&sensor=false";
//        StringBuilder response = new StringBuilder();
//        try {
//            URL url = new URL(stringUrl);
//            HttpURLConnection httpconn = (HttpURLConnection) url
//                    .openConnection();
//            if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                BufferedReader input = new BufferedReader(
//                        new InputStreamReader(httpconn.getInputStream()),
//                        8192);
//                String strLine = null;
//
//                while ((strLine = input.readLine()) != null) {
//                    response.append(strLine);
//                }
//                input.close();
//            }
//
//            String jsonOutput = response.toString();
//
//            JSONObject jsonObject = new JSONObject(jsonOutput);
//
//            // routesArray contains ALL routes
//            JSONArray routesArray = jsonObject.getJSONArray("routes");
//            // Grab the first route
//            JSONObject route = routesArray.getJSONObject(0);
//
//            JSONObject poly = route.getJSONObject("overview_polyline");
//            String polyline = poly.getString("points");
//            polyz = decodePoly(polyline);
//
//        } catch (Exception e) {
//
//        }
//
//        return null;
//
//    }
//
//    protected void onPostExecute(String file_url) {
//
//        for (int i = 0; i < polyz.size() - 1; i++) {
//            LatLng src = polyz.get(i);
//            LatLng dest = polyz.get(i + 1);
//            Polyline line = map.addPolyline(new PolylineOptions()
//                    .add(new LatLng(src.latitude, src.longitude),
//                            new LatLng(dest.latitude,                dest.longitude))
//                    .width(2).color(Color.RED).geodesic(true));
//
//        }
//        pDialog.dismiss();
//
//    }
//}
//
///* Method to decode polyline points */
//private List<LatLng> decodePoly(String encoded) {
//
//    List<LatLng> poly = new ArrayList<LatLng>();
//    int index = 0, len = encoded.length();
//    int lat = 0, lng = 0;
//
//    while (index < len) {
//        int b, shift = 0, result = 0;
//        do {
//            b = encoded.charAt(index++) - 63;
//            result |= (b & 0x1f) << shift;
//            shift += 5;
//        } while (b >= 0x20);
//        int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
//        lat += dlat;
//
//        shift = 0;
//        result = 0;
//        do {
//            b = encoded.charAt(index++) - 63;
//            result |= (b & 0x1f) << shift;
//            shift += 5;
//        } while (b >= 0x20);
//        int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
//        lng += dlng;
//
//        LatLng p = new LatLng((((double) lat / 1E5)),
//                (((double) lng / 1E5)));
//        poly.add(p);
//    }
//
//    return poly;
//}