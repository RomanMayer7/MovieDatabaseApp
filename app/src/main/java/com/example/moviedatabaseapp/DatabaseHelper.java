package com.example.moviedatabaseapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movie_database.db";
    private static final int DATABASE_VERSION = 1 ;

    //Tables-------------------------------------------------
    public static final String ACTORS_TABLE ="actors";
    //Fields in ACTORS_TABLE
    public static final String UID = "_id";
    public static final String NAME = "full_name";
    public static final String IMAGE = "image";
    public static final String IMDB = "imdb";

    public static final String MOVIES_TABLE ="movies";
    //Fields in MOVIES_TABLE
    public static final String TITLE = "title";
    public static final String YEAR = "year";
    public static final String GENRE = "genre";
    public static final String ACTOR = "actor";

    DatabaseHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        dropDBTables(db);
        db.execSQL("CREATE TABLE " + ACTORS_TABLE + " (" + UID +
                   " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME +
                     " VARCHAR(255)," + IMAGE +" VARCHAR(255)," +
                     IMDB+ " VARCHAR(255));");

        db.execSQL("CREATE TABLE " + MOVIES_TABLE + " (" + UID +
                " INTEGER PRIMARY KEY AUTOINCREMENT," + TITLE +
                " VARCHAR(255)," + YEAR +" INTEGER," +
                  GENRE+ " VARCHAR(255),"+ACTOR+" VARCHAR(255));");

    } // public void onCreate(SQLiteDatabase db)

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.w("LOG_TAG","Upgrading database from version " +
                oldVersion + " to "
                + newVersion + ", which will destroy all old data");

        //Drop Tables on UPGRADE
        dropDBTables(db);
        // RECREATE DB and Tables
        onCreate(db);

    } //public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)

    public void dropDBTables(SQLiteDatabase db)
    {
        db.execSQL("DROP TABLE IF EXISTS " + ACTORS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MOVIES_TABLE);
    }

    /**
     *************** All CRUD(Create, Read, Update, Delete) Operations ****************************************
     */

    // ================Adding new Actor======================
    void addActor(ActorModel actor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, actor.getFullName()); // Actor's Full Name Name
        values.put(IMAGE, actor.getImage()); // Actor's Image
        values.put(IMDB, actor.getIMDB()); // Actor's Profile in IMDB

        // Inserting Row
        db.insert(ACTORS_TABLE, null, values);
        db.close(); // Closing database connection
    } //void addActor(ActorModel actor)

    //===========Getting single actor=========================
    ActorModel getActor(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(ACTORS_TABLE, new String[]{UID,
                        NAME, IMAGE, IMDB}, UID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ActorModel actor = new ActorModel(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),cursor.getString(3) );
        // return actor
        return actor;
    } // ActorModel getActor(int id)

    // =====================Getting All Actors=====================
    public List getAllActors() {
        List actorsList = new ArrayList();
        // Select All Query
        String selectQuery = "SELECT * FROM " + ACTORS_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ActorModel actor = new ActorModel();
                actor.setId(Integer.parseInt(cursor.getString(0)));
                actor.setFullName(cursor.getString(1));
                actor.setImage(cursor.getString(2));
                actor.setIMDB(cursor.getString(3));
                 // Adding country to list
                actorsList.add(actor);
            } while (cursor.moveToNext());
        }

        return actorsList;
    } //public List getAllActors()
//*****************************************************************************************************
// =================Adding new Movie=================================
void addMovie(MovieModel movie) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(TITLE, movie.getTitle()); // Movie Title
    values.put(YEAR, movie.getYear()); // Year of Release
    values.put(GENRE, movie.getGenre()); // Movie Genre
    values.put(ACTOR, movie.getActor()); // Get Main Actor

    // Inserting Row
    db.insert(MOVIES_TABLE, null, values);
    db.close(); // Closing database connection
} //void addMovie(MovieModel movie)

    //===================Getting specific Movie========================
    MovieModel getMovie(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(MOVIES_TABLE, new String[]{UID,
                        TITLE, YEAR, GENRE,ACTOR}, UID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        MovieModel movie = new MovieModel(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), Integer.parseInt(cursor.getString(2)),
                cursor.getString(3),cursor.getString(4) );
        // return movie
        return movie;
    } //MovieModel getMovie(int id)

    // ======================Getting All Movies=============================
    public List getAllMovies() {
        Log.i("MainActivity","Inside getAllMovies()");
        List moviesList = new ArrayList();
        // Select All Query
        String selectQuery = "SELECT * FROM " + MOVIES_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MovieModel movie = new MovieModel();
                movie.setId(Integer.parseInt(cursor.getString(0)));
                movie.setTitle(cursor.getString(1));
                movie.setYear(Integer.parseInt(cursor.getString(2)));
                movie.setGenre(cursor.getString(3));
                movie.setActor(cursor.getString(4));
                Log.i("getAllMovies",movie.getTitle());
                // Adding country to list
                moviesList.add(movie);
            } while (cursor.moveToNext());
        }

        return moviesList;
    } //public List getAllMovies()

    // ================================Update Movie===========================================
    public int updateMovie(MovieModel movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE, movie.getTitle()); // Movie Title
        values.put(YEAR, movie.getYear()); // Year of Release
        values.put(GENRE, movie.getGenre()); // Movie Genre
        values.put(ACTOR, movie.getActor()); // Get Main Actor

        // update row
        return db.update(MOVIES_TABLE, values, UID + " = ?",
                         new String[]{String.valueOf(movie.getId())});
    } //public int updateMovie(MovieModel movie)

    // ============================Delete single Movie==========================================
    public void deleteMovie(MovieModel movie)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MOVIES_TABLE, UID + " = ?",
                  new String[] { String.valueOf(movie.getId()) });
        db.close();
    } //public void deleteMovie(MovieModel movie)

}
