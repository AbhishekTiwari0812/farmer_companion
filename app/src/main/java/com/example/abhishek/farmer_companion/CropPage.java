package com.example.abhishek.farmer_companion;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Abhishek on 04-10-2016.
 * Crop Page ( same for every crop )
 * Intent extra data specifies the crop type.
 * Contains button for weather, weeds, seeding infographics.
 * Query button.
 */

public class CropPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.side_drawer_main);
        LinearLayout crop_page_layout = (LinearLayout) findViewById(R.id.activity_crop_main);
        crop_page_layout.setVisibility(View.VISIBLE);
        LinearLayout main_page_layout = (LinearLayout) findViewById(R.id.activity_main);
        main_page_layout.setVisibility(View.GONE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView sideNavigationView = (NavigationView) findViewById(R.id.nav_view);
        sideNavigationView.setNavigationItemSelectedListener(this);

        // Get the name of the crop
        // To find which infographics to show.
        final String cropName = getIntent().getExtras().getString("CROP_NAME");
        this.setTitle(cropName.toUpperCase().charAt(0) + cropName.substring(1) + " Crop");
        // Set onclick listeners for each button.
        LinearLayout whetherAndTiming = (LinearLayout) findViewById(R.id.weather_and_timing_layout);
        Button queryAsker = (Button) findViewById(R.id.query_ask_button);
        queryAsker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QueryPage.class);
                // TODO: Add other tags.
                // Sends the crop name to query activity.
                intent.putExtra("CropName", cropName);
                startActivity(intent);
            }
        });

        boolean isPunjabi = false;
        SharedPreferences preferences = getSharedPreferences(OneTimeActivity.PREF_FILE, MODE_PRIVATE);
        String flag = preferences.getString(OneTimeActivity.PREF_LANG, OneTimeActivity.ENGLISH);
        if (flag.compareTo(OneTimeActivity.ENGLISH) != 0) {
            isPunjabi = true;
        }
        if (isPunjabi) {

            TextView tv = (TextView) findViewById(R.id.tv_intro_pick_item);
            tv.setText("ਮੀਨੂ ਵਿੱਚੋਂ ਚੁਣੋ ਜਾਂ ਸਵਾਲ ਪੁੱਛੋ");
            tv = (TextView) findViewById(R.id.tv_weather_and_timing);
            tv.setText("ਮੌਸਮ ਅਤੇ ਸਮਾਂ");
            tv = (TextView) findViewById(R.id.tv_seed_variety);
            tv.setText("ਬੀਜ ਦੀ ਕਿਸਮ");
            tv = (TextView) findViewById(R.id.tv_land_and_seed);
            tv.setText("ਜ਼ਮੀਨ ਅਤੇ ਬੀਜ ਦੀ ਤਿਆਰੀ");
            tv = (TextView) findViewById(R.id.tv_fert_and_weed_control);
            tv.setText("ਖਾਦ ਅਤੇ ਬੂਟੀ ਕੰਟਰੋਲ");
            tv = (TextView) findViewById(R.id.tv_harvesting_and_storage);
            tv.setText("ਵਾਢੀ ਅਤੇ ਸਟੋਰੇਜ");
            tv = (TextView) findViewById(R.id.tv_plant_protection);
            tv.setText("ਪੌਦਾ ਸੁਰੱਖਿਆ");
            queryAsker.setText("ਸਵਾਲ ਪੁੱਛੋ");

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            Menu menu = navigationView.getMenu();
            MenuItem tempMenuItem = menu.findItem(R.id.menu_title_crops);
            tempMenuItem.setTitle("ਫ਼ਸਲ");
            tempMenuItem = menu.findItem(R.id.wheat_crop);
            tempMenuItem.setTitle("          ਕਣਕ");
            tempMenuItem = menu.findItem(R.id.paddy_crop);
            tempMenuItem.setTitle("          ਝੋਨਾ");
            tempMenuItem = menu.findItem(R.id.cotton_crop);
            tempMenuItem.setTitle("          ਕਪਾਹ");
            tempMenuItem = menu.findItem(R.id.menu_title_language);
            tempMenuItem.setTitle("ਭਾਸ਼ਾ");
            tempMenuItem = menu.findItem(R.id.tutorial_opener);
            tempMenuItem.setTitle("ਕਿਵੇਂ ਵਰਤਣਾ ਹੈ");
        }
        // Zero based indexing is used to specify which infographics to show.
        // 0 : weather related infographics
        // 1 : Soil preparation related infographics
        // 2 : Plantation related infograpics
        // 3 : Disease related infographics
        // 4 : harvesting related infographics
        // 5 : storage related infographics

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
        String preferredLanguage = null;
        if (id == R.id.wheat_crop) {
            cropName = "wheat";
        } else if (id == R.id.paddy_crop) {
            cropName = "paddy";
        } else if (id == R.id.cotton_crop) {
            cropName = "cotton";
        } else if (id == R.id.lang_english) {
            preferredLanguage = OneTimeActivity.ENGLISH;
        } else if (id == R.id.lang_punjabi) {
            preferredLanguage = OneTimeActivity.PUNJABI;
        } else if (id == R.id.tutorial_opener) {
            Intent intent = new Intent(getApplicationContext(), TutorialSlide.class);
            intent.putExtra("drawer_flag", "true");
            startActivity(intent);
        }

        if (cropName != null) {
            Intent intent = new Intent(getApplicationContext(), CropPage.class);
            intent.putExtra("CROP_NAME", cropName);
            startActivity(intent);
            finish();
        } else if (preferredLanguage != null) {
            SharedPreferences preferences = getSharedPreferences(OneTimeActivity.PREF_FILE, MODE_PRIVATE);
            String prevLang = preferences.getString(OneTimeActivity.PREF_LANG, OneTimeActivity.ENGLISH);
            if (prevLang.compareTo(preferredLanguage) == 0) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            } else {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(OneTimeActivity.PREF_LANG, preferredLanguage);
                editor.commit();
                Intent i = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage(getBaseContext().getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("APP_LANG_CHANGE", "YES");
                startActivity(i);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                finish();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
