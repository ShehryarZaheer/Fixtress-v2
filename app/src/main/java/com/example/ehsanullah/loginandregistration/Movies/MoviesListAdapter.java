package com.example.ehsanullah.loginandregistration.Movies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ehsanullah.loginandregistration.R;

import java.util.ArrayList;


/**
 * Created by Lincoln on 31/03/16.
 */

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.MyViewHolder> {

    private ArrayList<MovieResult> movieResults;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;
        TextView movie_title;

        FrameLayout frameLayout;
        public MyViewHolder(View view) {
            super(view);
            thumbnail = (ImageView) view.findViewById(R.id.movie_image_view);
            movie_title = (TextView) view.findViewById(R.id.movietitle);

            frameLayout = (FrameLayout) view.findViewById(R.id.viewHolder_main_id);
            frameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }


    public MoviesListAdapter(Context context, ArrayList<MovieResult> movieResults) {
        mContext = context;
        this.movieResults= movieResults;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MovieResult movieResult = movieResults.get(position);

        Glide.with(mContext).load(movieResult.getBackdropPath())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.thumbnail);
        holder.movie_title.setText(movieResult.getTitle());
    }

    @Override
    public int getItemCount() {
        return movieResults.size();
    }


}