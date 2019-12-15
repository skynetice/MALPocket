package com.example.api_uas;

import android.widget.BaseAdapter;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class ListSeasonAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;

    public ListSeasonAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
    }
    public int getCount() {
        return data.size();
    }
    public Object getItem(int position) {
        return position;
    }
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        ListNewsViewHolder holder = null;
        if (convertView == null) {
            holder = new ListNewsViewHolder();
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.list_row, parent, false);
            holder.galleryImage = (ImageView) convertView.findViewById(R.id.galleryImage);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.episode = (TextView) convertView.findViewById(R.id.episode);
            holder.score = (TextView) convertView.findViewById(R.id.score);
            holder.studio = (TextView) convertView.findViewById(R.id.studio);
            convertView.setTag(holder);
        } else {
            holder = (ListNewsViewHolder) convertView.getTag();
        }
        holder.galleryImage.setId(position);
        holder.title.setId(position);
        holder.episode.setId(position);
        holder.score.setId(position);
        holder.studio.setId(position);

        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);

        try{
            holder.title.setText(song.get(SeasonActivity.KEY_TITLE));
            holder.episode.setText(song.get(SeasonActivity.KEY_EPISODES));
            holder.score.setText(song.get(SeasonActivity.KEY_SCORE));
            holder.studio.setText(song.get(SeasonActivity.KEY_STUDIO));

            if(song.get(SeasonActivity.KEY_IMAGEURL).toString().length() < 5)
            {
                holder.galleryImage.setVisibility(View.GONE);
            }else{
                Picasso.get()
                        .load(song.get(SeasonActivity.KEY_IMAGEURL))
                        .resize(300, 200)
                        .centerCrop()
                        .into(holder.galleryImage);
            }
        }catch(Exception e) {}
        return convertView;
    }
}

class ListNewsViewHolder {
    ImageView galleryImage;
    TextView  title, episode, score, studio;
}
