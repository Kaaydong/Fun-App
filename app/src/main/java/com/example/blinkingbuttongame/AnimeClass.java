package com.example.blinkingbuttongame;

import java.util.List;

public class AnimeClass {

    String request_hash;
    boolean request_cached;
    int request_cache_expiry;
    List<urlLinks> pictures;

    public String getRequest_hash() {
        return request_hash;
    }

    public void setRequest_hash(String request_hash) {
        this.request_hash = request_hash;
    }

    public boolean isRequest_cached() {
        return request_cached;
    }

    public void setRequest_cached(boolean request_cached) {
        this.request_cached = request_cached;
    }

    public int getRequest_cache_expiry() {
        return request_cache_expiry;
    }

    public void setRequest_cache_expiry(int request_cache_expiry) {
        this.request_cache_expiry = request_cache_expiry;
    }

    public List<urlLinks> getPictures() {
        return pictures;
    }

    public void setPictures(List<urlLinks> pictures) {
        this.pictures = pictures;
    }

    public String returnURL()
    {
        int length = pictures.size();
        String url = pictures.get((int)(Math.random()*length)).getLarge();
        return url;
    }

    private class urlLinks
    {
        String large, small;

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }
    }
}
