package com.example.ehsanullah.loginandregistration.Video;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ehsanullah.loginandregistration.R;
import com.example.ehsanullah.loginandregistration.activities.YoutubePlayerActivity;

import java.util.ArrayList;


/**
 * Created by SZ on 4/22/2017.
 */

public class AdapterViewHolder extends RecyclerView.ViewHolder {
    ImageView videoThumbnail;
    TextView videotitle;

    FrameLayout frameLayout;
    Context context;
    ArrayList<Video> videos;

    public AdapterViewHolder(final View itemView, final Context context, final ArrayList<Video> videos) {
        super(itemView);
        videoThumbnail = (ImageView) itemView.findViewById(R.id.video_thumbnail);
        videotitle = (TextView) itemView.findViewById(R.id.videotitle);
        frameLayout = (FrameLayout) itemView.findViewById(R.id.viewHolder_main_id);
        this.videos = videos;
        this.context = context;
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    FragmentTransaction ft = ((Activity) context).getFragmentManager().beginTransaction();
                frag.show(ft, "txn_tag");
                MyDialogFragment frag = new MyDialogFragment();
            */
                Intent intent = new Intent(context, YoutubePlayerActivity.class);
                intent.putExtra("videoID", videos.get(getLayoutPosition()).getVideoId());

                //region Adding clicked data in sharedPrefs

                String data = context.getSharedPreferences("videos",0).getString("videoList","");
                SharedPreferences preferences = context.getSharedPreferences("videos",0);
                SharedPreferences.Editor editor = preferences.edit();
                String newData = data.concat(":"+videos.get(getLayoutPosition()).getVideoTitle());
                editor.putString("videoList",newData);
                editor.commit();

                //endregion

                context.startActivity(intent);
            }
        });
    }

}
