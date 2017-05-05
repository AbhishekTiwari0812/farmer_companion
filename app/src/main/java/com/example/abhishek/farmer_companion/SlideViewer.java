package com.example.abhishek.farmer_companion;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
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
    private String sectionInformer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: remove this if needed.
        // Making notification bar transparent
        /*if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }*/

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        // Getting the information about the current crop name.
        // And the section,
        // to decide which info-graphics to show.
        // Example: wheat_0_  OR paddy_1_
        Intent intent = getIntent();
        sectionInformer = intent.getStringExtra("CROP_SECTION");
        setContentView(R.layout.crop_static_layout);
        // Setting up the slide view.
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnPrev = (Button) findViewById(R.id.btn_prev);
        btnNext = (Button) findViewById(R.id.btn_next);
        // Filling the slide view with infographic information.
        infographic_list = fillList();      // Gets the content to be filled in the views.
        int infographic_size = infographic_list.size();
        if (infographic_size > 1) {
            btnNext.setText("Next");
        }
        layouts = new int[infographic_size];        // Layout for views. infographic list sends information
        // to this layout-array before constructing the view.
        for (int i = 0; i < infographic_size; ++i)
            layouts[i] = R.layout.infographic_layout;

        // adding bottom dots
        addBottomDots(0);
        //TODO: is it needed?
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
        TextInfoClass infoText = new TextInfoClass();
        String resLocation = sectionInformer;       // directory location where the resource is located for current page.
        // for current crop and section,
        // Fetching images and audio
        ArrayList<Integer> images = getResList(resLocation, true);  // 2nd parameter = true fetches the images.
        int imageListSize = images.size();
        ArrayList<Integer> songs = getResList(resLocation, false);      // false: fetches the audio
        int songListSize = songs.size();
        // populating the list
        int listSize = images.size() > songs.size() ? images.size() : songs.size();
        ArrayList<ListItemObject> list = new ArrayList<>();
        for (int i = 0; i < listSize; ++i) {
            ListItemObject a = new ListItemObject();
            //TODO: add condition to handle errors.
            if (i < imageListSize)
                a.imageResourceLocation = images.get(i);
            else {
                a.imageResourceLocation = R.drawable.default_image;     // If an image is missing and audio is present, a place holder image
                // is set.
            }
            if (i < songListSize)
                a.audioResourceLocation = songs.get(i);
            else {
                a.audioResourceLocation = R.raw.default_audio;      // If the audio file is missing, placeholder audio file is set.
            }
            a.textInfo = infoText.getText(sectionInformer + i);     // sets the text information about the info-graphic.
            list.add(a);
        }
        HashMap<Integer, String> videoUrls = getVideoUrl();
        for (int i = 0; i < list.size(); ++i) {
            ListItemObject currObj = list.get(i);
            Integer imgUrl = currObj.getImageResource();
            if (videoUrls.containsKey(imgUrl)) {
                // TODO: Might cause error
                // Check whether
                currObj.isVideo = true;
                currObj.vidUrl = videoUrls.get(imgUrl);
            }
        }
        return list;
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
                _("getting:" + url + i);
                int imageResource = getResources().getIdentifier(url + i, dest, getPackageName());
                if (imageResource == 0)
                    return list;
                list.add(imageResource);
            } catch (Exception e) {
                _("no more items found");
                return list;
            }
            i++;
            if (i > 5)
                return list;
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
        videoUrlsString.put("wheat_1_2", "https://www.youtube.com/watch?v=rw6fV7dstmA");
        HashMap<Integer, String> videoUrls = new HashMap<>();
        List<String> urls = new ArrayList<>(videoUrlsString.keySet());
        for (int i = 0; i < videoUrlsString.size(); ++i) {
            int imageresource = getResources().getIdentifier("drawable/" + urls.get(i), "drawable", getPackageName());
            videoUrls.put(imageresource, videoUrlsString.get(urls.get(i)));
            // _("set image resource: " + imageresource);
            // _("IMG URL STRING::" + "drawable/" + urls.get(i));
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
            dots[i].setTextSize(35);
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
                btnNext.setText("Finish");
                btnPrev.setVisibility(View.GONE);
            } else {
                // still pages are left
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
            this.audio_res = new int[l_size];
            for (int i = 0; i < l_size; ++i) {
                this.audio_res[i] = a.get(i).getAudioResourceLocation();
            }

            for (int i = 0; i < l_size; ++i) {

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
