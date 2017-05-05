package com.example.abhishek.farmer_companion;

/**
 * Created by Abhishek on 23-10-2016.
 */

public class ListItemObject {
    int imageResourceLocation;
    int audioResourceLocation;
    String textInfo;

    //boolean isToggled;  // if true, text should show up. If not, image should
    boolean isVideo;
    String vidUrl;

    public int getImageResource() {
        return imageResourceLocation;
    }

    public int getAudioResourceLocation() {
        return audioResourceLocation;
    }

    public String getText() {
        return textInfo;
    }
}
