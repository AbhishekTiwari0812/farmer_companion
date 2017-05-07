package com.example.abhishek.farmer_companion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Abhishek on 07-05-2017.
 */

public class OneTimeActivity extends AppCompatActivity {
    SharedPreferences preferences;
    static final String PREF_FILE = "FARMER_PREFERENCE";
    static final String PREF_LANG = "pref_lang";
    static final String ENGLISH = "ENG";
    static final String PUNJABI = "PUN";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (appOpenedFirstTime()) {
            setContentView(R.layout.language_option);
            Button english_button = (Button) findViewById(R.id.language_english);
            Button punjabi_button = (Button) findViewById(R.id.language_punjabi);
            english_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setLanguage(ENGLISH);
                    startMainActivity();
                }
            });
            punjabi_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setLanguage(PUNJABI);
                    startMainActivity();
                }
            });
        } else startMainActivity();
    }

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

    void setLanguage(String lang) {
        preferences = getSharedPreferences(PREF_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_LANG, lang);
        editor.commit();
    }
}
