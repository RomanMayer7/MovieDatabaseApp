package com.example.moviedatabaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {

    String htmltext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        TextView myTextview = (TextView) findViewById(R.id.htmlTextView);
        htmltext = "<p>Main Menu includes following sections:</p>"+
                    "<b>Actors</b>"+
                    "<div>Access actor's profile information"+
                   " obtained through IMDB by using JSoup library.</div>"+
                   "<b>Search DB</b>" +
                   "<div>Search Movies database and filter results "+
                   "by Title,Year,Actor and Genre.</div>"+
                   "<b>Add Record</b>" +
                   "<div>Add new movie record.</div>"+
                   "<b>About</b>" +
                   "<div>Check App Version.</div>";

        Spanned sp = Html.fromHtml(htmltext);
        myTextview.setText(sp);
    }
}