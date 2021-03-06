package com.example.moviedatabaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import java.util.ArrayList;

public class MainMenuActivity extends AppCompatActivity {
    Activity activity;
    ArrayList movies;
    DatabaseHelper dbHelper;
    SQLiteDatabase sqlDB;
    Button searchDBButton,actorsDBButton,aboutButton,helpButton,exitButton;
    MoviesListAdapter moviesListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        activity=this;
        setupControls();
        dbHelper = new DatabaseHelper(this);
        sqlDB = dbHelper.getWritableDatabase();
        if(dbHelper.checkIfTableEmpty(DatabaseHelper.ACTORS_TABLE)) {
            populateActorsTable();
        }
        if(dbHelper.checkIfTableEmpty(DatabaseHelper.MOVIES_TABLE)) {
            populateMovieTable();
        }

        Button addRecordButton = (Button) findViewById(R.id.addRecordButton);
        addRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPopUp();
            }
        });
    } //protected void onCreate(Bundle savedInstanceState)

    protected void setupControls()
    {
       searchDBButton = findViewById(R.id.searchDBButton);
        searchDBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

        actorsDBButton = findViewById(R.id.actorsDBButton);
        actorsDBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), ActorsViewActivity.class);
                startActivity(intent);
            }
        });

        aboutButton = findViewById(R.id.aboutButton);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), AboutActivity.class);
                startActivity(intent);
            }
        });

       helpButton = findViewById(R.id.helpButton);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), HelpActivity.class);
                startActivity(intent);
            }
        });

        exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });

    } // protected void setupControls()


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
    } //protected void populateMovieTable()

    protected void addRecordToActorsTable(String actor,String image, String imdb)
    {

        ContentValues cv_values = new ContentValues();
        cv_values.put(DatabaseHelper.NAME,actor);

        //ContentValues cv_image = new ContentValues();
        cv_values.put(DatabaseHelper.IMAGE,image);

        //ContentValues cv_imdb = new ContentValues();
        cv_values.put(DatabaseHelper.IMDB,imdb);


        //CALL INSERT METHOD
        sqlDB.insert(DatabaseHelper.ACTORS_TABLE,null,cv_values);

    }//protected void addRecordToActorsTable(String actor,String image, String imdb)

    protected void populateActorsTable()
    {
        addRecordToActorsTable("Charlize Theron","charlize_theron.jpg","nm0000234");
        addRecordToActorsTable("Kyle MacLachlan","kyle.jpg","nm0001492");
        addRecordToActorsTable("Viggo Mortensen","viggo.jpg","nm0001557");

    } // protected void populateActorsTable()

    public void addPopUp()
    {

        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.edit_popup,
                (ViewGroup) activity.findViewById(R.id.popup_element));
        PopupWindow pwindo = new PopupWindow(layout, 900, 700, true);
        pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);

        final EditText titleEdit = (EditText) layout.findViewById(R.id.editTextTitle);
        final EditText yearEdit = (EditText) layout.findViewById(R.id.editTextYear);
        final EditText actorEdit = (EditText) layout.findViewById(R.id.editTextActor);
        final EditText genreEdit = (EditText) layout.findViewById(R.id.editTextGenre);


        Button save = (Button) layout.findViewById(R.id.save_popup);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titleStr = titleEdit.getText().toString();
                String yearStr  = yearEdit.getText().toString();
                String actorStr = actorEdit.getText().toString();
                String genreStr = genreEdit.getText().toString();

                MovieModel movie = new MovieModel(titleStr, Integer.parseInt(yearStr),genreStr,actorStr);
                dbHelper.addMovie(movie);
                if(moviesListAdapter==null)
                {
                    moviesListAdapter = new MoviesListAdapter(activity, movies, dbHelper);
                }
                pwindo.dismiss();
           }
        });
      } //public void addPopUp()
}