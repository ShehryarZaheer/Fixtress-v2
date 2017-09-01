package com.example.ehsanullah.loginandregistration.Games;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ehsanullah.loginandregistration.R;

import java.util.ArrayList;


/**
 * Created by SZ on 4/22/2017.
 */

public class GamesListAdapter extends RecyclerView.Adapter<GameViewHolder> {
    ArrayList<Game> games;
    Context context;

    public GamesListAdapter(Context context, ArrayList<Game> games) {
        this.context = context;
        this.games = games;
    }

    @Override
    public GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_list_layout, parent, false);
        return new GameViewHolder(itemView, context, games);

    }

    @Override
    public void onBindViewHolder(GameViewHolder holder, int position) {
        Glide.with(holder.gameThumbnail.getContext())
                .load(games.get(position).getImgurl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.gameThumbnail);

        holder.gametitle.setText(games.get(position).getName());

    }


    @Override
    public int getItemCount() {
        if (games!= null)
            return games.size();
        else
            return 0;
    }

}
