package com.example.ehsanullah.loginandregistration.Video;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cloubao on 1/20/16.
 */
public class VideosSearchFragment extends Fragment implements VideosSearchTask.SearchListener {

    private boolean searching;

    private VideosSearchTask searchTask;
    private VideosSearchTask.SearchListener searchListener;

    private ArrayList<String> queries;
    private List<Video> volumeList = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    //    searchListener = (VideosSearchTask.SearchListener) context;
    }

    void setSearchListener(VideosSearchTask.SearchListener listener){
        searchListener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void searchVideos(ArrayList<String> queries) {

        if (searchTask != null) {
            searchTask.cancel(true);
        }
        this.queries = queries;
        searchTask = new VideosSearchTask();
        searchTask.setSearchListener(this);
        searchTask.execute(queries);
    }


    public List<Video> getVolumeList() {
        return volumeList;
    }

    public boolean isSearching() {
        return searching;
    }

    @Override
    public void onVideosSearching() {
        searching = true;
        volumeList.clear();
        searchListener.onVideosSearching();

    }

    @Override
    public void onVideosResult(List<Video> volumes) {
        searching = false;
        volumeList = volumes;
        searchListener.onVideosResult(volumeList);

    }


}
