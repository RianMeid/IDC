package com.example.rian.idc;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Rian on 08/01/2018.
 */

public class restaurant_view extends AppCompatActivity{
    private GeoDataClient mGeoDataClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.restaurant_view);
        final Bundle b = getIntent().getExtras();
        String titel = b.getString("titel");
        TextView titeltext = (TextView) findViewById(R.id.titel);
        titeltext.setText(titel);
        TextView vicinitytext = (TextView) findViewById(R.id.vicinitytext);
        vicinitytext.setText(b.getString("vicinity"));
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        System.out.println(Float.valueOf(b.getString("rating")));
        ratingBar.setRating(Float.valueOf(b.getString("rating")));

        double ddistance = calculate_distance(b.getDouble("lat1"),Double.parseDouble(b.getString("lat2")),b.getDouble("lon1"),Double.parseDouble(b.getString("lon2")));
        int distance = (int) ddistance;
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar2);

        System.out.println(distance);
        if (distance > b.getInt("distance")){
            progressBar.setProgress(100);
            progressBar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        }else{
            System.out.println("it's in else");
            System.out.println(distance);
            System.out.println(b.getInt("distance"));
            System.out.println((int) (((double)distance/(double)b.getInt("distance"))*100));
            progressBar.setProgress((int) (((double)distance/(double)b.getInt("distance"))*100));
        }




        mGeoDataClient = Places.getGeoDataClient(this, null);
        final String placeId = b.getString("placeid");
        final Task<PlacePhotoMetadataResponse> photoMetadataResponse = mGeoDataClient.getPlacePhotos(placeId);
        try{
            photoMetadataResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoMetadataResponse>() {
                @Override
                public void onComplete( Task<PlacePhotoMetadataResponse> task) {
                    try {
                        // Get the list of photos.
                        PlacePhotoMetadataResponse photos = task.getResult();
                        // Get the PlacePhotoMetadataBuffer (metadata for all of the photos).
                        PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();
                        // Get the first photo in the list.
                        PlacePhotoMetadata photoMetadata = photoMetadataBuffer.get(0);
                        // Get the attribution text.
                        CharSequence attribution = photoMetadata.getAttributions();
                        // Get a full-size bitmap for the photo.
                        Task<PlacePhotoResponse> photoResponse = mGeoDataClient.getPhoto(photoMetadata);
                        photoResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoResponse>() {
                            @Override
                            public void onComplete( Task<PlacePhotoResponse> task) {
                                PlacePhotoResponse photo = task.getResult();
                                Bitmap bitmap = photo.getBitmap();
                                ProgressBar progressBarcircle = (ProgressBar) findViewById(R.id.progressBar3);
                                progressBarcircle.setVisibility(View.INVISIBLE);
                                ImageView mImg = (ImageView) findViewById(R.id.imagerestaurant);
                                mImg.setImageBitmap(bitmap);
                            }
                        });
                    }catch (Exception e){

                    }

                    Button buttonfav = (Button) findViewById(R.id.Favbut);
                    buttonfav.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v){
                            sqlController sql = new sqlController(restaurant_view.this);

                            String user = "Rian";

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            final DatabaseReference myRef = database.getReference(user);
                            ValueEventListener postListener = new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    final List<String> favourites = new ArrayList<String>();
                                    if (dataSnapshot.exists()){
                                        myRef.push().setValue(b.getString("titel")+System.getProperty("line.separator")+"Rating:"+b.getString("rating"));


                                    }else{

                                        favourites.add(b.getString("titel")+System.getProperty("line.separator")+"Rating:"+b.getString("rating"));
                                        myRef.setValue(favourites);
                                        System.out.println("nothing in firebase database");
                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            };
                            myRef.addListenerForSingleValueEvent(postListener);

                            //myRef.setValue(b.getString("titel")+System.getProperty("line.separator")+"Rating:"+b.getString("rating"));









                            sql.setFavourites(b.getString("titel")+System.getProperty("line.separator")+"Rating:"+b.getString("rating"));


                            Toast.makeText(restaurant_view.this,"Favourited",Toast.LENGTH_LONG).show();
                        }});
                }


            });
        }catch (Exception e){
            System.out.println(e);
        }}


        public double calculate_distance (double lat1, double lat2, double lon1, double lon2){
            Double el1 = 0.0;
            Double el2 = 0.0;
            final int R = 6371; // Radius of the earth

            double latDistance = Math.toRadians(lat2 - lat1);
            double lonDistance = Math.toRadians(lon2 - lon1);
            double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                    + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                    * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            double distance = R * c * 1000; // convert to meters

            double height = el1 - el2;

            distance = Math.pow(distance, 2) + Math.pow(height, 2);
            System.out.println(Math.sqrt(distance));
            return Math.sqrt(distance);


    }

        }


