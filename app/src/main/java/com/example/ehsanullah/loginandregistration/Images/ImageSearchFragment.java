package com.example.ehsanullah.loginandregistration.Images;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cloubao on 1/20/16.
 */
public class ImageSearchFragment extends Fragment implements ImageSearchTask.SearchListener{

    private boolean searching;

    private ImageSearchTask searchTask;
    private ImageSearchTask.SearchListener searchListener;

    private String latestQuery;
    private List<Image> volumeList = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //searchListener = (BooksSearchTask.SearchListener) context;
    }
    public void setSearchListener(ImageSearchTask.SearchListener listener){
        searchListener = listener;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void searchImages(ArrayList<String> queries) {
        if (searchTask != null) {
            searchTask.cancel(true);
        }

        searchTask = new ImageSearchTask();
        searchTask.setSearchListener(this);
        searchTask.execute(queries);
    }

    @Override
    public void onSearching() {
        searching = true;

        searchListener.onSearching();
    }

    @Override
    public void onResult(List<Image> volumes) {
        searching = false;
        volumeList= volumes;
        searchListener.onResult(volumeList);

    }


    public String getLatestQuery() {
        return latestQuery;
    }

    public List<Image> getVolumeList() {
        return volumeList;
    }

    public boolean isSearching() {
        return searching;
    }
}
