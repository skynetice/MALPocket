package com.example.api_uas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchResultActivity extends AppCompatActivity {


    String APIKEY = "xOXKDVZLmHf8u9gQGJMbi64it";
    String QUERY = "";
    ListView listSeason;
    ProgressBar loader;


    ArrayList<HashMap<String, String>> dataList = new ArrayList<>();
    static final String KEY_TITLE = "title";
    static final String KEY_IMAGEURL = "img";
    static final String KEY_URL = "url";
    static final String KEY_STATUS = "status";
    static final String KEY_SCORE = "score";
    static final String KEY_VIEW = "view";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        listSeason = findViewById(R.id.listView);
        loader = findViewById(R.id.loader);
        listSeason.setEmptyView(loader);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle!=null)
        {
            String query =(String) bundle.get("QUERY");
            QUERY = query;

        }


        if (Function.isNetworkAvailable(getApplicationContext())) {
            DownloadSearch newsTask = new DownloadSearch();
            newsTask.execute();
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }

    }

    class DownloadSearch extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() { super.onPreExecute(); }

        protected String doInBackground(String... args) {
            String call = Function.excuteGet("https://rest.farzain.com/api/animestream.php?apikey=" + APIKEY + "&type=search&q=" + QUERY);
            return call;
        }

        @Override
        protected void onPostExecute(String call) {

            if (call.length() > 10) { // Just checking if not empty

                try {
                    JSONObject root = new JSONObject(call);
                    JSONArray animeArray = root.getJSONArray("result");

                    for (int i = 0; i < animeArray.length(); i++) {
                        JSONObject animeObject = animeArray.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<>();
                        map.put(KEY_TITLE, animeObject.getString(KEY_TITLE));
                        map.put(KEY_IMAGEURL, animeObject.getString(KEY_IMAGEURL));
                        map.put(KEY_URL, animeObject.getString(KEY_URL));


                        JSONObject infoArray = animeObject.getJSONObject("info");
                        map.put(KEY_VIEW, infoArray.getString(KEY_VIEW));
                        map.put(KEY_STATUS, infoArray.getString(KEY_STATUS));
                        map.put(KEY_SCORE, infoArray.getString(KEY_SCORE));

                        dataList.add(map);
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Unexpected error", Toast.LENGTH_SHORT).show();
                }

                ListSearchAdapter adapter = new ListSearchAdapter(SearchResultActivity.this, dataList);
                listSeason.setAdapter(adapter);

                listSeason.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(dataList.get(+position).get(KEY_URL)));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setPackage("com.android.chrome");
                        startActivity(intent);
                    }
                });

            } else {
                Toast.makeText(getApplicationContext(), "No anime found", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
