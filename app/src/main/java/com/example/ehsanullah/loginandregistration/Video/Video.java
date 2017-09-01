package com.example.ehsanullah.loginandregistration.Video;

import com.google.api.services.youtube.model.ResourceId;

/**
 * Created by SZ on 7/30/2017.
 */

public class Video {

    ResourceId rid;
    String videoTitle;
    String videoId;
    String videoThumbnailUrl;

    public String getVideoThumbnailUrl() {
        return videoThumbnailUrl;
    }

    public void setVideoThumbnailUrl(String videoId) {
        this.videoThumbnailUrl ="https://img.youtube.com/vi/"+videoId+"/maxresdefault.jpg";
    }

    public ResourceId getRid() {
        return rid;
    }

    public void setRid(ResourceId rid) {
        this.rid = rid;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
