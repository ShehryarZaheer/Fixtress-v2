package com.example.ehsanullah.loginandregistration.Games;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.ehsanullah.loginandregistration.activities.TabsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cloubao on 1/20/16.
 */
public class GamesSearchFragment extends Fragment implements GamesSearchTask.SearchListener
{

    private boolean searching;

    private GamesSearchTask searchTask;
    private GamesSearchTask.SearchListener searchListener;

    private String latestQuery;
    private List<Game> volumeList = new ArrayList<>();
    private Context context;
    private TabsActivity tabsActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void setListener(GamesSearchTask.SearchListener searchListener){

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

    public void searchGames(ArrayList<String> queries) {

        if (searchTask != null) {
            searchTask.cancel(true);
        }

        searchTask = new GamesSearchTask();
        searchTask.setSearchListener(this);
        //searchListener = this;
        searchTask.execute(queries);
    }

    @Override
    public void onSearching() {
        searching = true;
        volumeList.clear();
        searchListener.onSearching();
    }

    @Override
    public void onResult(List<Game> volumes) {
        searching = false;
        volumeList = volumes;
        searchListener.onResult(volumeList);
    }

    public String getLatestQuery() {
        return latestQuery;
    }

    public List<Game> getVolumeList() {
        return volumeList;
    }

    public boolean isSearching() {
        return searching;
    }
}
