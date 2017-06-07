package com.divetym.dive.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.divetym.dive.rest.constants.ApiConstant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kali_root on 3/27/2017.
 */

public class Diver extends User {
    @SerializedName(ApiConstant.DIVER_ID)
    private String diverUid;

    public String getDiverUid() {
        return diverUid;
    }

    public void setDiverUid(String diverUid) {
        this.diverUid = diverUid;
    }

    @Override
    public String toString() {
        return "Diver{" +
                "diverUid=" + diverUid +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.diverUid);
    }

    public Diver() {
    }

    protected Diver(Parcel in) {
        super(in);
        this.diverUid = in.readString();
    }

    public static final Creator<Diver> CREATOR = new Creator<Diver>() {
        @Override
        public Diver createFromParcel(Parcel source) {
            return new Diver(source);
        }

        @Override
        public Diver[] newArray(int size) {
            return new Diver[size];
        }
    };
}
