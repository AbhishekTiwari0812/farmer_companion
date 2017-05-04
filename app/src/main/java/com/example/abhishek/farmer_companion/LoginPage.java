package com.example.abhishek.farmer_companion;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.EditText;

/**
 * Created by Abhishek on 05-03-2017.
 */

public class LoginPage extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("LoginFile", 0);
        String phoneNumber = sharedPreferences.getString("PhoneNumber", "-1");
        if (phoneNumber.length() == 10) {
            // nothing to do.
            onBackPressed();
        } else {
            setContentView(R.layout.login_page);
            EditText et_userName;
            EditText et_phoneNumber;
            TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            phoneNumber = telephonyManager.getLine1Number();

        }
    }
}
