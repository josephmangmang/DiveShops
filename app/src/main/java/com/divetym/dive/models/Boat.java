package com.divetym.dive.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.divetym.dive.models.common.ThumbnailEntity;
import com.divetym.dive.rest.constants.ApiConstant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kali_root on 3/27/2017.
 */

public class Boat extends ThumbnailEntity implements Parcelable {
    @SerializedName(ApiConstant.BOAT_ID)
    private int boatId;
    @SerializedName(ApiConstant.DIVE_SHOP_ID)
    private String diveShopUid;
    @SerializedName(ApiConstant.DESCRIPTION)
    private String description;

    public Boat() {
    }

    public Boat(int boatId, String diveShopUid, String name, String description, String imageUrl) {
        this.boatId = boatId;
        this.diveShopUid = diveShopUid;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public int getBoatId() {
        return boatId;
    }

    public void setBoatId(int boatId) {
        this.boatId = boatId;
    }

    public String getDiveShopUid() {
        return diveShopUid;
    }

    public void setDiveShopUid(String diveShopUid) {
        this.diveShopUid = diveShopUid;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Boat{" +
                "boatId=" + boatId +
                ", diveShopUid=" + diveShopUid +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.boatId);
        dest.writeString(this.diveShopUid);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.imageUrl);
    }

    protected Boat(Parcel in) {
        this.boatId = in.readInt();
        this.diveShopUid = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.imageUrl = in.readString();
    }

    public static final Creator<Boat> CREATOR = new Creator<Boat>() {
        @Override
        public Boat createFromParcel(Parcel source) {
            return new Boat(source);
        }

        @Override
        public Boat[] newArray(int size) {
            return new Boat[size];
        }
    };
}
