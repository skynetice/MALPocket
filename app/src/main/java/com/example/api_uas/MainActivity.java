package com.example.api_uas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    private Button buttonProfile, buttonCoffee, buttonGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonProfile = findViewById(R.id.button_profile_id);
        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProfile();
            }
        });

        buttonCoffee = findViewById(R.id.button_coffee_id);
        buttonCoffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCoffee();
            }
        });

        buttonGallery = findViewById(R.id.button_gallery_id);
        buttonGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
    }

    public void openProfile(){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void openCoffee(){
        Intent intent = new Intent(this, SeasonActivity.class);
        startActivity(intent);
    }

    public void openGallery(){
        Intent intent = new Intent(this, SearchPageActivity.class);
        startActivity(intent);
    }
}
