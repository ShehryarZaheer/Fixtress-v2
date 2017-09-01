package com.example.ehsanullah.loginandregistration.Images;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.ehsanullah.loginandregistration.R;
import com.example.ehsanullah.loginandregistration.Video.VideosFragment;
import com.example.ehsanullah.loginandregistration.activities.SlideshowDialogFragment;
import com.example.ehsanullah.loginandregistration.activities.TabsActivity;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by SZ on 8/5/2017.
 */

public class ImagesFragment extends android.support.v4.app.Fragment implements ImageSearchTask.SearchListener {


    RecyclerView videos_recycler_view;
    ProgressBar progressBar;

    private List<Image> images;

    public static VideosFragment newInstance(TabsActivity context) {

        Bundle args = new Bundle();
        args.putSerializable("context", context);

        VideosFragment fragment = new VideosFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.videos_fragment_layout, container, false);
        videos_recycler_view = (RecyclerView) view.findViewById(R.id.videos_fragment_recycler_view);
        progressBar = (ProgressBar) view.findViewById(R.id.videos_progress_bar);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageSearchFragment imageSearchFragment= (ImageSearchFragment) getActivity().getSupportFragmentManager().findFragmentByTag("imageSearchFragment");
        if (imageSearchFragment!= null) {
            images= imageSearchFragment.getVolumeList();
        } else {
            images= new ArrayList<>();
            imageSearchFragment= new ImageSearchFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(imageSearchFragment, "imageSearchFragment")
                    .commit();
            //     getActivity().getSupportFragmentManager().executePendingTransactions();


        }


        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

        videos_recycler_view.setLayoutManager(layoutManager);
        videos_recycler_view.setHasFixedSize(true);
        videos_recycler_view.addItemDecoration(new MaterialViewPagerHeaderDecorator());

        videos_recycler_view.setNestedScrollingEnabled(false);


        //     pDialog.hide();
        GalleryAdapter adapter = new GalleryAdapter(getContext(), (ArrayList<Image>) images);
        videos_recycler_view.setAdapter(adapter);

        videos_recycler_view.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(getContext(), videos_recycler_view, new GalleryAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", (Serializable) images);
                bundle.putInt("position", position);

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        ArrayList<String> searchqueries = new ArrayList<>();
        searchqueries.add("Beautiful Flowers");
        searchqueries.add("Walpapers");
        searchqueries.add("Stress Relief");
        searchqueries.add("Cricket");
        searchqueries.add("Shahrukh Khan");

        if (imageSearchFragment!= null) {
            imageSearchFragment.setSearchListener(this);
            imageSearchFragment.searchImages(searchqueries);
        }


    }

    @Override
    public void onSearching() {
        progressBar.setVisibility(View.VISIBLE);
        images.clear();
    }

    @Override
    public void onResult(List<Image> volumes) {
        progressBar.setVisibility(View.GONE);

        images.addAll(volumes);
        Log.e("images size",images.size()+"");
        videos_recycler_view.getAdapter().notifyDataSetChanged();
    }

}