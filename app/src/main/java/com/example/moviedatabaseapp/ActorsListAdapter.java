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

public class ActorsListAdapter extends BaseAdapter
{
    private Activity context;
    ArrayList<ActorModel> actors;
    private PopupWindow pwindo;
    DatabaseHelper db;
    BaseAdapter ba;
    public ActorsListAdapter(Activity context, ArrayList actors,DatabaseHelper db) {
        this.context = context;
        this.actors=actors;
        this.db=db;
    }
    public static class ViewHolder
    {
        TextView textViewId;
        TextView textViewName;
        TextView textViewImage;
        TextView textViewIMDB;
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
            row = inflater.inflate(R.layout.row_item, null, true);
            vh.textViewId = (TextView) row.findViewById(R.id.textViewId);
            vh.textViewName = (TextView) row.findViewById(R.id.textViewName);
            vh.textViewImage = (TextView) row.findViewById(R.id.textViewImage);
            vh.textViewIMDB = (TextView) row.findViewById(R.id.textViewIMDB);
            vh.editButton = (Button) row.findViewById(R.id.edit);
            vh.deleteButton = (Button) row.findViewById(R.id.delete);

            // store the holder with the view.
            row.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.textViewId.setText("" + actors.get(position).getId());
        vh.textViewName.setText(actors.get(position).getFullName());
        vh.textViewImage.setText("" + actors.get(position).getImage());
        vh.textViewIMDB.setText("" + actors.get(position).getIMDB());
        final int positionPopup = position;
        vh.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Save: ", "" + positionPopup);
                //editPopup(positionPopup);
            }
        });

        vh.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Last Index", "" + positionPopup);
                Integer index = (Integer) view.getTag();
                //db.deleteActor(actors.get(positionPopup));

                actors.remove(index.intValue());
                actors = (ArrayList) db.getAllActors();
                Log.d("Actors Table size", "" + actors.size());
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
        return actors.size();
    }

}
