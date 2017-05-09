package com.example.abhishek.farmer_companion;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/*
 * Created by Abhishek on 28-11-2016.
 *  Splash screen activity.
 */

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 1500;      // 1.5 seconds.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String internalRestartFlag = intent.getStringExtra("APP_LANG_CHANGE");
        // If app restarts, don't show the splash screen
        if (internalRestartFlag != null) {
            Intent i = new Intent(SplashScreen.this, OneTimeActivity.class);
            startActivity(i);
            finish();
        } else {
            setContentView(R.layout.splash_screen_layout);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(SplashScreen.this, OneTimeActivity.class);
                    startActivity(i);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
    }
}
