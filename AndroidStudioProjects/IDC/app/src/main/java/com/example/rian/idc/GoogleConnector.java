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

public class GoogleConnector extends AsyncTask<Double, Integer, JSONObject>{
    String GoogleAPI = "AIzaSyCwnzbDHsrYfadm_MFuqpzQJ1KVH4I1aow";
    public InputStreamReader x;
    public Double lo;
    public Double la;




    public JSONObject Httpsrequest (double lon,double lat, int distance) throws IOException{

        googlemodel m = new googlemodel();
        this.lo=lon;
        this.la=lat;
        if (la == null){

        }
        m.setLat(lat);
        m.setLon(lon);
        GoogleConnector g = new GoogleConnector();
        try {
            double d = (double) distance;
            JSONObject i = g.execute(la,lo,d).get();

            return i;


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }








        return null;



                }




    @Override
    protected JSONObject doInBackground(Double... lat)   {
        //lat first lon last
        try {


            URL url = new URL("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+Double.toString(lat[0])+","+Double.toString(lat[1])+"&radius="+Double.toString(lat[2])+"&type=restaurant&keyword=eten&key=AIzaSyBijvlQv6Ao4yIm4rl7sG21lPACMDxI23g");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(con.getInputStream());



            BufferedReader s = new BufferedReader(in);
            StringBuilder stringBuilder = new StringBuilder();
            String input;
            while ((input = s.readLine())!= null)
                stringBuilder.append(input);
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            return jsonObject;

        }catch (IOException E){
            E.printStackTrace();
    }catch (JSONException e){
            e.printStackTrace();
        }
    return null;

    }
    @Override
    protected void onPostExecute(JSONObject in){



    }

}

