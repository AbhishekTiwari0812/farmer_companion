package com.example.abhishek.farmer_companion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    static final String CROP_NAME = "CROP_NAME";

    //static final String WEATHER_API_KEY = "299bb9cfeb7af14e2de5cf2ff023a86f";

    @Override
    /*
    *   App opener activity.
    *   After splash activity, control reaches here.
    * */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_page);
        // wheat page opening button
        // Open crop page.
        // intent data sends info about the page to be opened
        Button wheatPage = (Button) findViewById(R.id.button1);
        wheatPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CropPage.class);
                intent.putExtra(CROP_NAME, "wheat");
                startActivity(intent);
            }
        });
        Button paddyPage = (Button) findViewById(R.id.button2);
        paddyPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CropPage.class);
                intent.putExtra(CROP_NAME, "paddy");
                startActivity(intent);
            }
        });
        Button cottonPage = (Button) findViewById(R.id.button3);
        cottonPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CropPage.class);
                intent.putExtra(CROP_NAME, "cotton");
                startActivity(intent);
            }
        });

    }
}
