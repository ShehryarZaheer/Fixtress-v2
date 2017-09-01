package com.example.ehsanullah.loginandregistration.Games;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cloubao on 1/20/16.
 */
public class GamesSearchTask extends AsyncTask<ArrayList<String>, Void, List<Game>> {

    private SearchListener searchListener;

    ArrayList<Game> games;

    public void setSearchListener(SearchListener searchListener) {
        this.searchListener = searchListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        searchListener.onSearching();
        games = new ArrayList<>();
    }

    @Override
    protected List<Game> doInBackground(ArrayList<String>... params) {
        HttpURLConnection connection = null;

        URL url = null;


        try {

            for (int i = 0; i < params[0].size(); i++) {
                String q = URLEncoder.encode(params[0].get(i), "UTF-8");

                String a = "https://data.42matters.com/api/v2.0/android/apps/search.json?q=" + q + "&limit=1&access_token=e3900c2ba112dd7ae5717d35064d67f20d32a33c";
                url = new URL(a);

                connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");


                connection.connect();
                //Log.e("response", responseCode + "");

                InputStream is = connection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = rd.readLine()) != null) {
                    response.append(line);
                }

                Log.e("ERR", response.toString());
                JSONObject object = new JSONObject(response.toString());
                JSONArray array = object.getJSONArray("results");

                if(array.length()>0) {
                    JSONObject object1 = array.getJSONObject(0);

                    Game game = new Game();
                    game.setName(object1.getString("title"));
                    game.setDescription(object1.getString("description"));
                    game.setImgurl(object1.getString("icon_72"));
                    game.setGoogleplayurl(object1.getString("market_url"));
                    games.add(game);
                }

            }
            Log.i("game image", games.get(0).getImgurl());
            return games;

        } catch (MalformedURLException e) {
//                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
            Log.e("ER1", e.toString());

        } catch (ProtocolException e) {
            // Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
            Log.e("ER2", e.toString());

        } catch (JSONException e) {
            //    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
            Log.e("ER3", e.toString());


        } catch (IOException e) {
            e.printStackTrace();
            Log.e("ER4", e.toString());
        }
        return new ArrayList<>();
    }

    @Override
    protected void onPostExecute(List<Game> games) {
        super.onPostExecute(games);
        searchListener.onResult(games);
    }

    public interface SearchListener {
        void onSearching();

        void onResult(List<Game> games);
    }

}
