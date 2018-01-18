package com.example.rian.idc;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rian on 20/10/2017.
 */

public class favourite_view extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourite_view);
        //sqlController sql = new sqlController(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Rian");
        final List<String> keyoffav = new ArrayList<>();
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> favouritesarray = new ArrayList<>();

                for (DataSnapshot child: dataSnapshot.getChildren()){
                    favouritesarray.add(child.getValue(String.class));
                    keyoffav.add(child.getKey());
                    System.out.println(child.getKey());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(), android.R.layout.simple_list_item_1, favouritesarray);
                getListView().setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        myRef.addListenerForSingleValueEvent(postListener);
        myRef.onDisconnect();





        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                //sqlController s = new sqlController(favourite_view.this);
                String selectedFromList = (String) getListView().getItemAtPosition(i);



                ValueEventListener postListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        for (DataSnapshot child: dataSnapshot.getChildren()){
                            System.out.println(child.getValue(String.class).getClass().getName());
                            if (child.getValue(String.class) == getListView().getItemAtPosition(i)){
                                System.out.println("It's A UGE FUCKING MATCH");
                            }

                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                System.out.println(myRef.child(keyoffav.get(i)).removeValue());



                //s.deletefavourits((String) getListView().getItemAtPosition(i));
                Toast.makeText(favourite_view.this,"Deleted",Toast.LENGTH_LONG).show();
                Intent intent = getIntent();
                finish();
                startActivity(intent);


            }
        });
    }


}
