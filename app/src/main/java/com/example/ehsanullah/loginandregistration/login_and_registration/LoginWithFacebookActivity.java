package com.example.ehsanullah.loginandregistration.login_and_registration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ehsanullah.loginandregistration.Constant;
import com.example.ehsanullah.loginandregistration.R;
import com.example.ehsanullah.loginandregistration.facebook_data.books;
import com.example.ehsanullah.loginandregistration.facebook_data.facebookData;
import com.example.ehsanullah.loginandregistration.facebook_data.games;
import com.example.ehsanullah.loginandregistration.facebook_data.liked;
import com.example.ehsanullah.loginandregistration.facebook_data.allLikedThings;
import com.example.ehsanullah.loginandregistration.facebook_data.movies;
import com.example.ehsanullah.loginandregistration.facebook_data.music;
import com.example.ehsanullah.loginandregistration.facebook_data.musicHaveListenedTo;
import com.example.ehsanullah.loginandregistration.facebook_data.musicPlaylist;
import com.example.ehsanullah.loginandregistration.facebook_data.rated;
import com.example.ehsanullah.loginandregistration.facebook_data.readBooks;
import com.example.ehsanullah.loginandregistration.facebook_data.tvShows;
import com.example.ehsanullah.loginandregistration.facebook_data.wantsToWatch;
import com.example.ehsanullah.loginandregistration.facebook_data.watched;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginWithFacebookActivity extends AppCompatActivity {

    private String currentUsersFirstName, currentUsersLastName, currentUsersPassword, currentUsersDOB, currentUsersGender;
    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private ProgressDialog pd;
    private GraphRequest graphRequest;
    private SharedPreferences sharedPreferences;
    private boolean passwordSettedToLoginWithoutFB = false;
    private boolean signUpWithoutFacebook = false;


    //_________________________ ArrayLists to store data in Firebase _____________________
    ArrayList<liked> namesOfLikedMoviesList = new ArrayList<liked>();
    ArrayList<watched> watchesMoviesTitlesList = new ArrayList<watched>();
    ArrayList<wantsToWatch> WTWMoviesTitlesList = new ArrayList<wantsToWatch>();
    ArrayList<rated> ratedMoviesTitlesList = new ArrayList<rated>();

    ArrayList<liked> namesOfLikedTvShowsList = new ArrayList<liked>();
    ArrayList<watched> titlesOfWatchedTvShowsList = new ArrayList<watched>();
    ArrayList<wantsToWatch> titlesOfWTWTvShowsList = new ArrayList<wantsToWatch>();
    ArrayList<rated> titlesOfRatedTvShowsList = new ArrayList<rated>();

    ArrayList<liked> namesOfLikedBooksList = new ArrayList<liked>();
    ArrayList<readBooks> namesOfReadBooksList = new ArrayList<readBooks>();
    ArrayList<rated> namesOfRatedBooksList = new ArrayList<rated>();

    ArrayList<liked> namesOfLikedMusicList = new ArrayList<liked>();
    ArrayList<musicHaveListenedTo> namesOfListenedToMusicList = new ArrayList<musicHaveListenedTo>();
    ArrayList<musicPlaylist> namesOfPlaylistMusicList = new ArrayList<musicPlaylist>();

    ArrayList<allLikedThings> allAllLikedThingsList = new ArrayList<>();

    ArrayList<games> namesOfLikedGamesList = new ArrayList<games>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //________________________________________ Initialization of FB will come always before setting View ______________
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login_with_facebook);


