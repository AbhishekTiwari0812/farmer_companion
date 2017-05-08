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
import android.widget.LinearLayout;
import android.widget.TextView;

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

        boolean isPunjabi = false;
        SharedPreferences preferences = getSharedPreferences(OneTimeActivity.PREF_FILE, MODE_PRIVATE);
        String flag = preferences.getString(OneTimeActivity.PREF_LANG, OneTimeActivity.ENGLISH);
        if (flag.compareTo(OneTimeActivity.ENGLISH) != 0) {
            isPunjabi = true;
        }

        if (isPunjabi) {
            TextView tv = (TextView) findViewById(R.id.string_wheat);
            tv.setText("ਕਣਕ");
            tv = (TextView) findViewById(R.id.string_paddy);
            tv.setText("ਝੋਨਾ");
            tv = (TextView) findViewById(R.id.string_cotton);
            tv.setText("ਕਪਾਹ");

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
            intent.putExtra(CROP_NAME, cropName);
            startActivity(intent);
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
                finish();
                //android.os.Process.killProcess(android.os.Process.myPid());
                //System.exit(1);
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
