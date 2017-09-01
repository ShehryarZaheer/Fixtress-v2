package com.example.ehsanullah.loginandregistration.facebook_data;

import java.util.ArrayList;

/**
 * Created by Ehsan Ullah on 7/19/2017.
 */

public class music {

    ArrayList<liked> likedMusic = new ArrayList<>();
    ArrayList<musicHaveListenedTo> listenedToMusicList = new ArrayList<>();
    ArrayList<musicPlaylist> playListMusicList = new ArrayList<>();

    public music() {
    }

    public music(ArrayList<liked> likedMusic, ArrayList<musicHaveListenedTo> listenedToMusicList, ArrayList<musicPlaylist> playListMusicList) {
        this.likedMusic = likedMusic;
        this.listenedToMusicList = listenedToMusicList;
        this.playListMusicList = playListMusicList;
    }

    public ArrayList<liked> getLikedMusic() {
        return likedMusic;
    }

    public void setLikedMusic(ArrayList<liked> likedMusic) {
        this.likedMusic = likedMusic;
    }

    public ArrayList<musicHaveListenedTo> getListenedToMusicList() {
        return listenedToMusicList;
    }

    public void setListenedToMusicList(ArrayList<musicHaveListenedTo> listenedToMusicList) {
        this.listenedToMusicList = listenedToMusicList;
    }

    public ArrayList<musicPlaylist> getPlayListMusicList() {
        return playListMusicList;
    }

    public void setPlayListMusicList(ArrayList<musicPlaylist> playListMusicList) {
        this.playListMusicList = playListMusicList;
    }
}
