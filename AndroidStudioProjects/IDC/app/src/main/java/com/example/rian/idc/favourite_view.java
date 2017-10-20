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

/**
 * Created by Rian on 20/10/2017.
 */

public class favourite_view extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourite_view);
        sqlController sql = new sqlController(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(), android.R.layout.simple_list_item_1, sql.getFavourites());
        getListView().setAdapter(adapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sqlController s = new sqlController(favourite_view.this);
                String selectedFromList = (String) getListView().getItemAtPosition(i);
                System.out.println(getListView().getItemAtPosition(i));
                s.deletefavourits((String) getListView().getItemAtPosition(i));
                Toast.makeText(favourite_view.this,"Deleted",Toast.LENGTH_LONG).show();
                Intent intent = getIntent();
                finish();
                startActivity(intent);


            }
        });
    }


}
