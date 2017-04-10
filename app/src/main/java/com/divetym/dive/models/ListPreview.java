package com.divetym.dive.models;

/**
 * Created by kali_root on 4/6/2017.
 */

public class ListPreview {
    private String title;
    private String subtitle;
    private String action;
    private String imageUrl;

    public ListPreview(String title, String imageUrl) {
        this(title, "", "", imageUrl);
    }

    public ListPreview(String title, String action, String imageUrl){
        this(title, "", action, imageUrl);
    }
    public ListPreview(String title, String subtitle, String action, String imageUrl) {
        this.title = title;
        this.subtitle = subtitle;
        this.action = action;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
