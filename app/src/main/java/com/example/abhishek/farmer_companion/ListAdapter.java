package com.example.abhishek.farmer_companion;/*package com.example.abhishek.btp1;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Abhishek on 16-11-2016.


public class ListAdapter extends BaseAdapter {
    ArrayList<ListItemObject> list;
    Context context;
    int randomToastGenerator = 4;
    Button audioPlayerButton, nextItem, prevItem, textToggler;
    TextView rankHolder, infoText, textViewCurrentAudioTime, textViewTotalTime;
    ImageView infoImage;
    SeekBar seekBar;
    ListView listView;
    int lastPlayingAudioIndex;
    AudioPlayer audioPlayer;

    ListAdapter(Context c, ArrayList<ListItemObject> e, ListView listView) {
        context = c;
        list = new ArrayList<>();
        this.listView = listView;
        lastPlayingAudioIndex = -1;
        for (int i = 0; i < e.size(); ++i) {
            list.add(e.get(i));
        }
        audioPlayer = new AudioPlayer(c);
        // load the images and audio accordingly.
        // fill the list.
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View newRow = layoutInflater.inflate(R.layout.list_item, parent, false);
        newRow.setClickable(false);
        rankHolder = (TextView) newRow.findViewById(R.id.textview_rank_holder);
        infoImage = (ImageView) newRow.findViewById(R.id.imageview_info);
        infoText = (TextView) newRow.findViewById(R.id.textview_info);
        textToggler = (Button) newRow.findViewById(R.id.button_text_toggle);
        textViewCurrentAudioTime = (TextView) newRow.findViewById(R.id.textview_current_time);
        seekBar = (SeekBar) newRow.findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                    handleSeekChange(progress, position);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        textViewTotalTime = (TextView) newRow.findViewById(R.id.textview_total_time);
        prevItem = (Button) newRow.findViewById(R.id.button_prev_item);
        prevItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position != 0)
                    listView.smoothScrollToPosition(position - 1);
                else
                    Toast.makeText(context, "Reached topmost level.", Toast.LENGTH_SHORT).show();
            }
        });
        audioPlayerButton = (Button) newRow.findViewById(R.id.button_audio_player);
        nextItem = (Button) newRow.findViewById(R.id.button_next_item);
        nextItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position != list.size() - 1)
                    listView.smoothScrollToPosition(position + 1);
                else {
                    Toast.makeText(context, "Reached bottommost level", Toast.LENGTH_SHORT).show();
                }
            }
        });
        fillItems(position);
        textToggler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int randomNum = (int) (Math.random() * 1000000.0);
                if (randomNum % randomToastGenerator == 0)
                    Toast.makeText(context, "You can also tap on image to change it to text.", Toast.LENGTH_LONG).show();
                updateView(position);
            }
        });
        infoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                randomToastGenerator = 10009;
                updateView(position);
                listView.smoothScrollToPosition(position);
            }
        });
        infoText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                randomToastGenerator = 10009;
                updateView(position);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        audioPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPauseSong(position);
            }
        });
        return newRow;
    }

    private void handleSeekChange(int progress, int pos) {
        pos = pos - listView.getFirstVisiblePosition();
        if (pos != lastPlayingAudioIndex) {
            _("Current:" + pos + " playing:" + lastPlayingAudioIndex);
            Toast.makeText(context, "This audio is not playing. Seek change will not do anything", Toast.LENGTH_SHORT).show();
            return;
        }
        View v = listView.getChildAt(pos - listView.getFirstVisiblePosition());
        if (v == null) {
            _("no view found");
            return;
        } else {
            audioPlayer.timeChange(progress);
        }
    }

    private void updateView(int i) {
        View v = listView.getChildAt(i - listView.getFirstVisiblePosition());
        if (v == null) {
            _("no view found");
            return;
        } else {
            _("changing item at position:" + i);
            ImageView imageView = (ImageView) v.findViewById(R.id.imageview_info);
            ScrollView scrollView = (ScrollView) v.findViewById(R.id.text_info_scroller);
            Button textToggler = (Button) v.findViewById(R.id.button_text_toggle);
            String tempHelper = (String) textToggler.getText();
            char c = tempHelper.charAt(tempHelper.length() - 1);
            Boolean removeImage;
            if (c == 't' || c == 'T') {
                removeImage = true;
            } else removeImage = false;
            if (removeImage) {
                textToggler.setText("Switch to image");
                imageView.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
            } else {
                textToggler.setText("Switch to text");
                imageView.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.GONE);
            }
        }
    }

    private void playPauseSong(int i) {
        View v = listView.getChildAt(i - listView.getFirstVisiblePosition());
        if (v == null) {
            _("no view found");
            return;
        } else {
            Button playerButton = (Button) v.findViewById(R.id.button_audio_player);
            String temp = (String) playerButton.getText();
            char last = temp.charAt(temp.length() - 1);
            if (last == 'Y' || last == 'y') {
                if (lastPlayingAudioIndex == i) {
                    audioPlayer.resumeCurrent();
                    playerButton.setText("Pause");
                } else {
                    if (lastPlayingAudioIndex != -1) {
                        View previousListView = listView.getChildAt(lastPlayingAudioIndex - listView.getFirstVisiblePosition());
                        if (previousListView != null) {
                            Button previousPlayer = (Button) previousListView.findViewById(R.id.button_audio_player);
                            previousPlayer.setText("Play");
                        }
                    }
                    ListItemObject obj = (ListItemObject) getItem(i);
                    int res = obj.audioResourceLocation;
                    playerButton.setText("Pause");
                    audioPlayer.playAudio(res);
                    lastPlayingAudioIndex = i;
                }
            } else {
                audioPlayer.pauseCurrent();
                playerButton.setText("Play");
                lastPlayingAudioIndex = i;
            }
        }
    }

    void fillItems(int i) {
        ListItemObject obj = list.get(i);
        rankHolder.setText("# " + (i + 1));
        infoImage.setImageResource(obj.getImageResource());
        infoText.setText(obj.getText());
    }

    void _(String str) {
        System.out.println("" + str);
    }


    public AudioPlayer getCurrentPlayer() {
        return audioPlayer;
    }
}
*/