package com.example.moviedatabaseapp;
import static android.R.attr.name;


public class ActorModel {
    int id;
    String fullName;
    String Image;
    String IMDB;

    public ActorModel() {
        super();
    }
    // constructors
    public ActorModel(int i, String _fullName, String _Image,String _IMDB) {
        super();
        this.id = i;
        this.fullName = _fullName;
        this.Image = _Image;
        this.IMDB = _IMDB;
    }


    public ActorModel(String _fullName, String _Image,String _IMDB) {
        this.fullName = _fullName;
        this.Image = _Image;
        this.IMDB = _IMDB;
    }
   //setters and getters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String _fullName) {
        this.fullName = _fullName;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String _Image) {
        this.Image = _Image;
    }

    public String getIMDB() {
        return IMDB;
    }

    public void setIMDB(String _IMDB) {
        this.IMDB = _IMDB;
    }
}