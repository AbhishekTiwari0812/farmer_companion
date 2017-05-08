package com.example.abhishek.farmer_companion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by Abhishek on 08-05-2017.
 */

public class QueryResponsePage extends AppCompatActivity implements View.OnClickListener {
    String[] insectNames;
    int[] insectRes;
    String responseFromServer;
    HashMap<String, String> insectNameToFile;
    boolean isPunjabi;
    ImageView[] imageview_insect;
    TextView[] textview_insect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.query_response_page);
        imageview_insect = new ImageView[3];

        imageview_insect[0] = (ImageView) findViewById(R.id.iv_reponse1);
        imageview_insect[1] = (ImageView) findViewById(R.id.iv_reponse2);
        imageview_insect[2] = (ImageView) findViewById(R.id.iv_reponse3);

        textview_insect[0] = (TextView) findViewById(R.id.tv_response1);
        textview_insect[1] = (TextView) findViewById(R.id.tv_response2);
        textview_insect[2] = (TextView) findViewById(R.id.tv_response3);

        isPunjabi = false;
        SharedPreferences preferences = getSharedPreferences(OneTimeActivity.PREF_FILE, MODE_PRIVATE);
        String flag = preferences.getString(OneTimeActivity.PREF_LANG, OneTimeActivity.ENGLISH);
        if (flag.compareTo(OneTimeActivity.ENGLISH) != 0) {
            isPunjabi = true;
        }

        responseFromServer = getIntent().getStringExtra("response");
        setInsectNameToFile();
        getInsectRes();
        for (int i = 0; i < 3; ++i) {
            imageview_insect[i].setImageResource(insectRes[i]);
            textview_insect[i].setText(insectNames[i]);
        }


    }

    void getInsectRes() {
        if (responseFromServer != null) {
            insectNames = responseFromServer.split(",");        // first 3 elements must be the names of the insects.
            insectRes = new int[3];
            String baseUrl = "drawable/";
            for (int i = 0; i < 3; ++i) {
                String srcUrl = "";
                srcUrl = baseUrl + insectNameToFile.get(insectNames[i]);
                if (isPunjabi) {
                    srcUrl += "_pun";
                }
                insectRes[i] = getResources().getIdentifier(srcUrl, "drawable", getPackageName());
                if (insectRes[i] == 0) {
                    _("Could not find resource for " + srcUrl);
                }
            }


        } else {
            Toast.makeText(getApplicationContext(), "Null response.Try again", Toast.LENGTH_LONG).show();
        }
    }

    // Set the file names of the insects in a hash map.
    // Insect name must be same as what is being returned by the server.
    void setInsectNameToFile() {
        insectNameToFile = new HashMap<>();
        insectNameToFile.put("BrownWheatMiteAdult", "wheat_5_1,wheat_5_2,wheat_5_3,wheat_5_4,wheat_5_5,wheat_5_6");
        insectNameToFile.put("RhopalosiphumPadiAdult", "wheat_5_7,wheat_5_8,wheat_5_9,wheat_5_10,wheat_5_11,wheat_5_12,wheat_5_13,wheat_5_14,wheat_5_15,wheat_5_16");
        insectNameToFile.put("SchizaphisGraminumAdult", "wheat_5_7,wheat_5_8,wheat_5_9,wheat_5_10,wheat_5_11,wheat_5_12,wheat_5_13,wheat_5_14,wheat_5_15,wheat_5_16");
        insectNameToFile.put("SitobianAvenaeAdult", "wheat_5_7,wheat_5_8,wheat_5_9,wheat_5_10,wheat_5_11,wheat_5_12,wheat_5_13,wheat_5_14,wheat_5_15,wheat_5_16");
        insectNameToFile.put("ArmyCutwormLarva", "wheat_5_17,wheat_5_18,wheat_5_19,wheat_5_20,wheat_5_21,wheat_5_22,wheat_5_23,wheat_5_24");
        insectNameToFile.put("GranaryWeevilAdult", "wheat_5_25,wheat_5_26,wheat_5_27,wheat_5_28");
    }

    void _(String str) {
        Log.d("Errrrrooorrr", "" + str);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_response_image1:
                Intent intent1 = new Intent(getApplicationContext(), SlideViewer.class);
                intent1.putExtra("QueryResponse", true);
                intent1.putExtra("InfoGraphicList", insectNameToFile.get(insectNames[0]));
                startActivity(intent1);
                break;
            case R.id.ll_response_image2:
                Intent intent2 = new Intent(getApplicationContext(), SlideViewer.class);
                intent2.putExtra("QueryResponse", true);
                intent2.putExtra("InfoGraphicList", insectNameToFile.get(insectNames[1]));
                startActivity(intent2);
                break;
            case R.id.ll_response_image3:
                Intent intent3 = new Intent(getApplicationContext(), SlideViewer.class);
                intent3.putExtra("QueryResponse", true);
                intent3.putExtra("InfoGraphicList", insectNameToFile.get(insectNames[2]));
                startActivity(intent3);
                break;
            default:

        }
    }
}
