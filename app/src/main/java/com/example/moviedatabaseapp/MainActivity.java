package com.example.moviedatabaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("MainActivity","Inside onCreate()");
        setupDatabase();
        //sqlDB.execSQL("delete from "+ DatabaseHelper.ACTORS_TABLE);
        //sqlDB.execSQL("delete from "+ DatabaseHelper.MOVIES_TABLE);
        populateActorsTable();
        populateMovieTable();
        queryActorsTable();

    }//protected void onCreate(Bundle savedInstanceState)

    protected void setupDatabase()
    {
        Log.i("MainActivity","Inside setupDatabase()");
        // INIT OUR DATABASE HELPER - This creates our Database with relevant Tables
        dbHelper = new DatabaseHelper(this);

        // RETRIEVE A READABLE AND WRITABLE DATABASE
        sqlDB = dbHelper.getWritableDatabase();

    }   //protected void setupDatabase()

    protected void addRecordToActorsTable(String name,String image, String imdb)
    {
        ContentValues cv_values = new ContentValues();
        cv_values.put(DatabaseHelper.NAME,name);

        //ContentValues cv_image = new ContentValues();
        cv_values.put(DatabaseHelper.IMAGE,image);

        //ContentValues cv_imdb = new ContentValues();
        cv_values.put(DatabaseHelper.IMDB,imdb);

        //CALL INSERT METHOD
        sqlDB.insert(DatabaseHelper.ACTORS_TABLE,null,cv_values);

    }//protected void addRecordToActorsTable(String name,String image, String imdb)

    protected void addRecordToMoviesTable(String title,String year, String genre,String actor)
    {
        ContentValues cv_values = new ContentValues();
        cv_values.put(DatabaseHelper.TITLE,title);

        //ContentValues cv_image = new ContentValues();
        cv_values.put(DatabaseHelper.YEAR,year);

        //ContentValues cv_imdb = new ContentValues();
        cv_values.put(DatabaseHelper.GENRE,genre);

        //ContentValues cv_imdb = new ContentValues();
        cv_values.put(DatabaseHelper.ACTOR,actor);

        //CALL INSERT METHOD
        sqlDB.insert(DatabaseHelper.MOVIES_TABLE,null,cv_values);

    }//protected void addRecordToMoviesTable(String title,String year, String genre,String actor)

    protected void populateActorsTable()
    {

        addRecordToActorsTable("Viggo Mortensen","viggo.jpg","121234");
        addRecordToActorsTable("Charlize Theron","charlize.jpg","434343");
        addRecordToActorsTable("Kyle MacLachlan","kyle.jpg","565566");


    }//protected void populateActorsTable()

    protected void populateMovieTable()
    {
        addRecordToMoviesTable("Hidalgo","2004","Western","Viggo Mortensen");
        addRecordToMoviesTable("Twin Peaks","1991","Mystery","Kyle MacLachlan");
        addRecordToMoviesTable("Snow White and the Huntsman","2012","Fantasy","Charlize Theron");
        addRecordToMoviesTable("Alatriste","2006","Adventure","Viggo Mortensen");
        addRecordToMoviesTable("Roswell","1994","Sci-fi","Kyle MacLachlan");
        addRecordToMoviesTable("Head in the Clouds","2004","Romance","Charlize Theron");
        addRecordToMoviesTable("LOTR:Fellowship of the Ring","2001","Fantasy","Viggo Mortensen");
        addRecordToMoviesTable("The Road","2009","Drama","Charlize Theron");
        addRecordToMoviesTable("Dune","1984","Sci-fi","Kyle MacLachlan");
    }


    protected void queryActorsTable()
    {
        Cursor cursor = sqlDB.query(DatabaseHelper.ACTORS_TABLE, new String[] {
                DatabaseHelper.UID, DatabaseHelper.NAME,DatabaseHelper.IMAGE ,DatabaseHelper.IMDB},
                null,null,null,null,null);
        while(cursor.moveToNext())
        {
          //GET COLUMN INDICES AND VALUES
          int id =cursor.getInt(cursor.getColumnIndex(DatabaseHelper.UID));
          String name=cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME));
          String img=cursor.getString(cursor.getColumnIndex(DatabaseHelper.IMAGE));
          String imdb=cursor.getString(cursor.getColumnIndex(DatabaseHelper.IMDB));
          Log.i("Query Actors DB: ","ROW " + id + " HAS NAME " + name +" HAS IMAGE " + img + " ,IMDB: " + imdb);
        }
        cursor.close();
    } //protected void queryActorsTable()

  }//public class MainActivity extends AppCompatActivity
