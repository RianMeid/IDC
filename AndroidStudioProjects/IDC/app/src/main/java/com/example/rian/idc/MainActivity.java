package com.example.rian.idc;

import android.Manifest;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.constraint.solver.Goal;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends ListActivity{
    Button button;
    Button button1;
    Button button2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("test");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button4);
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                System.out.println("button pressed");
                GPStracker g = new GPStracker(getApplicationContext());
                Location l = g.getLocation();

                if (l != null){
                    double lat = l.getLatitude();
                    double lon = l.getLongitude();
                    Toast.makeText(getApplicationContext(),"LAT: "+ lat+ "\n LON: "+lon,Toast.LENGTH_LONG).show();

                    GoogleConnector gc = new GoogleConnector();
                    try {
                        JSONObject j = gc.Httpsrequest(lon, lat);
                        JSONArray jsonArray = j.getJSONArray("results");
                        googlemodel m = new googlemodel();
                        m.setJSONresults(jsonArray);
                        List<String> results = new ArrayList<String>();
                        List<String> rating = new ArrayList<String>();
                        for (int i = 0; i< jsonArray.length(); i++){
                            String st = jsonArray.getJSONObject(i).getString("name");
                            String re = jsonArray.getJSONObject(i).getString("rating");
                            System.out.println(st);
                            results.add(st+System.getProperty("line.separator")+"Rating:"+re);
                            rating.add(re);
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(), android.R.layout.simple_list_item_1, results);
                        getListView().setAdapter(adapter);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        button1 = (Button) findViewById(R.id.distancebutton);
        button1.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                System.out.println(button1.getText());
                button1.setText("500");
                if (button1.getText() == "500"){
                    button1.setText("1000");
                }
                if (button1.getText() == "1000"){
                    button1.setText("5000");
                }
                if (button1.getText() == "5000"){
                    button1.setText("10000");
                }
                if (button1.getText() == "10000"){
                    button1.setText("500");
                }

            }
        });

        button2 = (Button) findViewById(R.id.favouritesbutton);
        button2.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                System.out.println("fav");
            }
        });


    }}
