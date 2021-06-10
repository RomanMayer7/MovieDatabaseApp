package com.example.moviedatabaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    //ArrayList<ActorModel> actors;
    ArrayList<MovieModel> movies;
    DatabaseHelper db;
    Button btnSubmit;
    PopupWindow pwindo;
    Activity activity;
    ListView moviesList;
    MoviesListAdapter moviesListAdapter;

    private Spinner spinner;
    private static final String[] paths = {"Title", "Year", "Genre","Actor"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        activity = this;
        db = new DatabaseHelper(this);
        //actorsList = (ListView) findViewById(R.id.actorsList);
        moviesList = (ListView) findViewById(R.id.moviesList);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MainActivity: ", "Submit Clicked");
            }
        });

        Log.d("MainActivity: ", "Before reading mainactivity");
        //actors = (ArrayList<ActorModel>) db.getAllActors();

        movies = (ArrayList<MovieModel>) db.getAllMovies();

        Log.d("MainActivity: ", "Movies DB size:"+movies.size());

        for (MovieModel movie : movies) {
            String log = "Id: " + movie.getId() + " ,Title: " + movie.getTitle() + " ,Year: " + movie.getYear() + " ,Genre: " + movie.getGenre();
            Log.d("Name: ", log);
        }

        moviesListAdapter = new MoviesListAdapter(this,movies,db);
        moviesList.setAdapter(moviesListAdapter);

        spinner = (Spinner)findViewById(R.id.searchTerm);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SearchActivity.this,
                R.layout.spinner_item,paths);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
       // spinner.setOnItemSelectedListener(this);

    } //protected void onCreate(Bundle savedInstanceState)
}