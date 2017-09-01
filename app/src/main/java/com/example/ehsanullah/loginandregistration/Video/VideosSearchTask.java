package com.example.ehsanullah.loginandregistration.Video;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.model.Volume;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * Created by cloubao on 1/20/16.
 */
public class VideosSearchTask extends AsyncTask<ArrayList<String>, Void, List<Volume>> {

    private SearchListener searchListener;
    private YouTube youtube;
    private ArrayList<Video> videos;
    private List<SearchResult> searchResultList;
    private HttpTransport HTTP_TRANSPORT;
    private JacksonFactory JSON_FACTORY;

    public void setSearchListener(SearchListener searchListener) {
        this.searchListener = searchListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        searchListener.onVideosSearching();
    }

    @Override
    protected List<Volume> doInBackground(ArrayList<String>... params) {


        /** Global instance of the HTTP transport. */
        HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();
/** Global instance of the JSON factory. */
        JSON_FACTORY = JacksonFactory.getDefaultInstance();

        Properties properties = new Properties();


        try {
            // This object is used to make YouTube Data API requests. The last
            // argument is required, but since we don't need anything
            // initialized when the HttpRequest is initialized, we override
            // the interface and provide a no-op function.
            youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName("youtube-cmdline-search-sample").build();

            //Initialize videos arraylist
            videos = new ArrayList<>();

            // Prompt the user to enter a query term.
            String queryTerm = "satisfying video";

            // Define the API request for retrieving search results.
            YouTube.Search.List search = youtube.search().list("id,snippet");

            // Set your developer key from the {{ Google Cloud Console }} for
            // non-authenticated requests. See:
            // {{ https://cloud.google.com/console }}
            String apiKey = "AIzaSyD4eMfmU6lgcR2AGF72mEUC6nBbZBMvOgY";
            search.setKey(apiKey);

            // Restrict the search results to only include videos. See:
            // https://developers.google.com/youtube/v3/docs/search/list#type
            search.setType("video");

            // To increase efficiency, only retrieve the fields that the
            // application uses.
            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            long noOfVideos = 1;
            search.setMaxResults(noOfVideos);


            for (int i = 0; i < params[0].size(); i++) {
                search.setQ(params[0].get(i));
                // Call the API and print results.
                SearchListResponse searchResponse = search.execute();
                searchResultList = searchResponse.getItems();
                if (searchResultList != null) {

                    Video video = new Video();
                    SearchResult singleVideo;

                    singleVideo = searchResultList.get(0);
                    //Log.e("youtube video etag",singleVideo.getEtag());
                    ResourceId rId = singleVideo.getId();
                    video.setRid(rId);
                    video.setVideoTitle(singleVideo.getSnippet().getTitle());
                    JSONObject object = new JSONObject(singleVideo.getId());
                    String videoid = null;
                    try {
                        videoid = object.getString("videoId");
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                    video.setVideoId(videoid);

                    video.setVideoThumbnailUrl(videoid);
                    videos.add(video);


                }

            }

        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Volume> volumes) {
        super.onPostExecute(volumes);
        searchListener.onVideosResult(videos == null ? Collections.<Video>emptyList() : videos);
    }

    public interface SearchListener {
        void onVideosSearching();

        void onVideosResult(List<Video> volumes);
    }

}
