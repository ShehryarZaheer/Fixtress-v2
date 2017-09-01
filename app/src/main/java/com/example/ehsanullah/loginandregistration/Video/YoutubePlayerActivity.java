package com.example.ehsanullah.loginandregistration.Video;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import com.example.ehsanullah.loginandregistration.OtherActivities.Config;
import com.example.ehsanullah.loginandregistration.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;


public class YoutubePlayerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, YouTubePlayer.OnFullscreenListener {

    String videoID;
    YouTubePlayerView playerView;
    private View decorView;
    View activity;
    YouTubePlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);
        activity = findViewById(R.id.activity_youtube_player);
        playerView = (YouTubePlayerView) findViewById(R.id.youtube_player_view);
        videoID = getIntent().getStringExtra("videoID");
        playerView.initialize(Config.DEVELOPER_KEY, this);
        decorView = this.getWindow().getDecorView();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        player = youTubePlayer;
         youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
        if (!b) {
            youTubePlayer.loadVideo(videoID);
        }
        player.setOnFullscreenListener(this);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    private void hideSystemUI() {
        System.out.println("Hide UI");
        activity.setFitsSystemWindows(false);
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

    }

    private void showSystemUI() {
        activity.setFitsSystemWindows(true);
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        activity.setFitsSystemWindows(true);
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int time = player.getCurrentTimeMillis();



        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            hideSystemUI();
            //player.seekToMillis(time);


        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            showSystemUI();
            //player.seekToMillis(time);

        }

    }

    @Override
    public void onFullscreen(boolean b) {
        if(b)
            //player.seekToMillis(player.getCurrentTimeMillis());
           hideSystemUI();
    }
}