//        //------------------- to hide the Action bar ---------
//        getSupportActionBar().hide();


        callbackManager = CallbackManager.Factory.create();


        //_______________________________ Initializing the SharedPreferences _________________________________
        sharedPreferences = this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        //_______________________________________________ Views _______________________________________
        info = (TextView) findViewById(R.id.info);
        loginButton = (LoginButton) findViewById(R.id.login_button);


        //_____________________________________ getting permissions to access data of FB ____________________________
        loginButton.setReadPermissions("user_friends");
        loginButton.setReadPermissions("public_profile");
        loginButton.setReadPermissions("email");
        loginButton.setReadPermissions("user_birthday");
        loginButton.setReadPermissions("read_custom_friendlists");
        loginButton.setReadPermissions("user_likes");
        loginButton.setReadPermissions("user_actions.video");
        loginButton.setReadPermissions("user_actions.books");
        loginButton.setReadPermissions("user_actions.music");
        loginButton.setReadPermissions("user_actions.news");
        loginButton.setReadPermissions("user_games_activity");


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {


                //_______________________________ getting the email, from user is currently logged in _____________________
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                                Constant.CURRENT_USERS_EMAIL = object.optString("email");

                                //_______________________________ check if this account has already been created on that email _____________________
                                checkEmailAlreadyExistenceFunc(loginResult);
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "email");
                request.setParameters(parameters);
                request.executeAsync();


                //getFriendsList(loginResult);
