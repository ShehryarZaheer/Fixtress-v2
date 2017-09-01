package com.example.ehsanullah.loginandregistration.Images;

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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cloubao on 1/20/16.
 */
public class ImageSearchTask extends AsyncTask<ArrayList<String>, Void, List<Image>> {

    private SearchListener searchListener;

    ArrayList<Image> images;
    public void setSearchListener(SearchListener searchListener) {
        this.searchListener = searchListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        searchListener.onSearching();
        images = new ArrayList<>();
    }

    @Override
    protected List<Image> doInBackground(ArrayList<String>... params) {
        HttpURLConnection connection = null;
        try {


            // Your API key
            String key="AIzaSyBC4sofbNlvqQP9ijVoOkFHAwReGAm1cms";

            // Your Search Engine ID
            String cx = "006678944915768895517:frt32vtyzuy";


            for(int i = 0;i<params[0].size();i++) {

                // looking for
                String strNoSpaces = params[0].get(i).replace(" ", "+");


                String url2 = "https://www.googleapis.com/customsearch/v1?q=" + strNoSpaces + "&key=" + key + "&cx=" + cx + "&alt=json";


                URL url = new URL(url2);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int status = connection.getResponseCode();

                InputStream is = connection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null) {
                    response.append(line + "\n");
                }

                JSONObject object = new JSONObject(response.toString());
                JSONArray array = object.getJSONArray("items");


                for (int j = 0; j < array.length() / 3; j++) {
                    JSONObject arrayObj = array.getJSONObject(j);
                    JSONObject pagemap = arrayObj.getJSONObject("pagemap");
                    JSONArray cse_image_array;
                    try {
                        cse_image_array = pagemap.getJSONArray("cse_image");
                        if (cse_image_array.length() > 0) {
                            JSONObject srcobject = cse_image_array.getJSONObject(0);

                            String imgurl = srcobject.getString("src");

                            //imgurl = URLEncoder.encode(imgurl, "UTF-8");
                            Image image = new Image();
                            image.setName(arrayObj.getString("title"));


                            image.setSmall(imgurl);
                            image.setMedium(imgurl);
                            image.setLarge(imgurl);
                            image.setTimestamp(" ");

                            images.add(image);
                        }

                    } catch (JSONException exception) {
                        continue;
                    }
                }

            }
            return images;

        } catch (MalformedURLException e) {
            e.printStackTrace();

            Log.e("exception",e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("exception",e.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return images;
    }

    @Override
    protected void onPostExecute(List<Image> images) {
        super.onPostExecute(images);
        searchListener.onResult(images);
    }

    public interface SearchListener {
        void onSearching();

        void onResult(List<Image> volumes);
    }

}
