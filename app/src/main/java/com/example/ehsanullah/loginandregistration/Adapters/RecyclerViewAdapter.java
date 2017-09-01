package com.example.ehsanullah.loginandregistration.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ehsanullah.loginandregistration.Images.Image;
import com.example.ehsanullah.loginandregistration.R;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SZ on 7/29/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    int position;
    SearchResult singleVideo;
    List<SearchResult> searchResults;

    ArrayList<Image> images;

    Context context;

    public RecyclerViewAdapter(Context context, List<SearchResult> searchResults, ArrayList<Image> images) {
        this.searchResults = searchResults;
        this.images = images;
        this.context = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_layout, parent, false);
        return new RecyclerViewHolder(itemView, context, searchResults.get(position));
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        this.position = position;
        if (searchResults.size() > 0) {
            if (searchResults.size() > position) {
                if (searchResults.get(position) != null) {
                    singleVideo = searchResults.get(position);
                    ResourceId rId = singleVideo.getId();
                    holder.videoText.setText(singleVideo.getSnippet().getTitle());
                    JSONObject object = new JSONObject(singleVideo.getId());
                    String videoid = null;
                    try {
                        videoid = object.getString("videoId");
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                    //String urlhighres= "https://img.youtube.com/vi/"+videoid+"/hqdefault.jpg";
                    String url = "https://img.youtube.com/vi/" + videoid + "/maxresdefault.jpg";
//Log.e("thumbnail url",singleVideo.getSnippet().getThumbnails().getHigh().getUrl()+"");

                    Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.videoThumbnail);
                } else {
                    holder.cardView.setVisibility(View.GONE);
                }
            }
        } else {
            holder.cardView.setVisibility(View.GONE);
        }
        if (images.size() > 0) {
            if (images.size() > position) {
                if (images.get(position) != null) {
                    Image image = images.get(position);
                    Glide.with(context).load(image.getMedium())
                            .thumbnail(0.5f)
                            .crossFade()
                         .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(holder.imageView);

                    holder.imageText.setText(image.getName());
                } else {
                    holder.imagelayout.setVisibility(View.GONE);
                }
            }
        } else {
            holder.imagelayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (searchResults != null)
            return searchResults.size();
        else
            return 0;
    }
}
