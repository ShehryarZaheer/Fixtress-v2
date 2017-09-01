package com.example.ehsanullah.loginandregistration.facebook_data;

import java.util.ArrayList;

/**
 * Created by Ehsan Ullah on 7/13/2017.
 */

public class books {

    ArrayList<liked> likedList = new ArrayList<>();
    ArrayList<readBooks> readList = new ArrayList<>();
    ArrayList<rated> ratedList = new ArrayList<>();

    public books() {
    }

    public books(ArrayList<liked> likedList, ArrayList<readBooks> readList, ArrayList<rated> ratedList) {
        this.likedList = likedList;
        this.readList = readList;
        this.ratedList = ratedList;
    }

    public ArrayList<liked> getLikedList() {
        return likedList;
    }

    public void setLikedList(ArrayList<liked> likedList) {
        this.likedList = likedList;
    }

    public ArrayList<readBooks> getReadList() {
        return readList;
    }

    public void setReadList(ArrayList<readBooks> readList) {
        this.readList = readList;
    }

    public ArrayList<rated> getRatedList() {
        return ratedList;
    }

    public void setRatedList(ArrayList<rated> ratedList) {
        this.ratedList = ratedList;
    }
}
