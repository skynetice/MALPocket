package com.example.api_uas;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class ListSearchAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;

    public ListSearchAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
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
        ListSearchViewHolder holder = null;
        if (convertView == null) {
            holder = new ListSearchViewHolder();
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.list_search, parent, false);
            holder.galleryImage = (ImageView) convertView.findViewById(R.id.galleryImage);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.status = (TextView) convertView.findViewById(R.id.status);
            holder.score = (TextView) convertView.findViewById(R.id.score);
            holder.view = (TextView) convertView.findViewById(R.id.view);
            convertView.setTag(holder);
        } else {
            holder = (ListSearchViewHolder) convertView.getTag();
        }
        holder.galleryImage.setId(position);
        holder.title.setId(position);
        holder.status.setId(position);
        holder.score.setId(position);
        holder.view.setId(position);

        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);

        try{
            holder.title.setText(song.get(SearchResultActivity.KEY_TITLE));
            holder.status.setText(song.get(SearchResultActivity.KEY_STATUS));
            holder.score.setText(song.get(SearchResultActivity.KEY_SCORE));
            holder.view.setText(song.get(SearchResultActivity.KEY_VIEW));

            if(song.get(SearchResultActivity.KEY_IMAGEURL).toString().length() < 5)
            {
                holder.galleryImage.setVisibility(View.GONE);
            }else{
                Picasso.get()
                        .load(song.get(SearchResultActivity.KEY_IMAGEURL))
                        .resize(300, 200)
                        .centerCrop()
                        .into(holder.galleryImage);
            }
        }catch(Exception e) {}
        return convertView;
    }
}

class ListSearchViewHolder {
    ImageView galleryImage;
    TextView  title, status, score, view;
}
