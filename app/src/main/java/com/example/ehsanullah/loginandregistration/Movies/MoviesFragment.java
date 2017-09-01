package com.example.ehsanullah.loginandregistration.Movies;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.ehsanullah.loginandregistration.R;
import com.example.ehsanullah.loginandregistration.activities.TabsActivity;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by SZ on 8/5/2017.
 */

public class MoviesFragment extends android.support.v4.app.Fragment {


    RecyclerView movies_recycler_view;
    ProgressBar progressBar;

    private ArrayList<MovieResult> movieResults;

    public static MoviesFragment newInstance(TabsActivity context) {

        Bundle args = new Bundle();
        args.putSerializable("context", context);

        MoviesFragment fragment = new MoviesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies_layout, container, false);
        movies_recycler_view = (RecyclerView) view.findViewById(R.id.movies_fragment_recycler_view);
        progressBar = (ProgressBar) view.findViewById(R.id.movies_progress_bar);

        progressBar.setVisibility(View.VISIBLE);


        // Check if the NetworkConnection is active and connected.
        ConnectivityManager connMgr = (ConnectivityManager)
                getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new TMDBQueryManager().execute();
        }
        return view;
    }



    private class TMDBQueryManager extends AsyncTask {

        private final String TMDB_API_KEY = "ef68bfed72780ce7ae801b9daba23069";
        private static final String DEBUG_TAG = "TMDBQueryManager";

        @Override
        protected ArrayList<MovieResult> doInBackground(Object... params) {
            try {
                movieResults =  searchIMDB("nothing");
            } catch (IOException e) {
                return null;
            }
            return movieResults;
        }

        @Override
        protected void onPostExecute(Object result) {
            progressBar.setVisibility(View.GONE);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

            movies_recycler_view.setLayoutManager(layoutManager);
            movies_recycler_view.setHasFixedSize(true);
            movies_recycler_view.addItemDecoration(new MaterialViewPagerHeaderDecorator());

            movies_recycler_view.setNestedScrollingEnabled(false);


            if(movieResults==null)
                movieResults = new ArrayList<>();
            MoviesListAdapter adapter = new MoviesListAdapter(getContext(), movieResults);
            movies_recycler_view.setAdapter(adapter);

        }

        ;

        /**
         * Searches IMDBs API for the given query
         *
         * @param query The query to search.
         * @return A list of all hits.
         */
        public ArrayList<MovieResult> searchIMDB(String query) throws IOException {
            // Build URL
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("http://api.themoviedb.org/3/discover/movie");
            stringBuilder.append("?api_key=" + TMDB_API_KEY);
            stringBuilder.append("&with_genres=80"/* + query*/);
            URL url = new URL(stringBuilder.toString());

            InputStream stream = null;
            try {
                // Establish a connection
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.addRequestProperty("Accept", "application/json"); // Required to get TMDB to play nicely.
                conn.setDoInput(true);
                conn.connect();

                int responseCode = conn.getResponseCode();
                Log.d(DEBUG_TAG, "The response code is: " + responseCode + " " + conn.getResponseMessage());

                stream = conn.getInputStream();
                return parseResult(stringify(stream));
            } finally {
                if (stream != null) {
                    stream.close();
                }
            }
        }

        private ArrayList<MovieResult> parseResult(String result) {
            String streamAsString = result;
            ArrayList<MovieResult> results = new ArrayList<MovieResult>();
            try {
                JSONObject jsonObject = new JSONObject(streamAsString);
                JSONArray array = (JSONArray) jsonObject.get("results");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonMovieObject = array.getJSONObject(i);
                    MovieResult.Builder movieBuilder = MovieResult.newBuilder(
                            Integer.parseInt(jsonMovieObject.getString("id")),
                            jsonMovieObject.getString("title"))
                            .setBackdropPath(jsonMovieObject.getString("backdrop_path"))
                            .setOriginalTitle(jsonMovieObject.getString("original_title"))
                            .setPopularity(jsonMovieObject.getString("popularity"))
                            .setPosterPath(jsonMovieObject.getString("poster_path"))
                            .setReleaseDate(jsonMovieObject.getString("release_date"));
                    results.add(movieBuilder.build());
                }
            } catch (JSONException e) {
                System.err.println(e);
                Log.d(DEBUG_TAG, "Error parsing JSON. String was: " + streamAsString);
            }
            return results;
        }

        public String stringify(InputStream stream) throws IOException, UnsupportedEncodingException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(reader);
            return bufferedReader.readLine();
        }
    }


}


