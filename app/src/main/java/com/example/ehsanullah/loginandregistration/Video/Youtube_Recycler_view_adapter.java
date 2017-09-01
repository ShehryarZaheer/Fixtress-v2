package com.example.ehsanullah.loginandregistration.Video;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ehsanullah.loginandregistration.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.api.services.youtube.model.SearchResult;


import java.util.ArrayList;


/**
 * Created by SZ on 4/22/2017.
 */

public class Youtube_Recycler_view_adapter extends RecyclerView.Adapter<AdapterViewHolder> implements YouTubePlayer.OnInitializedListener {
    static int position;
    SearchResult singleVideo;
    ArrayList<Video> videos;
    Context context;

    public Youtube_Recycler_view_adapter(Context context, ArrayList<Video> videos) {
        this.context = context;
        this.videos = videos;
    }

    @Override
    public AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_adapter_layout, parent, false);
        return new AdapterViewHolder(itemView, context, videos);

    }

    @Override
    public void onBindViewHolder(AdapterViewHolder holder, int position) {
        this.position = position;
        holder.videotitle.setText(videos.get(position).getVideoTitle());

        Glide.with(context).load(videos.get(position).getVideoThumbnailUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.videoThumbnail);

    }

    @Override
    public int getItemCount() {
        if (videos != null)
            return videos.size();
        else
            return 0;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        // loadVideo() will auto play video
        // Use cueVideo() method, if you don't want to play it automatically
        youTubePlayer.loadVideo(singleVideo.getId().getVideoId());

        // Hiding player controls
        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
    /*    if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(
                    getString(R.string.error_player), errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
*/
    }
}
