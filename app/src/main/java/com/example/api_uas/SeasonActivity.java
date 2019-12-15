package com.example.api_uas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class SeasonActivity extends AppCompatActivity {

    Integer YEAR = 2019;
    String SEASON = "fall";
    ListView listSeason;
    ProgressBar loader;


    ArrayList<HashMap<String, String>> dataList = new ArrayList<>();
    static final String KEY_TITLE = "title";
    static final String KEY_IMAGEURL = "image_url";
    static final String KEY_EPISODES = "episodes";
    static final String KEY_SCORE = "score";
    static final String KEY_STUDIO = "name";
    static final String KEY_URL = "url";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season);

        listSeason = findViewById(R.id.listNews);
        loader = findViewById(R.id.loader);
        listSeason.setEmptyView(loader);


        if (Function.isNetworkAvailable(getApplicationContext())) {
            DownloadSeason newsTask = new DownloadSeason();
            newsTask.execute();
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }

    }

    class DownloadSeason extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() { super.onPreExecute(); }

        protected String doInBackground(String... args) {
            String call = Function.excuteGet("https://api.jikan.moe/v3/season/" + YEAR + "/" + SEASON);
            return call;
        }

        @Override
        protected void onPostExecute(String call) {

            if (call.length() > 10) { // Just checking if not empty

                try {
                    JSONObject root = new JSONObject(call);
                    JSONArray animeArray = root.getJSONArray("anime");

                    for (int i = 0; i < animeArray.length(); i++) {
                        JSONObject animeObject = animeArray.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<>();
                        map.put(KEY_TITLE, animeObject.getString(KEY_TITLE));
                        map.put(KEY_IMAGEURL, animeObject.getString(KEY_IMAGEURL));
                        map.put(KEY_EPISODES, animeObject.getString(KEY_EPISODES));
                        map.put(KEY_URL, animeObject.getString(KEY_URL));
                        map.put(KEY_SCORE, animeObject.getString(KEY_SCORE));

                        JSONArray producersArray = animeObject.getJSONArray("producers");
                        JSONObject producersObject = producersArray.getJSONObject(0);
                        map.put(KEY_STUDIO, producersObject.getString(KEY_STUDIO));

                        dataList.add(map);
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Unexpected error", Toast.LENGTH_SHORT).show();
                }

                ListSeasonAdapter adapter = new ListSeasonAdapter(SeasonActivity.this, dataList);
                listSeason.setAdapter(adapter);

                listSeason.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Intent i = new Intent(SeasonActivity.this, SeasonDetailActivity.class);
                        i.putExtra("url", dataList.get(+position).get(KEY_URL));
                        startActivity(i);
                    }
                });

            } else {
                Toast.makeText(getApplicationContext(), "No news found", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
