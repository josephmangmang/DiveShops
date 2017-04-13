package com.divetym.dive.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.divetym.dive.rest.constants.ApiConstant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kali_root on 3/27/2017.
 */

public class Diver extends User implements Parcelable {
    @SerializedName(ApiConstant.DIVER_ID)
    private String diverUid;
    @SerializedName(ApiConstant.NAME)
    private String name;

    public Diver() {
    }

    public Diver(String userId, String email) {
        super(userId, email, AccountType.Diver);
    }

    public String getDiverUid() {
        return diverUid;
    }

    public void setDiverUid(String diverUid) {
        this.diverUid = diverUid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        dest.writeString(this.diverUid);
        dest.writeString(this.name);
    }

    protected Diver(Parcel in) {
        this.diverUid = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<Diver> CREATOR = new Parcelable.Creator<Diver>() {
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
