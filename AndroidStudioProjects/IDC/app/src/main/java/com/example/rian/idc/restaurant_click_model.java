package com.example.rian.idc;

/**
 * Created by Rian on 08/01/2018.
 */

public class restaurant_click_model {
    String titel;
    String imgurl;
    String lat;
    String lon;
    restaurant_click_model(String titel){
        this.titel = titel;

    }

    public String get_titel(){
        return this.titel;
    }
}
