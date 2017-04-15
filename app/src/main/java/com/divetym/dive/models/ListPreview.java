package com.divetym.dive.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kali_root on 4/6/2017.
 */

public class ListPreview implements Parcelable {
    private int position;
    private String title;
    private String subtitle;
    private String action;
    private String imageUrl;

    public ListPreview(int position, String title, String imageUrl) {
        this(position, title, "", "", imageUrl);
    }

    public ListPreview(int position, String title, String action, String imageUrl) {
        this(position, title, "", action, imageUrl);
    }

    public ListPreview(int position, String title, String subtitle, String action, String imageUrl) {
        this.position = position;
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "ListPreview{" +
                "title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", action='" + action + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.subtitle);
        dest.writeString(this.action);
        dest.writeString(this.imageUrl);
    }

    protected ListPreview(Parcel in) {
        this.title = in.readString();
        this.subtitle = in.readString();
        this.action = in.readString();
        this.imageUrl = in.readString();
    }

    public static final Parcelable.Creator<ListPreview> CREATOR = new Parcelable.Creator<ListPreview>() {
        @Override
        public ListPreview createFromParcel(Parcel source) {
            return new ListPreview(source);
        }

        @Override
        public ListPreview[] newArray(int size) {
            return new ListPreview[size];
        }
    };
}
