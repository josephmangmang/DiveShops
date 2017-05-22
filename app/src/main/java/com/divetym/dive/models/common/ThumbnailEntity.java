package com.divetym.dive.models.common;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.divetym.dive.rest.constants.ApiConstant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kali_root on 5/7/2017.
 */

public class ThumbnailEntity implements Parcelable {
    public static final String TAG = ThumbnailEntity.class.getSimpleName();
    @SerializedName(ApiConstant.NAME)
    protected String name;
    @SerializedName(ApiConstant.IMAGE)
    protected String imageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ThumbnailEntity() {
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = ((ThumbnailEntity) obj).getName().equals(this.getName());
        Log.d(TAG, "equals obj1: " + getName() + " obj2: " + ((ThumbnailEntity) obj).getName() + " = " + equals);
        return equals;
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.imageUrl);
    }

    protected ThumbnailEntity(Parcel in) {
        this.name = in.readString();
        this.imageUrl = in.readString();
    }

}
