package com.example.abhishek.farmer_companion;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by Abhishek on 05-03-2017.
 * This is the main page for querying the information from the backend
 * TODO: Send image and tags to the backend
 * TODO: Get the response and output in pretty format
 */

public class QueryPage extends AppCompatActivity {
    private ImageView imageHolder;
    private final int requestCodeCapture = 20;
    private final int requestCodeUpload = 21;
    Button captureImage;
    Button sendDataButton;
    Button uploadFromPhone;
    LinearLayout queryButtonLayout;
    TextView tv_or_string;
    String queryImageStringForm;
    String responseFromServer;
    // TODO: change this for correct URL
    private static final String UPLOAD_URL = "http://10.20.0.100:80/btp/RequestHandler.php";
    ProgressDialog progress;
    Context context;
    int backPressCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.query_page);
        this.setTitle("  Ask Question");
        backPressCount = 0;
        context = this;
        queryButtonLayout = (LinearLayout) findViewById(R.id.image_query_layout);
        imageHolder = (ImageView) findViewById(R.id.captured_photo);
        captureImage = (Button) findViewById(R.id.photo_button);
        uploadFromPhone = (Button) findViewById(R.id.upload_button);
        tv_or_string = (TextView) findViewById(R.id.textview_or_string);

        // start using the camera
        captureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(photoCaptureIntent, requestCodeCapture);
            }
        });

        uploadFromPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), requestCodeUpload);
            }
        });

        sendDataButton = (Button) findViewById(R.id.send_data_button);
        sendDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: get text and send information
                uploadFile();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (backPressCount == 0) {
            super.onBackPressed();
        } else {
            backPressCount -= 1;
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageHolder.getLayoutParams();
            params.weight = 0.0f;
            imageHolder.setImageBitmap(null);
            captureImage.setVisibility(View.VISIBLE);
            uploadFromPhone.setVisibility(View.VISIBLE);
            tv_or_string.setVisibility(View.VISIBLE);
            sendDataButton.setVisibility(View.GONE);
            changeVisibility(true);
        }
    }

    /*
    * This method is used for capturing the image
    * Takes the image through camera.
    * Calls the getStringImage which converts the image
     * to string and stores in the global variable queryImageStringForm.
    * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        backPressCount += 1;
        super.onActivityResult(requestCode, resultCode, data);
        if (this.requestCodeCapture == requestCode && resultCode == RESULT_OK) {
            // get the image captured.
            Bitmap queryImage = (Bitmap) data.getExtras().get("data");
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageHolder.getLayoutParams();
            params.weight = 7.0f;
            imageHolder.setImageBitmap(queryImage);
            changeVisibility(false);
            queryImageStringForm = getStringImage(queryImage);
            // TODO: send the image for query.
            // TODO: on back pressed, try again.

        } else if (requestCode == requestCodeUpload && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                backPressCount += 1;
                Bitmap queryImage = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageHolder.getLayoutParams();
                params.weight = 7.0f;
                imageHolder.setImageBitmap(queryImage);
                changeVisibility(false);
                queryImageStringForm = getStringImage(queryImage);
            } catch (IOException e) {
                System.out.println("Error. Code not working!");
                e.printStackTrace();
            }
        } else {
            // TODO: Action wasn't successful. Try again.

        }
    }


    void changeVisibility(boolean firstPage) {
        if (firstPage) {
            queryButtonLayout.setVisibility(View.VISIBLE);
            //captureImage.setVisibility(View.VISIBLE);
            //uploadFromPhone.setVisibility(View.VISIBLE);
            tv_or_string.setVisibility(View.VISIBLE);
            sendDataButton.setVisibility(View.GONE);

        } else {
            queryButtonLayout.setVisibility(View.GONE);
            //captureImage.setVisibility(View.GONE);
            //uploadFromPhone.setVisibility(View.GONE);
            tv_or_string.setVisibility(View.GONE);
            sendDataButton.setVisibility(View.VISIBLE);
        }
    }

    /*
     * Converts bitmap to byte-stream
    **/
    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    public int uploadFile() {
        // TODO: Update this to send user registration data too.
        // KEY, VALUE PAIR for sending the data to the server.
        Map<String, String> params = new HashMap<>();
        params.put("question_tag", "something_123");        // this is the name of the user ( not needed ? )
        params.put("user_id", "1");                         // id of the user
        params.put("action_type", "answer_query");          // action-type is answer-query for now.
        params.put("query_image", queryImageStringForm);    // This is the image to be uploaded.
        new PostClass(params).execute();
        return 1;
    }

    private class PostClass extends AsyncTask<String, Void, Void> {

        Map<String, String> params;

        public PostClass(Map<String, String> params) {
            this.params = params;
        }

        protected void onPreExecute() {
            responseFromServer = "";
            progress = new ProgressDialog(context);
            progress.setMessage("Loading");
            progress.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                URL url = new URL(UPLOAD_URL);
                //opening a new connection
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(120000);
                // Get the query to be posted using HTTP_POST
                String urlParameters = getQuery(this.params);

                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                //sending data to server
                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
                dStream.writeBytes(urlParameters);
                dStream.flush();
                dStream.close();
                /******DATA SENT*******/
                int responseCode = connection.getResponseCode();
                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters);
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters);
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");

                // reading the response from the server
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                }
                // Reading response from server is complete.
                br.close();
                responseFromServer = responseOutput.toString();
                System.out.println(responseOutput.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();
            Toast.makeText(getApplicationContext(), "Response:" + responseFromServer, Toast.LENGTH_LONG).show();
        }

        private String getQuery(Map<String, String> params) throws UnsupportedEncodingException {

            StringBuilder result = new StringBuilder();
            boolean first = true;
            Iterator it = params.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                if (first)
                    first = false;
                else
                    result.append("&");
                result.append(URLEncoder.encode((String) pair.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode((String) pair.getValue(), "UTF-8"));
            }
            return result.toString();
        }

    }
}
