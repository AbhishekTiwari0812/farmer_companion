package com.example.abhishek.farmer_companion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Abhishek on 04-10-2016.
 */

public class CropPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crop_page);
        final String cropName = getIntent().getExtras().getString("CROP_NAME");
        LinearLayout whetherAndTiming = (LinearLayout) findViewById(R.id.weather_and_timing_layout);

        Button queryAsker = (Button) findViewById(R.id.query_ask_button);
        queryAsker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QueryPage.class);
                // TODO: Add other tags.
                intent.putExtra("CropName", cropName);
                startActivity(intent);
            }
        });

        whetherAndTiming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SlideViewer.class);
                intent.putExtra("CROP_SECTION", cropName + "_0_");
                startActivity(intent);
            }
        });

        LinearLayout soilPrep = (LinearLayout) findViewById(R.id.layout_seed_soil);
        soilPrep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SlideViewer.class);
                intent.putExtra("CROP_SECTION", cropName + "_1_");
                startActivity(intent);
            }
        });

        LinearLayout plantation = (LinearLayout) findViewById(R.id.layout_platation);
        plantation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SlideViewer.class);
                intent.putExtra("CROP_SECTION", cropName + "_2_");
                startActivity(intent);
            }
        });
        LinearLayout diseases = (LinearLayout) findViewById(R.id.layout_diseases);
        diseases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SlideViewer.class);
                intent.putExtra("CROP_SECTION", cropName + "_3_");
                startActivity(intent);
            }
        });
        LinearLayout harvesting = (LinearLayout) findViewById(R.id.layout_harvesting);
        harvesting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SlideViewer.class);
                intent.putExtra("CROP_SECTION", cropName + "_4_");
                startActivity(intent);
            }
        });
        LinearLayout storage = (LinearLayout) findViewById(R.id.layout_storage);
        storage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SlideViewer.class);
                intent.putExtra("CROP_SECTION", cropName + "_5_");
                startActivity(intent);
            }
        });


    }

}
