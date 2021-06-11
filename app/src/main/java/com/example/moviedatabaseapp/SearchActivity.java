package com.example.moviedatabaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener{

    //ArrayList<ActorModel> actors;
    ArrayList<MovieModel> movies;
    DatabaseHelper db;
    Button btnSubmit;
    PopupWindow pwindo;
    Activity activity;
    ListView moviesList;
    MoviesListAdapter moviesListAdapter;
    int selectedPath; //Spinner's Item selected Index
    EditText input;
    StringBuilder sb;

    private Spinner spinner;
    private static final String[] paths = {"All","Title", "Year", "Genre","Actor"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        activity = this;
        db = new DatabaseHelper(this);
        //actorsList = (ListView) findViewById(R.id.actorsList);
        moviesList = (ListView) findViewById(R.id.moviesList);
        selectedPath=0;
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MainActivity: ", "Submit Clicked");
                switch (selectedPath) {
                    case 0 :
                        Log.d("Submit", "Search : 'All',  ID:"+ selectedPath);
                        movies = (ArrayList<MovieModel>) db.getAllMovies();
                        break ;
                    case 1 :
                        Log.d("Submit: ", "Search: 'Title',  ID:"+ selectedPath);
                        //movies=new ArrayList<>();
                        input =(EditText) findViewById(R.id.editTextSearchInput);
                        sb = new StringBuilder(input.getText().length());
                        sb.append(input.getText());
                        movies = (ArrayList<MovieModel>) db.getMoviesByTitle(sb.toString());
                        break ;
                    case 2 :
                        Log.d("Submit: ", "Search: 'Year',  ID:"+ selectedPath);
                        input =(EditText) findViewById(R.id.editTextSearchInput);
                        sb = new StringBuilder(input.getText().length());
                        sb.append(input.getText());
                        movies = (ArrayList<MovieModel>) db.getMoviesByYear(sb.toString());
                        break ;
                    case 3 :
                        Log.d("Submit: ", "Search: 'Genre',  ID:"+ selectedPath);
                        input =(EditText) findViewById(R.id.editTextSearchInput);
                        sb = new StringBuilder(input.getText().length());
                        sb.append(input.getText());
                        movies = (ArrayList<MovieModel>) db.getMoviesByGenre(sb.toString());

                        break ;
                    case 4 :
                        Log.d("Spinner: ", "Search: 'Actor',  ID:"+ selectedPath);
                        input =(EditText) findViewById(R.id.editTextSearchInput);
                        sb = new StringBuilder(input.getText().length());
                        sb.append(input.getText());
                        movies = (ArrayList<MovieModel>) db.getMoviesByActor(sb.toString());

                        break ;
                }
                moviesListAdapter = new MoviesListAdapter(activity,movies,db);
                moviesList.setAdapter(moviesListAdapter);
            }
        });

        movies = (ArrayList<MovieModel>) db.getAllMovies();

        Log.d("MainActivity: ", "Movies DB size:"+movies.size());

        for (MovieModel movie : movies) {
            String log = "Id: " + movie.getId() + " ,Title: " + movie.getTitle() + " ,Year: " + movie.getYear() + " ,Genre: " + movie.getGenre();
            Log.d("Name: ", log);
        }

        moviesListAdapter = new MoviesListAdapter(this,movies,db);
        moviesList.setAdapter(moviesListAdapter);
       //---------------------------------------------------------------------------------------------------------
        spinner = (Spinner)findViewById(R.id.searchTerm);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SearchActivity.this,
                R.layout.spinner_item,paths);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


    } //protected void onCreate(Bundle savedInstanceState)


    @Override public void onItemSelected(AdapterView<?> parent, View v, int position, long id)
    {
        switch (position) {
            case 0 :
                Log.d("Spinner: ", "Selected: 'All',  ID:"+position);
                selectedPath=position;
                break ;
            case 1 :
                Log.d("Spinner: ", "Selected: 'Title',  ID:"+position);
                selectedPath=position;
                break ;
            case 2 :
                Log.d("Spinner: ", "Selected: 'Year',  ID:"+position);
                selectedPath=position;
                break ;
            case 3 :
                Log.d("Spinner: ", "Selected: 'Genre',  ID:"+position);
                selectedPath=position;
                break ;
            case 4 :
                Log.d("Spinner: ", "Selected: 'Actor',  ID:"+position);
                selectedPath=position;
                break ;
        }
    } //public void onItemSelected(AdapterView<?> parent, View v, int position, long id)

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
// TODO Auto-generated method stub
    } //public void onNothingSelected(AdapterView<?> parent)
}