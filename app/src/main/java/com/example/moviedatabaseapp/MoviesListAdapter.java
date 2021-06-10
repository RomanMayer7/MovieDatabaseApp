package com.example.moviedatabaseapp;

import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import java.util.ArrayList;

public class MoviesListAdapter extends BaseAdapter
{
    private Activity context;
    ArrayList<MovieModel> movies;
    private PopupWindow pwindo;
    DatabaseHelper db;
    BaseAdapter ba;
    public MoviesListAdapter(Activity context, ArrayList movies,DatabaseHelper db) {
        this.context = context;
        this.movies=movies;
        this.db=db;
    }
    public static class ViewHolder
    {
        TextView textViewId;
        TextView textViewTitle;
        TextView textViewYear;
        TextView textViewGenre;
        TextView textViewActor;
        Button editButton;
        Button deleteButton;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            row = inflater.inflate(R.layout.m_row_item, null, true);
            vh.textViewId = (TextView) row.findViewById(R.id.textViewId);
            vh.textViewTitle = (TextView) row.findViewById(R.id.textViewTitle);
            vh.textViewYear = (TextView) row.findViewById(R.id.textViewYear);
            vh.textViewGenre = (TextView) row.findViewById(R.id.textViewGenre);
            vh.textViewActor = (TextView) row.findViewById(R.id.textViewActor);
            vh.editButton = (Button) row.findViewById(R.id.edit);
            vh.deleteButton = (Button) row.findViewById(R.id.delete);

            // store the holder with the view.
            row.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.textViewId.setText("" + movies.get(position).getId());
        vh.textViewTitle.setText(movies.get(position).getTitle());
        vh.textViewYear.setText("" + movies.get(position).getYear());
        vh.textViewGenre.setText("" + movies.get(position).getGenre());
        vh.textViewActor.setText("" + movies.get(position).getActor());
        final int positionPopup = position;
        vh.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Edit: ", "" + positionPopup);
                editPopup(positionPopup);
            }
        });

        vh.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Last Index", "" + positionPopup);
                Integer index = (Integer) view.getTag();
                db.deleteMovie(movies.get(positionPopup));

                movies.remove(index.intValue());
                movies = (ArrayList) db.getAllMovies();
                Log.d("Country size", "" + movies.size());
                notifyDataSetChanged();
            }

        });


        return row;
    } //public View getView(int position, View convertView, ViewGroup parent)


    public long getItemId(int position) {
        return position;
    }
    public Object getItem(int position) {
        return position;
    }
    public int getCount() {
        return movies.size();
    }


    public void editPopup(final int positionPopup)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View layout = inflater.inflate(R.layout.edit_popup,
                      (ViewGroup) context.findViewById(R.id.popup_element));
        pwindo = new PopupWindow(layout, 700, 800, true);
        pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
        final EditText titleEdit = (EditText) layout.findViewById(R.id.editTextTitle);
        final EditText actorEdit = (EditText) layout.findViewById(R.id.editTextActor);
        final EditText yearEdit = (EditText) layout.findViewById(R.id.editTextYear);
        final EditText genreEdit = (EditText) layout.findViewById(R.id.editTextGenre);
        titleEdit.setText(movies.get(positionPopup).getTitle());
        actorEdit.setText("" + movies.get(positionPopup).getActor());
        yearEdit.setText("" + movies.get(positionPopup).getYear());
        genreEdit.setText("" + movies.get(positionPopup).getGenre());

        Log.d("Title: ", "" + movies.get(positionPopup).getTitle());
        Button save = (Button) layout.findViewById(R.id.save_popup);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titleStr = titleEdit.getText().toString();
                String actorStr = actorEdit.getText().toString();
                String yearStr = yearEdit.getText().toString();
                String genreStr = genreEdit.getText().toString();

                MovieModel movie = movies.get(positionPopup);
                movie.setTitle(titleStr);
                movie.setActor(actorStr);
                movie.setYear(Integer.parseInt(yearStr));
                movie.setGenre(genreStr);


                db.updateMovie(movie);
                movies = (ArrayList) db.getAllMovies();
                notifyDataSetChanged();
                for (MovieModel movie1 : movies) {
                    String log = "Id: " + movie1.getId() + " ,Title: " + movie1.getTitle() + " ,Year: " + movie1.getYear();
                    // Writing Movies to log
                    Log.d("Movie: ", log);
                }
                pwindo.dismiss();
            }
        });
    } // public void editPopup(final int positionPopup)
}
