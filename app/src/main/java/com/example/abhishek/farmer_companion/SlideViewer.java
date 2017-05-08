package com.example.abhishek.farmer_companion;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SlideViewer extends AppCompatActivity {

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    ArrayList<ListItemObject> infographic_list;
    private Button btnPrev, btnNext;
    //private PrefManager prefManager;
    boolean isPunjabi;
    private String sectionInformer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        setContentView(R.layout.crop_static_layout);
        isPunjabi = false;
        SharedPreferences preferences = getSharedPreferences(OneTimeActivity.PREF_FILE, MODE_PRIVATE);
        String flag = preferences.getString(OneTimeActivity.PREF_LANG, OneTimeActivity.ENGLISH);
        if (flag.compareTo(OneTimeActivity.ENGLISH) != 0) {
            isPunjabi = true;
        }
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnPrev = (Button) findViewById(R.id.btn_prev);
        btnNext = (Button) findViewById(R.id.btn_next);

        if (isPunjabi) {
            btnNext.setText("ਅਗਲਾ");
            btnPrev.setText("ਪਿਛਲੇ");
        }

        infographic_list = fillList();      // Gets the content to be filled in the views.
        int infographic_size = infographic_list.size();
        if (infographic_size > 1) {
            if (!isPunjabi)
                btnNext.setText("Next");
            else
                btnNext.setText("ਅਗਲਾ");
        }
        layouts = new int[infographic_size];        // Layout for views. infographic list sends information
        // to this layout-array before constructing the view.
        for (int i = 0; i < infographic_size; ++i)
            layouts[i] = R.layout.infographic_layout;
        // adding bottom dots
        addBottomDots(0);
        // making notification bar transparent
        changeStatusBarColor();
        //TODO: send resources to populate the list items here!
        myViewPagerAdapter = new MyViewPagerAdapter(infographic_list);
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        // TODO: Change it make it to go
        // TODO: to previous page.

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // launchHomeScreen();
                int current = getItem(-1);
                if (current >= 0) {
                    viewPager.setCurrentItem(current);
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    onBackPressed();
                }
            }
        });
    }

    // Creates a list of ListItemObject
    // returns content to populate the slide-viewer
    private ArrayList<ListItemObject> fillList() {
        //Get the text for info-graphics from TextInfoClass
        Intent intent = getIntent();
        sectionInformer = intent.getStringExtra("CROP_SECTION");
        if (sectionInformer == null) {
            return fillResponseInfoGraph(intent);
        }
        TextInfoClass infoText = new TextInfoClass();

        String resLocation = sectionInformer;       // directory location where the resource is located for current page.
        // for current crop and section,
        // Fetching images and audio
        ArrayList<Integer> images = getResList(resLocation, true);  // 2nd parameter = true fetches the images.
        int imageListSize = images.size();
        // populating the list
        int listSize = images.size();
        ArrayList<ListItemObject> list = new ArrayList<>();
        for (int i = 0; i < listSize; ++i) {
            ListItemObject a = new ListItemObject();
            if (i < imageListSize)
                a.imageResourceLocation = images.get(i);
            else {
                a.imageResourceLocation = R.drawable.default_image;     // If an image is missing and audio is present, a place holder image
                // is set.
            }
            if (isPunjabi) {
                a.textInfo = infoText.getText(sectionInformer + i + "_pun");     // sets the text information about the info-graphic.
            } else {
                a.textInfo = infoText.getText(sectionInformer + i);     // sets the text information about the info-graphic.
            }
            list.add(a);
        }
        HashMap<Integer, String> videoUrls = getVideoUrl();
        for (int i = 0; i < list.size(); ++i) {
            ListItemObject currObj = list.get(i);
            Integer imgUrl = currObj.getImageResource();
            if (videoUrls.containsKey(imgUrl)) {
                currObj.isVideo = true;
                currObj.vidUrl = videoUrls.get(imgUrl);
            }
        }
        return list;
    }

    private ArrayList<ListItemObject> fillResponseInfoGraph(Intent intent) {
        try {
            String[] responseSlides = intent.getStringExtra("InfoGraphicList").split(",");
            TextInfoClass infoText = new TextInfoClass();
            SharedPreferences preferences = getSharedPreferences(OneTimeActivity.PREF_FILE, MODE_PRIVATE);
            String lang = preferences.getString(OneTimeActivity.PREF_LANG, OneTimeActivity.ENGLISH);
            boolean isPunjabi = (lang.compareTo(OneTimeActivity.PUNJABI) == 0);
            int[] images = new int[responseSlides.length];
            String baseUrl = "drawable/";
            for (int i = 0; i < responseSlides.length; ++i) {
                String srcUrl = "";
                srcUrl = baseUrl + responseSlides[i];
                if (isPunjabi) {
                    srcUrl += "_pun";
                }
                images[i] = getResources().getIdentifier(srcUrl, "drawable", getPackageName());
                if (images[i] == 0) {
                    _("Could not find resource for " + srcUrl);
                }
            }
            int imageListSize = images.length;
            ArrayList<ListItemObject> list = new ArrayList<>();
            for (int i = 0; i < imageListSize; ++i) {
                ListItemObject a = new ListItemObject();
                if (i < imageListSize)
                    a.imageResourceLocation = images[i];
                else {
                    a.imageResourceLocation = R.drawable.default_image;     // If an image is missing and audio is present, a place holder image
                }
                if (isPunjabi) {
                    a.textInfo = infoText.getText(responseSlides[i] + "_pun");     // sets the text information about the info-graphic.
                } else {
                    a.textInfo = infoText.getText(responseSlides[i]);     // sets the text information about the info-graphic.
                }
                list.add(a);
            }
            HashMap<Integer, String> videoUrls = getVideoUrl();
            for (int i = 0; i < list.size(); ++i) {
                ListItemObject currObj = list.get(i);
                Integer imgUrl = currObj.getImageResource();
                if (videoUrls.containsKey(imgUrl)) {
                    currObj.isVideo = true;
                    currObj.vidUrl = videoUrls.get(imgUrl);
                }
            }
            return list;
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "No resources found", Toast.LENGTH_LONG).show();
            return new ArrayList<>();
        }
    }

    // helper function for fill-list.
    // fetches the images and audio files from the res directory.
    // The images in the drawable folder should be named properly
    // to position the infographics in correct places.

    ArrayList<Integer> getResList(String resIdInit, boolean isImage) {
        ArrayList<Integer> list = new ArrayList<>();
        String url = "";
        String dest = "";

        if (isImage) {
            url = "drawable/" + resIdInit;
            dest = "drawable";
            _("Listing images from:" + resIdInit);
        } else {
            url = "raw/" + resIdInit;
            dest = "raw";
            _("Listing audio from:" + resIdInit);
        }
        int i = 0;
        while (true) {
            try {
                if (i > 40)
                    return list;
                _("getting:" + url + i);
                int imageResource = 0;
                if (isPunjabi) {
                    imageResource = getResources().getIdentifier(url + i + "_pun", dest, getPackageName());
                } else {
                    imageResource = getResources().getIdentifier(url + i, dest, getPackageName());
                }
                if (imageResource == 0){
                    i++;
                    continue;
                }

                list.add(imageResource);
            } catch (Exception e) {
                _("no more items found");
                return list;
            }
            i++;

            _("Current i = " + i);
        }
    }


    // Used only for debugging.
    private void _(Object s) {
        System.out.println("" + s);
    }

    // TODO: Add the video resource names and the link in the hashmap
    // TODO: Change the map here if new video is to be added
    private HashMap<Integer, String> getVideoUrl() {
        HashMap<String, String> videoUrlsString = new HashMap<>();
        videoUrlsString.put("wheat_1_1", "https://youtu.be/rw6fV7dstmA");
        videoUrlsString.put("wheat_1_2", "https://www.youtube.com/watch?v=rw6fV7dstmA");
        videoUrlsString.put("wheat_2_5", "https://youtu.be/NnrWz7Jujro");
        videoUrlsString.put("wheat_2_10", "https://youtu.be/c3B29gAmspI");
        videoUrlsString.put("wheat_2_13", "https://youtu.be/B6MvJ_j00FM");
        videoUrlsString.put("wheat_3_8", "https://youtu.be/zl1ENNrnS1M");
        videoUrlsString.put("wheat_3_24", "https://youtu.be/lzTH1x_Ph8w");
        videoUrlsString.put("wheat_4_2", "https://youtu.be/j-f0KkNNnMs");
        videoUrlsString.put("wheat_4_3", "https://youtu.be/lsO9GUBuPJo");
        videoUrlsString.put("wheat_4_4", "https://youtu.be/hZGbTzMy8ZA");
        videoUrlsString.put("wheat_5_6", "https://youtu.be/TenRNA_usxA");
        videoUrlsString.put("wheat_5_10", "https://youtu.be/HVTitHBwpN0");
        videoUrlsString.put("wheat_5_13", "https://youtu.be/oTnTWre488s");
        videoUrlsString.put("wheat_5_14", "https://youtu.be/lQsQbXlVL0Q");
        videoUrlsString.put("wheat_5_18", "https://youtu.be/TjrdnEJQlwA");
        videoUrlsString.put("wheat_5_22", "https://youtu.be/8uzewaZJRtU");
        videoUrlsString.put("wheat_5_27", "https://youtu.be/eM5CFcNnW1k");
        videoUrlsString.put("wheat_1_1_pun", "https://youtu.be/rw6fV7dstmA");
        videoUrlsString.put("wheat_1_2_pun", "https://www.youtube.com/watch?v=rw6fV7dstmA");
        videoUrlsString.put("wheat_2_5_pun", "https://youtu.be/NnrWz7Jujro");
        videoUrlsString.put("wheat_2_10_pun", "https://youtu.be/c3B29gAmspI");
        videoUrlsString.put("wheat_2_13_pun", "https://youtu.be/B6MvJ_j00FM");
        videoUrlsString.put("wheat_3_8_pun", "https://youtu.be/zl1ENNrnS1M");
        videoUrlsString.put("wheat_3_24_pun", "https://youtu.be/lzTH1x_Ph8w");
        videoUrlsString.put("wheat_4_2_pun", "https://youtu.be/j-f0KkNNnMs");
        videoUrlsString.put("wheat_4_3_pun", "https://youtu.be/lsO9GUBuPJo");
        videoUrlsString.put("wheat_4_4_pun", "https://youtu.be/hZGbTzMy8ZA");
        videoUrlsString.put("wheat_5_6_pun", "https://youtu.be/TenRNA_usxA");
        videoUrlsString.put("wheat_5_10_pun", "https://youtu.be/HVTitHBwpN0");
        videoUrlsString.put("wheat_5_13_pun", "https://youtu.be/oTnTWre488s");
        videoUrlsString.put("wheat_5_14_pun", "https://youtu.be/lQsQbXlVL0Q");
        videoUrlsString.put("wheat_5_18_pun", "https://youtu.be/TjrdnEJQlwA");
        videoUrlsString.put("wheat_5_22_pun", "https://youtu.be/8uzewaZJRtU");
        videoUrlsString.put("wheat_5_27_pun", "https://youtu.be/eM5CFcNnW1k");


        HashMap<Integer, String> videoUrls = new HashMap<>();
        List<String> urls = new ArrayList<>(videoUrlsString.keySet());
        for (int i = 0; i < videoUrlsString.size(); ++i) {
            int imageresource = getResources().getIdentifier("drawable/" + urls.get(i), "drawable", getPackageName());
            videoUrls.put(imageresource, videoUrlsString.get(urls.get(i)));
            _("set image resource: " + imageresource);
        }
        return videoUrls;
    }

    // Adds dots in the bottom of the slide view pages.
    // Helps visualizing navigation
    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];
        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            if (dots.length > 10)
                dots[i].setTextSize(20);
            else dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage % 4]); // color can be changed inside the color.xml
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage % 4]); // color can be changed inside the color.xml
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }


    //	viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // changing the next button text 'NEXT' / 'Finish'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                if (!isPunjabi)
                    btnNext.setText("Finish");
                else {
                    btnNext.setText("ਮੁਕੰਮਲ");
                }
                btnPrev.setVisibility(View.GONE);
            } else {
                // still pages are left
                if (isPunjabi)
                    btnNext.setText("ਅਗਲਾ");
                else
                    btnNext.setText("NEXT");
                btnPrev.setVisibility(View.VISIBLE);
            }
            if (position >= 1) {
                btnPrev.setVisibility(View.VISIBLE);
            } else {
                btnPrev.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;
        private int[] image_res;
        private String[] text_res;
        private HashMap<Integer, String> videoUrls;
        private int[] audio_res;

        public MyViewPagerAdapter(ArrayList<ListItemObject> a) {
            // setting the resources in the slide view.
            int l_size = a.size();
            this.image_res = new int[l_size];
            this.videoUrls = new HashMap<>();
            for (int i = 0; i < l_size; ++i) {
                this.image_res[i] = a.get(i).getImageResource();
                if (a.get(i).isVideo) {
                    this.videoUrls.put(a.get(i).getImageResource(), a.get(i).vidUrl);
                }
            }
            this.text_res = new String[l_size];
            for (int i = 0; i < l_size; ++i) {
                this.text_res[i] = a.get(i).getText();
            }

        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(layouts[position], container, false);
            ImageView infoImage = (ImageView) view.findViewById(R.id.info_image);
            TextView infoText = (TextView) view.findViewById(R.id.info_text);
            // TODO: change the page content here.
            infoImage.setImageResource(image_res[position]);
            infoText.setText(text_res[position]);
            if (this.videoUrls.containsKey(Integer.valueOf(image_res[position]))) {
                infoImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String id = videoUrls.get(image_res[position]);
                        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(id));
                        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(id));
                        try {
                            startActivity(appIntent);
                        } catch (ActivityNotFoundException ex) {
                            startActivity(webIntent);
                        }
                    }
                });
            }
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
