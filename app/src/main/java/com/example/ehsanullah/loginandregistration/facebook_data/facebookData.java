package com.example.ehsanullah.loginandregistration.facebook_data;

import java.util.ArrayList;

/**
 * Created by Ehsan Ullah on 7/13/2017.
 */

public class facebookData {

    String email;
    movies movie = new movies();
    tvShows tvshow = new tvShows();
    books books = new books();
    music music = new music();
    ArrayList<allLikedThings> allLikedThingsList = new ArrayList<allLikedThings>();
    ArrayList<games> likedGamesList = new ArrayList<games>();


    public facebookData() {
    }

    public facebookData(String email, movies movie, tvShows tvshow, com.example.ehsanullah.loginandregistration.facebook_data.books books, com.example.ehsanullah.loginandregistration.facebook_data.music music, ArrayList<allLikedThings> allLikedThingsList, ArrayList<games> likedGamesList) {
        this.email = email;
        this.movie = movie;
        this.tvshow = tvshow;
        this.books = books;
        this.music = music;
        this.allLikedThingsList = allLikedThingsList;
        this.likedGamesList = likedGamesList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public movies getMovie() {
        return movie;
    }

    public void setMovie(movies movie) {
        this.movie = movie;
    }

    public tvShows getTvshow() {
        return tvshow;
    }

    public void setTvshow(tvShows tvshow) {
        this.tvshow = tvshow;
    }

    public com.example.ehsanullah.loginandregistration.facebook_data.books getBooks() {
        return books;
    }

    public void setBooks(com.example.ehsanullah.loginandregistration.facebook_data.books books) {
        this.books = books;
    }

    public com.example.ehsanullah.loginandregistration.facebook_data.music getMusic() {
        return music;
    }

    public void setMusic(com.example.ehsanullah.loginandregistration.facebook_data.music music) {
        this.music = music;
    }

    public ArrayList<allLikedThings> getAllLikedThingsList() {
        return allLikedThingsList;
    }

    public void setAllLikedThingsList(ArrayList<allLikedThings> allLikedThingsList) {
        this.allLikedThingsList = allLikedThingsList;
    }

    public ArrayList<games> getLikedGamesList() {
        return likedGamesList;
    }

    public void setLikedGamesList(ArrayList<games> likedGamesList) {
        this.likedGamesList = likedGamesList;
    }
}

