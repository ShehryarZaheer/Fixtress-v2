package com.example.ehsanullah.loginandregistration.Games;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.ehsanullah.loginandregistration.R;
import com.example.ehsanullah.loginandregistration.Video.VideosFragment;
import com.example.ehsanullah.loginandregistration.activities.TabsActivity;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by SZ on 8/5/2017.
 */

public class GameFragment extends android.support.v4.app.Fragment implements GamesSearchTask.SearchListener {


    RecyclerView games_recycler_view;
    ProgressBar progressBar;

    private List<Game> gamesList;

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
        games_recycler_view = (RecyclerView) view.findViewById(R.id.videos_fragment_recycler_view);
        progressBar = (ProgressBar) view.findViewById(R.id.videos_progress_bar);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);
        ArrayList<String> searchqueries = new ArrayList<>();
        searchqueries.add("Shane Watson");
        searchqueries.add("Marvell");
        searchqueries.add("Spider Man Home Coming Trailer");
        searchqueries.add("The Dark Knight Rises");
        searchqueries.add("Ted Talk");

        GamesSearchFragment gamesSearchFragment= (GamesSearchFragment) getActivity().getSupportFragmentManager().findFragmentByTag("gamesSearchFragment");
        if (gamesSearchFragment!= null) {
            gamesList= gamesSearchFragment.getVolumeList();
        } else {
            gamesList= new ArrayList<>();
            gamesSearchFragment= new GamesSearchFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(gamesSearchFragment, "gamesSearchFragment")
                    .commit();
            // getActivity().getSupportFragmentManager().executePendingTransactions();


        }


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);

        GamesListAdapter listAdapter = new GamesListAdapter(getContext(),(ArrayList<Game>) gamesList);

        games_recycler_view.setHasFixedSize(true);
        games_recycler_view.addItemDecoration(new MaterialViewPagerHeaderDecorator());

        games_recycler_view.setLayoutManager(layoutManager);
        games_recycler_view.setAdapter(listAdapter);


        // booksSearchFragment = (BooksSearchFragment) getFragmentManager().findFragmentByTag("booksSearchFragment");
        if (gamesSearchFragment!= null) {

            gamesSearchFragment.setListener(this);
            gamesSearchFragment.searchGames(searchqueries);
        }


    }

    @Override
    public void onSearching() {

        gamesList.clear();
        games_recycler_view.getAdapter().notifyDataSetChanged();
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void onResult(List<Game> volumes) {
        progressBar.setVisibility(View.GONE);
        gamesList.addAll(volumes);
        games_recycler_view.getAdapter().notifyDataSetChanged();

    }

}