//                getFriends(loginResult);
//                getNews(loginResult);

            }

            @Override
            public void onCancel() {

                info.setText("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException e) {
                info.setText("Login attempt failed.");
            }
        });
    }

    private void checkEmailAlreadyExistenceFunc(final LoginResult loginResult) {

        //_______________________________ Query to get the all the data of a specific user w.r.t Email _________________________________
        Query mobileNumExistenceQuery = Constant.FIREBASE_REF.child("users").orderByChild("email").equalTo(Constant.CURRENT_USERS_EMAIL);
        mobileNumExistenceQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = null;

                if (dataSnapshot.exists()) {

                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        user = child.getValue(User.class);
                        Constant.CURRENT_USERS_ID = child.getKey().toString();
                    }


                    //______________ we are getting this value to check if password has already been set to login without FB ______
                    passwordSettedToLoginWithoutFB = user.getPasswordSettedToLoginWithoutFB();
                    signUpWithoutFacebook = user.getSignUpWithoutFacebook();
                    Constant.QUESTIONNAIRE_FILLED = user.getQuestionnaireFilled();


                    if (signUpWithoutFacebook == true) {
                        //_______________________________ fetch the data From Facebook and Store in Firebase  _____________________
                        signUpWithFacebookFunc(loginResult);
                    } else {
                        if (Constant.QUESTIONNAIRE_FILLED == false)
                            startQuestionnaireWhereYouLeftFunc();
                    }


                    Toast.makeText(getApplicationContext(), "Now here will open the activity of TabView", Toast.LENGTH_LONG).show();
                } else {

                    //_______________________________ creating account on email currently logged in  _____________________
                    signUpWithFacebookFunc(loginResult);
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getApplicationContext(), firebaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void signUpWithFacebookFunc(LoginResult loginResult) {

        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

//                                    Profile profile = Profile.getCurrentProfile();
//                                    Constant.CURRENT_ACCOUNTS_FIRST_NAME = profile.getFirstName();
//                                    Constant.CURRENT_ACCOUNTS_Last_NAME = profile.getLastName();


                        //______________________________________ getting all required data from user's profile _________________
                        getPublicInfoFunc(object);
                        info.append("\n------------------------\n");
                        getLikedMoviesFunc(object);
                        info.append("\n------------------------\n");
                        getWatchedMoviesFunc(object);
                        info.append("\n------------------------\n");
                        getWantsToWatchMoviesFunc(object);
                        info.append("\n------------------------\n");
                        getRatedMoviesFunc(object);
                        info.append("\n------------------------\n");
                        getLikedTvShowsFunc(object);
                        info.append("\n------------------------\n");
                        getWatchedTvShowsFunc(object);
                        info.append("\n------------------------\n");
                        getWantsToWatchTvShowsFunc(object);
                        info.append("\n------------------------\n");
                        getRatedTvShowsFunc(object);
                        info.append("\n------------------------\n");
                        getLikedBooksFunc(object);
                        info.append("\n------------------------\n");
                        getReadBooksFunc(object);
                        info.append("\n------------------------\n");
                        getRatedBooksFunc(object);
                        info.append("\n------------------------\n");
                        getLikedMusicFunc(object);
                        info.append("\n------------------------\n");
                        getAllLikesItemsFunc(object);
                        info.append("\n------------------------\n");
                        getLikedGamesFunc(object);


                        //______________________________________ Creating new account after getting info from FB _____________
                        gettingValuesFromFBAndCreatingAccountInDB();
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday,first_name,last_name,likes{category,name,location}," +
                "games{name},books{name},books.reads,books.rates,music{name},movies{name},television{name},video.watches,video.wants_to_watch,video.rates");
        request.setParameters(parameters);
        request.executeAsync();

    }


    private void getRatedBooksFunc(JSONObject object) {

        try {

            // convert Json object into Json array
            JSONObject obj = object.getJSONObject("books.rates");
            JSONArray allLikedBooks = obj.getJSONArray("data");

            for (int i = 0; i < allLikedBooks.length(); i++) {

                JSONObject book = allLikedBooks.getJSONObject(i);


                if (book != null) {
                    namesOfRatedBooksList.add(new rated(book.optString("name")));
                }
            }

            for (int j = 0; j < namesOfRatedBooksList.size(); j++) {
                info.append("\n\nTitle: " + namesOfRatedBooksList.get(j));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getReadBooksFunc(JSONObject object) {

        try {

            // convert Json object into Json array
            JSONObject obj = object.getJSONObject("books.reads");
            JSONArray allLikedBooks = obj.getJSONArray("data");

            for (int i = 0; i < allLikedBooks.length(); i++) {

                JSONObject book = allLikedBooks.getJSONObject(i);


                if (book != null) {
                    namesOfReadBooksList.add(new readBooks(book.optString("name")));
                }
            }

            for (int j = 0; j < namesOfReadBooksList.size(); j++) {
                info.append("\n\nTitle: " + namesOfReadBooksList.get(j));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getRatedTvShowsFunc(JSONObject object) {
        //_________________________________ Getting Rated Tv Shows ___________________________

        try {
            // convert Json object into Json array
            JSONArray allRatedVideos1 = object.getJSONObject("video.rates").getJSONArray("data");

            for (int i = 0; i < allRatedVideos1.length(); i++) {

                JSONObject singleRatedVideo = allRatedVideos1.getJSONObject(i);

                JSONObject tvShow = singleRatedVideo.getJSONObject
                        ("data").getJSONObject("tv_show");


                if (tvShow != null) {
                    titlesOfRatedTvShowsList.add(new rated(tvShow.optString("title")));
                }
            }

            for (int j = 0; j < titlesOfRatedTvShowsList.size(); j++) {
                info.append("\n\nTitle: " + titlesOfRatedTvShowsList.get(j));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getRatedMoviesFunc(JSONObject object) {
        //_________________________________ Getting Rated Movies ___________________________

        try {
            // convert Json object into Json array
            JSONArray allRatedVideos = object.getJSONObject("video.rates").getJSONArray("data");

            for (int i = 0; i < allRatedVideos.length(); i++) {

                JSONObject singleRatedVideo = allRatedVideos.getJSONObject(i);

                JSONObject movie = singleRatedVideo.getJSONObject("data").getJSONObject("movie");


                if (movie != null) {
                    ratedMoviesTitlesList.add(new rated(movie.optString("title")));
                }
            }

            for (int j = 0; j < ratedMoviesTitlesList.size(); j++) {
                info.append("\n\nTitle: " + ratedMoviesTitlesList.get(j));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getWantsToWatchTvShowsFunc(JSONObject object) {
        //_________________________________ Getting Wants to Watch Tv Shows ___________________________


        try {
            // convert Json object into Json array
            JSONArray allWantsToWatchVideos1 = object.getJSONObject("video.wants_to_watch").getJSONArray("data");

            for (int i = 0; i < allWantsToWatchVideos1.length(); i++) {

                JSONObject singleWatchedVideo = allWantsToWatchVideos1.getJSONObject(i);

                JSONObject tvShow = singleWatchedVideo.getJSONObject("data").getJSONObject("tv_show");

                if (tvShow != null) {
                    titlesOfWTWTvShowsList.add(new wantsToWatch(tvShow.optString("title")));
                }
            }

            for (int j = 0; j < titlesOfWTWTvShowsList.size(); j++) {
                info.append("\n\nTitle: " + titlesOfWTWTvShowsList.get(j));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getWantsToWatchMoviesFunc(JSONObject object) {
        //_________________________________ Getting Wants to Watch Movies ___________________________


        try {
            // convert Json object into Json array
            JSONArray allWantsToWatchVideos = object.getJSONObject("video.wants_to_watch").getJSONArray("data");

            for (int i = 0; i < allWantsToWatchVideos.length(); i++) {

                JSONObject singleWatchedVideo = allWantsToWatchVideos.getJSONObject(i);

                JSONObject movie = singleWatchedVideo.getJSONObject("data").getJSONObject("movie");
                ;

                if (movie != null) {
                    WTWMoviesTitlesList.add(new wantsToWatch(movie.optString("title")));
                }
            }

            for (int j = 0; j < WTWMoviesTitlesList.size(); j++) {
                info.append("\n\nTitle: " + WTWMoviesTitlesList.get(j).getName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getWatchedTvShowsFunc(JSONObject object) {
        //_________________________________ Getting Watched Tv Shows ___________________________

        try {
            // convert Json object into Json array
            JSONArray allWatchedVideos1 = object.getJSONObject("video.watches").getJSONArray("data");

            for (int i = 0; i < allWatchedVideos1.length(); i++) {

                JSONObject singleWatchedVideo = allWatchedVideos1.getJSONObject(i);

                JSONObject tvShow = singleWatchedVideo.getJSONObject("data").getJSONObject("tv_show");
                ;

                if (tvShow != null) {
                    titlesOfWatchedTvShowsList.add(new watched(tvShow.optString("title")));
                }
            }

            for (int j = 0; j < titlesOfWatchedTvShowsList.size(); j++) {
                info.append("\n\nTitle: " + titlesOfWatchedTvShowsList.get(j));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getWatchedMoviesFunc(JSONObject object) {
        //_________________________________ Getting Watched Movies ___________________________

        try {
            // convert Json object into Json array
            JSONArray allWatchedVideos = object.getJSONObject("video.watches").getJSONArray("data");

            for (int i = 0; i < allWatchedVideos.length(); i++) {

                JSONObject singleWatchedVideo = null;

                singleWatchedVideo = allWatchedVideos.getJSONObject(i);

                JSONObject movie = singleWatchedVideo.getJSONObject("data").getJSONObject("movie");
                ;

                if (movie != null) {
                    watchesMoviesTitlesList.add(new watched(movie.optString("title")));
                }
            }

            for (int j = 0; j < watchesMoviesTitlesList.size(); j++) {
                info.append("\n\nTitle: " + watchesMoviesTitlesList.get(j).getName());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getLikedTvShowsFunc(JSONObject object) {
        //_________________________________ Getting Liked Tv Shows ___________________________

        try {
            // convert Json object into Json array
            JSONArray likedTvShowsList = object.getJSONObject("television").getJSONArray("data");

            for (int i = 0; i < likedTvShowsList.length(); i++) {

                JSONObject tvShow = likedTvShowsList.getJSONObject(i);

                namesOfLikedTvShowsList.add(new liked(tvShow.optString("name")));


                info.append("\n\nName: " + namesOfLikedTvShowsList.get(i));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getLikedMusicFunc(JSONObject object) {
        //_________________________________ Getting Liked Music ___________________________

        try {
            // convert Json object into Json array
            JSONArray allLikedMusic = object.getJSONObject("music").getJSONArray("data");

            for (int i = 0; i < allLikedMusic.length(); i++) {

                JSONObject music = allLikedMusic.getJSONObject(i);

                if (music != null) {
                    namesOfLikedMusicList.add(new liked(music.optString("name")));
                }
            }

            for (int j = 0; j < namesOfLikedMusicList.size(); j++) {
                info.append("\n\nTitle: " + namesOfLikedMusicList.get(j));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getLikedBooksFunc(JSONObject object) {
        //_________________________________ Getting Liked Books ___________________________

        try {
            // convert Json object into Json array
            JSONArray allLikedBooks = object.getJSONObject("books").getJSONArray("data");

            for (int i = 0; i < allLikedBooks.length(); i++) {

                JSONObject book = allLikedBooks.getJSONObject(i);

                if (book != null) {
                    namesOfLikedBooksList.add(new liked(book.optString("name")));
                }
            }

            for (int j = 0; j < namesOfLikedBooksList.size(); j++) {
                info.append("\n\nTitle: " + namesOfLikedBooksList.get(j));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getLikedGamesFunc(JSONObject object) {
        //_________________________________ Getting Liked Games ___________________________

        try {
            // convert Json object into Json array
            JSONArray allLikedGames = object.getJSONObject("games").getJSONArray("data");

            for (int i = 0; i < allLikedGames.length(); i++) {

                JSONObject game = allLikedGames.getJSONObject(i);


                if (game != null) {
                    namesOfLikedGamesList.add(new games(game.optString("name")));
                }
            }

            for (int j = 0; j < namesOfLikedGamesList.size(); j++) {
                info.append("\n\nTitle: " + namesOfLikedGamesList.get(j));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getLikedMoviesFunc(JSONObject object) {
        //_________________________________ Getting Liked Movies ___________________________

        try {
            // convert Json object into Json array
            JSONObject obj = object.getJSONObject("movies");
            JSONArray likedMoviesList = obj.getJSONArray("data");

            for (int i = 0; i < likedMoviesList.length(); i++) {

                JSONObject movie = likedMoviesList.getJSONObject(i);
                namesOfLikedMoviesList.add(new liked(movie.optString("name")));


                info.append("\n\nName: " + namesOfLikedMoviesList.get(i).getName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getAllLikesItemsFunc(JSONObject object) {
        //_________________________________ Getting all Liked Items ___________________________

        try {
            // convert Json object into Json array
            JSONArray likesObject = object.getJSONObject("likes").getJSONArray("data");

            for (int i = 0; i < likesObject.length(); i++) {

                JSONObject likedThing = likesObject.getJSONObject(i);

                allAllLikedThingsList.add(new allLikedThings(likedThing.optString("name"), likedThing.optString("category")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getPublicInfoFunc(JSONObject object) {

        //_________________________________ Getting Public_Profile ___________________________
        currentUsersFirstName = object.optString("first_name");
        currentUsersLastName = object.optString("last_name");
        currentUsersDOB = object.optString("birthday");
        currentUsersGender = object.optString("gender");

        info.append("\n\nFirst Name: " + currentUsersFirstName + "-----" + "\nEmail: " + Constant.CURRENT_USERS_EMAIL + "\nBirthday: " +
                currentUsersDOB + "\nGender " + currentUsersGender);

    }


    protected void getNews(LoginResult login_result) {
        GraphRequest data_request = GraphRequest.newMeRequest(
                login_result.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject json_object,
                            GraphResponse response) {

                        ArrayList<String> idsList = new ArrayList<String>();
                        ArrayList<String> namesList = new ArrayList<String>();
                        ArrayList<String> createdTimeList = new ArrayList<String>();

                        try {

                            // convert Json object into Json array
                            JSONObject obj = json_object.getJSONObject("news.reads");
                            JSONArray allLikedMusic = obj.getJSONArray("data");

                            for (int i = 0; i < allLikedMusic.length(); i++) {

                                JSONObject music = allLikedMusic.getJSONObject(i);


                                if (music != null) {
                                    idsList.add(music.optString("id"));
                                    namesList.add(music.optString("name"));
                                    createdTimeList.add(music.optString("created_time"));
                                }
                            }

                            for (int j = 0; j < namesList.size(); j++) {
                                info.append("\n\nId: " + idsList.get(j) + "\nTitle: " + namesList.get(j)
                                        + "\nType: " + createdTimeList.get(j));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle permission_param = new Bundle();
        // add the field to get the details of liked pages
        permission_param.putString("fields", "news.reads");
        data_request.setParameters(permission_param);
        data_request.executeAsync();
    }


    protected void getFriends(LoginResult login_result) {

        GraphRequest data_request = GraphRequest.newMeRequest(
                login_result.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject json_object,
                            GraphResponse response) {

                        ArrayList<String> idsList = new ArrayList<String>();
                        ArrayList<String> nameOfFriendsList = new ArrayList<String>();

                        try {

                            // convert Json object into Json array
                            JSONObject obj = json_object.getJSONObject("friends");
                            JSONArray posts = obj.getJSONArray("data");

                            for (int i = 0; i < posts.length(); i++) {

                                JSONObject post = posts.getJSONObject(i);
                                idsList.add(post.optString("id"));
                                //String category = post.optString("category");
                                nameOfFriendsList.add(post.optString("name"));
                                //int count = post.optInt("likes");
                                // print id, page name and number of like of facebook page

                                info.append("\n\nId: " + idsList.get(i) + "\nName: " + nameOfFriendsList.get(i));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle permission_param = new Bundle();
        // add the field to get the details of liked pages
        permission_param.putString("fields", "friends{id,name}");
        data_request.setParameters(permission_param);
        data_request.executeAsync();
    }


    private void getFriendsList(LoginResult loginResult) {

        graphRequest = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject json_object,
                            GraphResponse response) {

                        try {
                            JSONArray jsonArrayFriends = json_object.getJSONObject("friends").getJSONArray("data");
                            JSONObject friendlistObject = jsonArrayFriends.getJSONObject(0);
                            String friendListID = friendlistObject.getString("id");
                            myNewGraphReq(friendListID);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle param = new Bundle();
        param.putString("fields", "friends{id,name}");
        graphRequest.setParameters(param);
        graphRequest.executeAsync();

    }


    private void myNewGraphReq(String friendlistId) {
        final String graphPath = "/" + friendlistId + "/members/";
        AccessToken token = AccessToken.getCurrentAccessToken();
        GraphRequest request = new GraphRequest(token, graphPath, null, HttpMethod.GET, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {
                JSONObject object = graphResponse.getJSONObject();

                ArrayList<String> nameList = new ArrayList<>();

                try {
                    JSONArray arrayOfUsersInFriendList = object.getJSONArray("data");
                /* Do something with the user list */
                /* ex: get first user in list, "name" */
                    for (int i = 0; i < arrayOfUsersInFriendList.length(); i++) {
                        JSONObject user = arrayOfUsersInFriendList.getJSONObject(i);
                        nameList.add(user.getString("name"));

                        info.append("\n\nnName: " + nameList.get(i));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle param = new Bundle();
        param.putString("fields", "name");
        graphRequest.setParameters(param);
        graphRequest.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }


    public void gettingValuesFromFBAndCreatingAccountInDB() {


        if (signUpWithoutFacebook == true) {

            //______________________ Storing the Facebook data in Firebase without personal Info ________________
            Constant.FIREBASE_REF.child("facebookData").push().setValue(
                    new facebookData(Constant.CURRENT_USERS_EMAIL,
                            new movies(namesOfLikedMoviesList, watchesMoviesTitlesList, WTWMoviesTitlesList, ratedMoviesTitlesList),
                            new tvShows(namesOfLikedTvShowsList, titlesOfWatchedTvShowsList, titlesOfWTWTvShowsList, titlesOfRatedTvShowsList),
                            new books(namesOfLikedBooksList, namesOfReadBooksList, namesOfRatedBooksList),
                            new music(namesOfLikedMusicList, namesOfListenedToMusicList, namesOfPlaylistMusicList),
                            allAllLikedThingsList,
                            namesOfLikedGamesList));


            //_____________________________ we are setting this value false so that it never again fetch and store facebook data _______
            Constant.FIREBASE_REF.child("users").child(Constant.CURRENT_USERS_ID).child("signUpWithoutFacebook").setValue(false);


            //______________ starting the questionnaire where left _____________
            startQuestionnaireWhereYouLeftFunc();

        } else {
            //_______________________________ Storing the data(personal info) in Firebase at node 'user' ________________________
            Constant.CURRENT_USERS_ID = Constant.FIREBASE_REF.child("users").push().getKey().toString();

            Constant.FIREBASE_REF.child("users").child(Constant.CURRENT_USERS_ID).setValue(new User(currentUsersFirstName,
                    currentUsersLastName, Constant.CURRENT_USERS_MOBILE_NUM,
                    Constant.CURRENT_USERS_EMAIL, "" /*currentUsersPassword*/,
                    currentUsersGender, currentUsersDOB, ""/*friendOrFamilyNum*/, Constant.CURRENT_ACCOUNTS_USER_PREGNANT,
                    false/*signUpWithoutFB*/, false/*passwordSettedToLoginWithoutFB*/, false/*questionnaireFilled*/, Constant.HAS_SENSOR));


            //_______________________________ Storing the data(likes etc)  in Firebase at node 'facebook_data', that we have fetched from Facebook  ______________________
            Constant.FIREBASE_REF.child("facebookData").push().setValue(
                    new facebookData(Constant.CURRENT_USERS_EMAIL,
                            new movies(namesOfLikedMoviesList, watchesMoviesTitlesList, WTWMoviesTitlesList, ratedMoviesTitlesList),
                            new tvShows(namesOfLikedTvShowsList, titlesOfWatchedTvShowsList, titlesOfWTWTvShowsList, titlesOfRatedTvShowsList),
                            new books(namesOfLikedBooksList, namesOfReadBooksList, namesOfRatedBooksList),
                            new music(namesOfLikedMusicList, namesOfListenedToMusicList, namesOfPlaylistMusicList),
                            allAllLikedThingsList,
                            namesOfLikedGamesList));


            //_______________________________ Storing the data in sharedprefrences _________________________________
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("email", Constant.CURRENT_USERS_EMAIL);
            editor.putString("password", currentUsersPassword);
            editor.putBoolean("questionnaire_filled", false);
            editor.putString("current_user_id", Constant.CURRENT_USERS_ID);
            //editor.putString("device_id",androidId.toString());
            //editor.putString("currentUserPushedKey",key);
            editor.commit();

            //____________________ Starting the questionnaire ________________________
            startQuestionnaireWhereYouLeftFunc();
        }

    }

    private void startQuestionnaireWhereYouLeftFunc() {


        //________________ assigning values to all ArrayLists of questionnaire results, to set the value on each index ___________
        for (int i = 0; i < 10; i++) {
            Constant.agreeablenessAnswersList.add("");
            Constant.conscientiousnessAnswersList.add("");
            Constant.extraversionAnswersList.add("");
            Constant.neutrocismAnswersList.add("");
            Constant.opennessAnswersList.add("");
        }


        //_________ Starting the Activity in which we have 'fragment_container' to set the password to login without facebook ________
        Intent intent = new Intent(getApplicationContext(), questionnaireActivity.class);// we used this activity because there was error in creating fragment in 'fragment_container' thats why we create a new 'fragment_container'
        intent.putExtra("passwordSettedToLoginWithoutFB", passwordSettedToLoginWithoutFB);
        startActivity(intent);

    }

}
