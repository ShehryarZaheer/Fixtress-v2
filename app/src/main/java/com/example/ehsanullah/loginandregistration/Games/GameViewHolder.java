package com.example.ehsanullah.loginandregistration.Games;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ehsanullah.loginandregistration.R;

import java.util.ArrayList;


/**
 * Created by SZ on 4/22/2017.
 */

public class GameViewHolder extends RecyclerView.ViewHolder {
    ImageView gameThumbnail;
    TextView gametitle;
    Button binstall;
    FrameLayout frameLayout;
    Context context;
    ArrayList<Game> games;

    public GameViewHolder(final View itemView, final Context context, final ArrayList<Game> games) {
        super(itemView);
        gameThumbnail = (ImageView) itemView.findViewById(R.id.game_thumbnail);
        gametitle = (TextView) itemView.findViewById(R.id.game_title);
        binstall = (Button) itemView.findViewById(R.id.binstall);
        this.games = games;
        this.context = context;
        binstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // region Adding games search data in sharedprefs for finding related results next time
                String data = context.getSharedPreferences("games", 0).getString("gamesList", "");
                SharedPreferences preferences = context.getSharedPreferences("games", 0);
                SharedPreferences.Editor editor = preferences.edit();
                String newData = data.concat(":" + games.get(getLayoutPosition()).getName());
                editor.putString("gamesList", newData);
                editor.commit();

                // endregion

                //region Open app in Play store
                Intent intent = new Intent(Intent.ACTION_VIEW);

                //Copy App URL from Google Play Store.
                intent.setData(Uri.parse(games.get(getLayoutPosition()).getGoogleplayurl()));

                context.startActivity(intent);
                //endregion
            }
        });
    }

}
