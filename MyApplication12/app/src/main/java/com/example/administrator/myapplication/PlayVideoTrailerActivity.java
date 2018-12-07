package com.example.administrator.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class PlayVideoTrailerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{

    YouTubePlayerView youTubePlayerView;
    String key ="";
    int Request_video = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video_trailer);

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.myYoutube);


        Intent intent = getIntent();
        key = intent.getStringExtra("keyVideoTrailer");
        //Toast.makeText(this,key,Toast.LENGTH_SHORT).show();

        youTubePlayerView.initialize("AIzaSyDRmLvyWPzzwAoaasADzvTIQGZt427pGyA",this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.loadVideo(key);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if(youTubeInitializationResult.isUserRecoverableError()){
            youTubeInitializationResult.getErrorDialog(PlayVideoTrailerActivity.this, Request_video);

        }else {
            Toast.makeText(this,"Error!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == Request_video){
            youTubePlayerView.initialize("AIzaSyDRmLvyWPzzwAoaasADzvTIQGZt427pGyA", PlayVideoTrailerActivity.this);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
