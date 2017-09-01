package com.example.ehsanullah.loginandregistration.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ehsanullah.loginandregistration.Images.GalleryAdapter;
import com.example.ehsanullah.loginandregistration.R;
import com.example.ehsanullah.loginandregistration.activities.YoutubePlayerActivity;
import com.google.api.services.books.model.Volume;
import com.google.api.services.youtube.model.SearchResult;


/**
 * Created by SZ on 7/29/2017.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private Volume volume;
    Context context;
    SearchResult searchResult;

    CardView cardView;
ImageView videoThumbnail;
    TextView videoText;

    LinearLayout booklayout;
    ImageView bookimage;
    TextView bookTitle;

    LinearLayout imagelayout;
    ImageView imageView;
    TextView imageText;

    public RecyclerViewHolder(View itemView, final Context context, final SearchResult singlevideo) {
        super(itemView);

        this.context = context;
        this.searchResult = singlevideo;

        cardView = (CardView) itemView.findViewById(R.id.cardview);
        videoThumbnail = (ImageView) itemView.findViewById(R.id.video_thumbnail);
        videoText = (TextView) itemView.findViewById(R.id.videotitle);

        booklayout = (LinearLayout) itemView.findViewById(R.id.booklayout);
        bookimage = (ImageView) itemView.findViewById(R.id.bookimage);
        bookTitle = (TextView) itemView.findViewById(R.id.book_title);

        imagelayout = (LinearLayout) itemView.findViewById(R.id.imagelayout);
        imageView = (ImageView) itemView.findViewById(R.id.image);
        imageText = (TextView) itemView.findViewById(R.id.image_title);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    FragmentTransaction ft = ((Activity) context).getFragmentManager().beginTransaction();
                frag.show(ft, "txn_tag");
                MyDialogFragment frag = new MyDialogFragment();
            */
                Intent intent = new Intent(context, YoutubePlayerActivity.class);
                intent.putExtra("videoID", singlevideo.getId().getVideoId());
                context.startActivity(intent);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private GalleryAdapter.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final GalleryAdapter.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public void setVolume(Volume volume) {
        this.volume = volume;


/*
        System.out.println(volume.getVolumeInfo().getInfoLink());

        int approximateWidth = 300;
        int approximateHeight = 400;

        DisplayMetrics displayMetrics = itemView.getContext().getResources().getDisplayMetrics();

        int screenWidth = displayMetrics.widthPixels;

        int width = screenWidth / spanCount;
        int height = (approximateHeight * width) / approximateWidth;

        ViewGroup.LayoutParams params = itemView.getLayoutParams();
        params.width = width;
        params.height = height;
        itemView.setLayoutParams(params);
        itemView.invalidate();
*/
        Volume.VolumeInfo.ImageLinks imageLinks = volume.getVolumeInfo().getImageLinks();

        if (imageLinks != null) {
            String medium = imageLinks.getMedium();
            String large = imageLinks.getLarge();
            String small = imageLinks.getSmall();
            String thumbnail = imageLinks.getThumbnail();
            String smallThumbnail = imageLinks.getSmallThumbnail();

            String imageLink = "";
            if (large != null) {
                imageLink = large;
            } else if (medium != null) {
                imageLink = medium;
            } else if (small != null) {
                imageLink = small;
            } else if (thumbnail != null) {
                imageLink = thumbnail;
            } else if (smallThumbnail != null) {
                imageLink = smallThumbnail;
            }

            imageLink = imageLink.replace("edge=curl", "");
            System.out.println(imageLink);

            Glide.with(itemView.getContext())
                    .load(imageLink)
                    .into((ImageView) itemView);
        } else {
            System.err.println("No images ??");
        }

    }


}
