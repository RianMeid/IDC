package com.example.rian.idc;

import java.io.InputStreamReader;

/**
 * Created by Rian on 04/10/2017.
 */

public class googlemodel {
    private Double lon;
    private Double lat;
    private InputStreamReader inputStreamReader;


    public void setLon(Double lon) {
        this.lon = lon;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setInputStreamReader(InputStreamReader inputStreamReader) {
        this.inputStreamReader = inputStreamReader;
    }



    public Double getLon() {
        return lon;
    }

    public Double getLat() {
        return lat;
    }

    public InputStreamReader getInputStreamReader() {
        return inputStreamReader;
    }



}
