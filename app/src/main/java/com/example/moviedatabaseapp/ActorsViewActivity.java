package com.example.moviedatabaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ActorsViewActivity extends AppCompatActivity {
    List<ActorModel> actorsList;
    ActorModel selectedActor;
    int selectedActorID;
    TextView actorNameTextView;
    TextView imdbTextView;
    TextView resultTextView;
    Button nextDBButton;
    ImageView actorImgView;
    Activity activity;
    DatabaseHelper db;
    Thread thread;
    String thePage = "";
    String resultStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actors_view);
        activity=this;
        //actorsList = Arrays.asList("charlize_theron.jpg", "kyle.jpg", "viggo.jpg");
        db = new DatabaseHelper(this);
        actorsList  = (ArrayList<ActorModel>) db.getAllActors();
        selectedActorID=0;
        selectedActor = actorsList.get(selectedActorID);
        actorImgView = findViewById(R.id.actorImgView);
        int resID = getResources().getIdentifier( removeExtension(selectedActor.Image),"drawable", getPackageName());
        actorImgView.setImageResource(resID);

        actorNameTextView = findViewById(R.id.actorNameTextView);
        actorNameTextView.setText(selectedActor.fullName);
        imdbTextView = findViewById(R.id.imdbTextView);
        imdbTextView.setText(selectedActor.IMDB);

        resultTextView = findViewById(R.id.resultTextView);

        setupControls();
        resultTextView.setText("Please Wait...");
        setupThread();

    }

    protected void setupControls()
    {
        nextDBButton = findViewById(R.id.nextDBButton);
        nextDBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nextActor();
                resultTextView.setText("Please Wait...");
                setupThread();
            }
        });

    } // protected void setupControls()

    protected void nextActor()
    {
       // Log.i("Selected Actor :",selectedActorID+"");
        //selectedActorID= (selectedActorID++)%actorsList.size();
        selectedActorID++;
        selectedActorID = selectedActorID%(actorsList.size());
       // Log.i("After:",selectedActorID+"");
        selectedActor = actorsList.get(selectedActorID);

        int resID = getResources().getIdentifier( removeExtension(selectedActor.Image),"drawable", getPackageName());
        actorImgView.setImageResource(resID);

        actorNameTextView.setText(selectedActor.fullName);
        imdbTextView.setText(selectedActor.IMDB);


    } //protected void nextActor()

    String removeExtension(String fileName)
    {
        if (fileName.indexOf(".") > 0)
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
        return fileName;
    }

    protected void setupThread()
    {
        thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    scrapeTheWebsite();
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            }
        });
        thread.start(); // this line is very important!!!
    } // protected void setupThread()

    protected void scrapeTheWebsite()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    String urlString = "https://www.imdb.com/name/"+selectedActor.getIMDB()+"/";
                    Document doc = Jsoup.connect(urlString).get();
                    String title = doc.title();
                    Elements links = doc.select("td"); // search for relevant data in html
                    resultStr=""; //reset
                    resultStr = title + "\n";
                    for (Element link : links)
                    {
                        resultStr = resultStr + "text = " + link.text() + "\n";
                    }

                }
                catch (IOException e)
                {
                    resultStr = "Error: this went wrong, error = " + e.getMessage() + "\n";
                }
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {

                        resultTextView.setText( resultStr );
                    }
                });
            } //public void run()
        }).start();
    } // protected void scrapeTheWebsite()

}