package com.example.api_uas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class SearchPageActivity extends AppCompatActivity {

    private Button buttonSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        buttonSearch = findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSearch();
            }
        });

    }

    public void openSearch(){
        EditText nameField = (EditText) findViewById(R.id.search_field);
        Editable nameEditable = nameField.getText();
        String query = nameEditable.toString();

        Intent intent = new Intent(this, SearchResultActivity.class);
        intent.putExtra("QUERY", query);
        startActivity(intent);
    }

    public void search(View view) {
        EditText nameField = (EditText) findViewById(R.id.search_field);
        Editable nameEditable = nameField.getText();
        String query = nameEditable.toString();

        Intent intent = new Intent(this, SearchResultActivity.class);
        intent.putExtra("QUERY", query);
        startActivity(intent);
    }


}
