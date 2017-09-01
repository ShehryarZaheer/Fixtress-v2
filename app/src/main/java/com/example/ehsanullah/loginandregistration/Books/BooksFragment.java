package com.example.ehsanullah.loginandregistration.Books;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.ehsanullah.loginandregistration.R;
import com.example.ehsanullah.loginandregistration.Video.VideosFragment;
import com.example.ehsanullah.loginandregistration.activities.TabsActivity;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.google.api.services.books.model.Volume;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by SZ on 8/5/2017.
 */

public class BooksFragment extends android.support.v4.app.Fragment implements BooksSearchTask.SearchListener {


    RecyclerView books_recycler_view;
    ProgressBar progressBar;
    private List<Volume> volumeList;

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
        books_recycler_view = (RecyclerView) view.findViewById(R.id.videos_fragment_recycler_view);
        progressBar = (ProgressBar) view.findViewById(R.id.videos_progress_bar);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);

        BooksSearchFragment booksSearchFragment = (BooksSearchFragment) getActivity().getSupportFragmentManager().findFragmentByTag("booksSearchFragment");
        if (booksSearchFragment != null) {
            volumeList = booksSearchFragment.getVolumeList();
        } else {
            volumeList = new ArrayList<>();
            booksSearchFragment = new BooksSearchFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(booksSearchFragment, "booksSearchFragment")
                    .commit();
           // getActivity().getSupportFragmentManager().executePendingTransactions();


        }


        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

        BookListAdapter adapter = new BookListAdapter(getContext(),volumeList, 2);

        books_recycler_view.setHasFixedSize(true);
        books_recycler_view.addItemDecoration(new MaterialViewPagerHeaderDecorator());

        books_recycler_view.setLayoutManager(layoutManager);
        books_recycler_view.setAdapter(adapter);


       // booksSearchFragment = (BooksSearchFragment) getFragmentManager().findFragmentByTag("booksSearchFragment");
        if (booksSearchFragment != null) {
            booksSearchFragment.setListener(BooksFragment.this);

            booksSearchFragment.searchBooks("Peaceful mind");
        }


    }

    @Override
    public void onSearching() {

        volumeList.clear();
        books_recycler_view.getAdapter().notifyDataSetChanged();
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void onResult(List<Volume> volumes) {
        progressBar.setVisibility(View.GONE);
        volumeList.addAll(volumes);
        books_recycler_view.getAdapter().notifyDataSetChanged();

    }

}