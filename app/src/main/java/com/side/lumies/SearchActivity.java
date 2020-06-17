package com.side.lumies;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        try {
            MediaPlayer mp = new MediaPlayer();
            mp.setDataSource("http://cs.saturn.tj:8000/premium128.mp3");
            mp.prepare();
            mp.start();
        }
        catch (Exception e) {
        }
    }
}
