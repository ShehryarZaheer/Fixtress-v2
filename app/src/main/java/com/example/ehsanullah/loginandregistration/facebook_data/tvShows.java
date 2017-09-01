package com.example.ehsanullah.loginandregistration.facebook_data;

import java.util.ArrayList;

/**
 * Created by Ehsan Ullah on 7/13/2017.
 */

public class tvShows {

    ArrayList<liked> likedList = new ArrayList<>();
    ArrayList<watched> watchedList = new ArrayList<>();
    ArrayList<wantsToWatch> wtwList = new ArrayList<>();
    ArrayList<rated> ratedList = new ArrayList<>();

    public tvShows() {
    }

    public tvShows(ArrayList<liked> likedList, ArrayList<watched> watchedList, ArrayList<wantsToWatch> wtwList, ArrayList<rated> ratedList) {
        this.likedList = likedList;
        this.watchedList = watchedList;
        this.wtwList = wtwList;
        this.ratedList = ratedList;
    }

    public ArrayList<liked> getLikedList() {
        return likedList;
    }

    public void setLikedList(ArrayList<liked> likedList) {
        this.likedList = likedList;
    }

    public ArrayList<watched> getWatchedList() {
        return watchedList;
    }

    public void setWatchedList(ArrayList<watched> watchedList) {
        this.watchedList = watchedList;
    }

    public ArrayList<wantsToWatch> getWtwList() {
        return wtwList;
    }

    public void setWtwList(ArrayList<wantsToWatch> wtwList) {
        this.wtwList = wtwList;
    }

    public ArrayList<rated> getRatedList() {
        return ratedList;
    }

    public void setRatedList(ArrayList<rated> ratedList) {
        this.ratedList = ratedList;
    }
}
