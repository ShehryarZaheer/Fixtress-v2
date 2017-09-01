package com.example.ehsanullah.loginandregistration.Books;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.ehsanullah.loginandregistration.activities.TabsActivity;
import com.google.api.services.books.model.Volume;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cloubao on 1/20/16.
 */
public class BooksSearchFragment extends Fragment implements BooksSearchTask.SearchListener {

    private boolean searching;

    private BooksSearchTask searchTask;
    private BooksSearchTask.SearchListener searchListener;

    private String latestQuery;
    private List<Volume> volumeList = new ArrayList<>();
    private Context context;
    private TabsActivity tabsActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
 }

    void setListener(BooksSearchTask.SearchListener searchListener){

            this.searchListener = searchListener;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    void setContext(TabsActivity tabsActivity) {
        this.tabsActivity = tabsActivity;
    }

    public void searchBooks(String query) {
        if (query.equalsIgnoreCase(latestQuery)) {
            return;
        }
        if (searchTask != null) {
            searchTask.cancel(true);
        }
        latestQuery = query;
        searchTask = new BooksSearchTask();
        searchTask.setSearchListener(this);
        //searchListener = this;
        searchTask.execute(query);
    }

    @Override
    public void onSearching() {
        searching = true;
        volumeList.clear();
        searchListener.onSearching();
    }

    @Override
    public void onResult(List<Volume> volumes) {
        searching = false;
        volumeList = volumes;
        searchListener.onResult(volumeList);
    }

    public String getLatestQuery() {
        return latestQuery;
    }

    public List<Volume> getVolumeList() {
        return volumeList;
    }

    public boolean isSearching() {
        return searching;
    }
}
