package com.example.rian.idc;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.nio.file.Files;
import java.io.File;
import java.util.ArrayList;
/**
 * Created by Rian on 19/10/2017.
 */

public class sqlController extends SQLiteOpenHelper{

    public sqlController(Context context) {
        super(context, "Android.db", null, 1);
    }



    public void setFavourites (String hotel){
        String newhotel = hotel.replace("'","");
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO favourite VALUES('"+newhotel+"');");
        System.out.println("inserted=  "+hotel);


    }
    public ArrayList getFavourites(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultSet = db.rawQuery("Select hotels from favourite",null);
        resultSet.moveToFirst();
        ArrayList<String> hotels = new ArrayList<String>();
        while (!resultSet.isAfterLast()){
            System.out.println(resultSet.getString(resultSet.getColumnIndex("hotels")));
            hotels.add(resultSet.getString(resultSet.getColumnIndex("hotels")));
            resultSet.moveToNext();
        }
        return hotels;
    }

    public void deletefavourits(String hotel){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM favourite WHERE hotels='"+hotel+"';");

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS favourite(hotels VARCHAR);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
