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
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    static boolean called = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String user = "Rian";
        if (!called){
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            called = true;
        }









        System.out.println("test");
        sqlController sql = new sqlController(this);
        sql.getFavourites();
        googlemodel gm = new googlemodel();
        gm.setDistance(500);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button3);
        button1 = (Button) findViewById(R.id.distancebutton);
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                GPStracker g = new GPStracker(getApplicationContext());
                Location l = g.getLocation();

                if (l != null){
                    final double lat = l.getLatitude();
                    final double lon = l.getLongitude();


                    GoogleConnector gc = new GoogleConnector();
                    try {
                        JSONObject j = gc.Httpsrequest(lon, lat, Integer.parseInt((String) button1.getText()));
                        JSONArray jsonArray = j.getJSONArray("results");
                        System.out.println(jsonArray);
                        if (jsonArray == null){
                            Toast.makeText(MainActivity.this,"No internet connection",Toast.LENGTH_LONG).show();
                        }
                        googlemodel m = new googlemodel();
                        m.setJSONresults(jsonArray);
                        List<String> results = new ArrayList<String>();
                        final List<String> rating = new ArrayList<String>();
                        final List<String> titel = new ArrayList<String>();
                        final List<String> ratings = new ArrayList<String>();
                        final List<String> latlist = new ArrayList<String>();
                        final List<String> lnglist = new ArrayList<String>();
                        final List<String> imgurl = new ArrayList<String>();
                        final List<String> vicinity = new ArrayList<String>();

                        for (int i = 0; i< jsonArray.length(); i++){
                            System.out.println(jsonArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location"));
                            String st;
                            String re;
                            try{
                                re = jsonArray.getJSONObject(i).getString("rating");
                                st = jsonArray.getJSONObject(i).getString("name");
                                results.add(st+System.getProperty("line.separator")+"Rating:"+re);
                                titel.add(st);
                                latlist.add(jsonArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getString("lat"));
                                lnglist.add(jsonArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getString("lng"));
                                imgurl.add(jsonArray.getJSONObject(i).getString("place_id"));
                                rating.add(re);
                                vicinity.add(jsonArray.getJSONObject(i).getString("vicinity"));
                            }catch (JSONException e){
                                System.out.println(e);
                            }



                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(), android.R.layout.simple_list_item_1, results);
                        getListView().setAdapter(adapter);

                        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String selectedFromList = (String) getListView().getItemAtPosition(i);
                                //sqlController sql = new sqlController(MainActivity.this);
                                //sql.setFavourites(selectedFromList);
                                Intent intent = new Intent(MainActivity.this, restaurant_view.class);
                                intent.putExtra("titel",titel.get(i));
                                intent.putExtra("rating",rating.get(i));
                                intent.putExtra("placeid",imgurl.get(i));
                                intent.putExtra("lat1",lat);
                                intent.putExtra("lat2",latlist.get(i));
                                intent.putExtra("lon1",lon);
                                intent.putExtra("lon2",lnglist.get(i));
                                intent.putExtra("distance",Integer.parseInt((String) button1.getText()));
                                intent.putExtra("vicinity",vicinity.get(i));
                                startActivity(intent);



                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }catch (NullPointerException e){
                        System.out.println("It's a null");
                        Toast.makeText(MainActivity.this,"No connection",Toast.LENGTH_LONG).show();
                        e.printStackTrace();

                    }
                }
            }
        });


        button1.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                if (Integer.parseInt((String) button1.getText()) == 500){
                    button1.setText("2000");

                }else  {
                    if (Integer.parseInt((String) button1.getText()) == 2000){
                        button1.setText("5000");

                    }else {
                        if (Integer.parseInt((String) button1.getText()) == 5000){
                            button1.setText("10000");

                        }else {
                            if (Integer.parseInt((String) button1.getText()) == 10000) {
                                button1.setText("500");
                            }
                        }



                    }
                }



            }
        });

        button2 = (Button) findViewById(R.id.favourite);
        button2.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, favourite_view.class);
                startActivity(i);
            }
        });


    }}
