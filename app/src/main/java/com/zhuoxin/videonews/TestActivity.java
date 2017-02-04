package com.zhuoxin.videonews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zhuoxin.videonews.videoplayernews.part.SimpleVideoPlayer;


public class TestActivity extends AppCompatActivity {

    SimpleVideoPlayer simpleVideoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        simpleVideoPlayer = (SimpleVideoPlayer) findViewById(R.id.test_svp);
        simpleVideoPlayer.setVideoPath(VideoUrlRes.getTestUrl1());
    }

    @Override
    protected void onResume() {
        super.onResume();
        simpleVideoPlayer.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        simpleVideoPlayer.onPause();
    }
}
