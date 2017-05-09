package com.example.abhishek.farmer_companion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * First activity that opens on app icon click.
 */

public class OneTimeActivity extends AppCompatActivity {
    SharedPreferences preferences;      //used to store the language preference.
    static final String PREF_FILE = "FARMER_PREFERENCE";    //file name
    static final String PREF_LANG = "pref_lang";        //language value key
    static final String ENGLISH = "ENG";        //const for English
    static final String PUNJABI = "PUN";        // const for Punjabi


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (appOpenedFirstTime()) {
            // Ask language preference, if the app is opened for the first time.
            setContentView(R.layout.language_option);
            Button english_button = (Button) findViewById(R.id.language_english);
            Button punjabi_button = (Button) findViewById(R.id.language_punjabi);
            english_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setLanguage(ENGLISH);
                    //start the tutorial of the app
                    // after language selection
                    System.out.println("Opening tutorial");
                    Intent intent = new Intent(getApplicationContext(), TutorialSlide.class);
                    startActivity(intent);
                    finish();
                }
            });
            punjabi_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setLanguage(PUNJABI);
                    // start tutorial in Punjabi
                    // after language selection
                    Intent intent = new Intent(getApplicationContext(), TutorialSlide.class);
                    startActivity(intent);
                    finish();
                    //startMainActivity();
                }
            });
        } else startMainActivity();
    }

    // Returns true if there is no app data in the phone.
    // Otherwise false.
    boolean appOpenedFirstTime() {
        preferences = getSharedPreferences(PREF_FILE, MODE_PRIVATE);
        String flag = preferences.getString("FirstTime", "y");
        if (flag == "y") {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("FirstTime", "f");
            editor.commit();
            return true;
        } else return false;
    }

    void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    //set language preference.
    void setLanguage(String lang) {
        preferences = getSharedPreferences(PREF_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_LANG, lang);
        editor.commit();
    }
}
