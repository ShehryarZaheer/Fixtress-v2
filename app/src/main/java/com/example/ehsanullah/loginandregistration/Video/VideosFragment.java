package com.example.ehsanullah.loginandregistration.Video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.ehsanullah.loginandregistration.R;
import com.example.ehsanullah.loginandregistration.activities.TabsActivity;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by SZ on 8/5/2017.
 */

public class VideosFragment extends android.support.v4.app.Fragment implements VideosSearchTask.SearchListener {


    RecyclerView videos_recycler_view;
    ProgressBar progressBar;

    private HttpTransport HTTP_TRANSPORT;
    private JacksonFactory JSON_FACTORY;
    private List<Video> videosList;

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

        /** Global instance of the HTTP transport. */
        HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();
/** Global instance of the JSON factory. */
        JSON_FACTORY = JacksonFactory.getDefaultInstance();

        ArrayList<String> searchqueries = new ArrayList<>();
        searchqueries.add("See you again");
        searchqueries.add("Satisfying Video");
        searchqueries.add("Spider Man Home Coming Trailer");
        searchqueries.add("The Dark Knight Rises");
        searchqueries.add("Ted Talk");
//        new LoadVideos().execute(searchqueries);


        VideosSearchFragment videosSearchFragment = (VideosSearchFragment) getActivity().getSupportFragmentManager().findFragmentByTag("videosSearchFragment");
        if (videosSearchFragment != null) {
            videosList = videosSearchFragment.getVolumeList();
        } else {
            videosList = new ArrayList<>();
            videosSearchFragment = new VideosSearchFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(videosSearchFragment, "videosSearchFragment")
                    .commit();
       //     getActivity().getSupportFragmentManager().executePendingTransactions();


        }


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        Youtube_Recycler_view_adapter adapter = new Youtube_Recycler_view_adapter(getContext(),(ArrayList<Video>) videosList);

        videos_recycler_view.setHasFixedSize(true);
        videos_recycler_view.setLayoutManager(layoutManager);
        videos_recycler_view.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        videos_recycler_view.setAdapter(adapter);


        if (videosSearchFragment != null) {
            videosSearchFragment.setSearchListener(this);
            videosSearchFragment.searchVideos(searchqueries);
        }
    }

    @Override
    public void onVideosSearching() {
        videosList.clear();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onVideosResult(List<Video> videos) {
        progressBar.setVisibility(View.GONE);

        videos.addAll(videos);
        Youtube_Recycler_view_adapter adapter = new Youtube_Recycler_view_adapter(getContext(), (ArrayList<Video>) videos);
        videos_recycler_view.setAdapter(adapter);

    }
}