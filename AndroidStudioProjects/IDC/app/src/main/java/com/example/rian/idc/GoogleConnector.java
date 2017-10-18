package com.example.rian.idc;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Rian on 01/10/2017.
 */

public class GoogleConnector extends AsyncTask<Double, Integer, InputStreamReader>{
    String GoogleAPI = "AIzaSyCwnzbDHsrYfadm_MFuqpzQJ1KVH4I1aow";
    public InputStreamReader x;
    public Double lo;
    public Double la;




    public void Httpsrequest (double lon,double lat) throws IOException{

        googlemodel m = new googlemodel();
        this.lo=lon;
        this.la=lat;
        if (la == null){
            System.out.println("lat in http is null");
        }
        m.setLat(lat);
        m.setLon(lon);
        GoogleConnector g = new GoogleConnector();
        try {
            InputStreamReader i = g.execute(la,lo).get();
            System.out.println(i+"dis one");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(x+"lol");











                }




    @Override
    protected InputStreamReader doInBackground(Double... lat) {
        //lat first lon last
        try {

            System.out.println("lat= "+lat[0]+"lon= "+lat[1]);
            URL url = new URL("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+Double.toString(lat[0])+","+Double.toString(lat[1])+"&radius=5000&type=restaurant&key=AIzaSyBijvlQv6Ao4yIm4rl7sG21lPACMDxI23g");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(con.getInputStream());
            System.out.println(con.getResponseMessage());

            System.out.println("connection made");
            BufferedReader s = new BufferedReader(in);
            StringBuilder stringBuilder = new StringBuilder();
            String input;
            while ((input = s.readLine())!= null)
                stringBuilder.append(input);
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i< jsonArray.length(); i++){
                String st = jsonArray.getJSONObject(i).getString("name");
                System.out.println(st);
            }

            return in;

        }catch (IOException E){
            E.printStackTrace();
    }catch (JSONException e){
            e.printStackTrace();
        }
    return null;

    }
    @Override
    protected void onPostExecute(InputStreamReader in){
        System.out.println(in);
        this.x = in;

    }

}

