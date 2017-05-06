package com.example.abhishek.farmer_companion;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    static final String CROP_NAME = "CROP_NAME";

    @Override
    /*
    *   App opener activity.
    *   After splash activity, control reaches here.
    * */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.side_drawer_main);

        LinearLayout main_page_layout = (LinearLayout) findViewById(R.id.activity_main);
        main_page_layout.setVisibility(View.VISIBLE);
        LinearLayout crop_page_layout = (LinearLayout) findViewById(R.id.activity_crop_main);
        crop_page_layout.setVisibility(View.GONE);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        LinearLayout wheatPage = (LinearLayout) findViewById(R.id.layout_wheat_crop);
        wheatPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CropPage.class);
                intent.putExtra(CROP_NAME, "wheat");
                startActivity(intent);
            }
        });
        LinearLayout paddyPage = (LinearLayout) findViewById(R.id.layout_paddy_crop);
        paddyPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CropPage.class);
                intent.putExtra(CROP_NAME, "paddy");
                startActivity(intent);
            }
        });
        LinearLayout cottonPage = (LinearLayout) findViewById(R.id.layout_cotton_crop);
        cottonPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CropPage.class);
                intent.putExtra(CROP_NAME, "cotton");
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String cropName = null;
        if (id == R.id.wheat_crop) {
            cropName = "wheat";
        } else if (id == R.id.paddy_crop) {
            cropName = "paddy";
        } else if (id == R.id.cotton_crop) {
            cropName = "cotton";
        }
        Intent intent = new Intent(getApplicationContext(), CropPage.class);
        intent.putExtra(CROP_NAME, cropName);
        startActivity(intent);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
