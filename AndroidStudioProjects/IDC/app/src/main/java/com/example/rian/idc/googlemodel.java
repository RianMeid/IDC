package com.example.rian.idc;

import org.json.JSONArray;

import java.io.InputStreamReader;

/**
 * Created by Rian on 04/10/2017.
 */

public class googlemodel {
    private Double lon;
    private Double lat;
    private JSONArray js;
    private int distance;


    public void setLon(Double lon) {
        this.lon = lon;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setJSONresults(JSONArray jsonArray) {
        this.js = jsonArray;
    }

    public void setDistance(int d){ this.distance = d;}



    public Double getLon() {
        return lon;
    }

    public Double getLat() {
        return lat;
    }

    public JSONArray getJSONarray() {
        return js;
    }

    public int getDistance(){return distance;}



}
