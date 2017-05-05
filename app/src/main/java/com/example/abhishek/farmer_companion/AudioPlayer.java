/*

package com.example.abhishek.farmer_companion;

import android.content.Context;
import android.media.MediaPlayer;

public class AudioPlayer {
    boolean isPlaying;
    int currentRes;
    MediaPlayer player;
    Context context;

    AudioPlayer(Context context) {
        this.context = context;
    }

    int playAudio(int res) {
        int previousProgress = 0;
        if (player != null)
            if (player.isPlaying()) {
                previousProgress = player.getCurrentPosition();
                player.stop();
            }
        player = MediaPlayer.create(context, res);
        player.start();
        return previousProgress;
    }

    int getDuration() {
        if (player != null)
            return player.getDuration();
        else return 0;
    }

    void resumeCurrent() {
        if (player != null)
            player.start();
    }

    int getCurrentProgress() {
        if (player != null)
            return player.getCurrentPosition();
        else return 0;
    }

    void stopCurrent() {
        if (player != null)
            player.stop();
    }

    void pauseCurrent() {
        if (player.isPlaying())
            player.pause();
    }

    void freeRes() {

    }

    public void timeChange(int progress) {
        if (player != null) {
            player.seekTo(progress);
        }
    }
}
*/
