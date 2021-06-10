package com.example.moviedatabaseapp;
import static android.R.attr.name;


public class MovieModel {
    int id;
    String title;
    int year;
    String genre;
    String actor;

    public MovieModel() {
        super();
    }

    // constructors
    public MovieModel(int i, String _title, int _year,String _genre, String _actor) {
        super();
        this.id = i;
        this.title = _title;
        this.year = _year;
        this.genre =_genre;
        this.actor =_actor;
    }


    public MovieModel(String _title, int _year,String _genre, String _actor) {
        this.title = _title;
        this.year = _year;
        this.genre =_genre;
        this.actor =_actor;
    }
    //setters and getters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String _title) {
        this.title = _title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int _year) {
        this.year = _year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String _genre) {
        this.genre = _genre;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String _actor) {
        this.actor = _actor;
    }
}