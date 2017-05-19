package com.divetym.dive.models;

import android.os.Parcel;

import com.divetym.dive.models.common.ThumbnailEntity;
import com.divetym.dive.rest.constants.ApiConstant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kali_root on 5/16/2017.
 */

public class Guide extends ThumbnailEntity {
    @SerializedName(ApiConstant.GUIDE_ID)
    private int guideId;
    @SerializedName(ApiConstant.DIVE_SHOP_ID)
    private String diveShopUid;

    public int getGuideId() {
        return guideId;
    }

    public void setGuideId(int guideId) {
        this.guideId = guideId;
    }

    public String getDiveShopUid() {
        return diveShopUid;
    }

    public void setDiveShopUid(String diveShopUid) {
        this.diveShopUid = diveShopUid;
    }

    public Guide() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.guideId);
        dest.writeString(this.diveShopUid);
    }

    protected Guide(Parcel in) {
        super(in);
        this.guideId = in.readInt();
        this.diveShopUid = in.readString();
    }

    public static final Creator<Guide> CREATOR = new Creator<Guide>() {
        @Override
        public Guide createFromParcel(Parcel source) {
            return new Guide(source);
        }

        @Override
        public Guide[] newArray(int size) {
            return new Guide[size];
        }
    };
}
