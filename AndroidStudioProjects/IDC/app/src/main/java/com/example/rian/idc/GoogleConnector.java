package com.example.rian.idc;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
        g.execute(la,lo);










                }




    @Override
    protected InputStreamReader doInBackground(Double... lat) {
        //lat first lon last
        try {


            URL url = new URL("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+Double.toString(lat[0])+","+Double.toString(lat[1])+"&radius=5000&type=restaurant&key=AIzaSyBijvlQv6Ao4yIm4rl7sG21lPACMDxI23g");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(con.getInputStream());

            System.out.println("connection made");
            System.out.println(in);
            return in;

        }catch (IOException E){
            E.printStackTrace();
    }
    return null;

    }
    @Override
    protected void onPostExecute(InputStreamReader in){
        System.out.println(in);

    }

}

