package com.example.rian.idc;

import android.Manifest;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;

public class MainActivity extends AppCompatActivity{
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("test");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button3);
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
                        gc.Httpsrequest(lon, lat);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });





    }}
