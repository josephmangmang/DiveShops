package com.divetym.dive.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.divetym.dive.rest.constants.ApiConstant;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

/**
 * Created by kali_root on 3/28/2017.
 */

public class DiveShopCourse extends Course implements Parcelable{
    public static final String TAG = DiveShopCourse.class.getSimpleName();
    @SerializedName(ApiConstant.DIVE_SHOP_COURSE_ID)
    private int diveShopCourseId;
    @SerializedName(ApiConstant.DIVE_SHOP_ID)
    private String diveShopUid;
    @SerializedName(ApiConstant.PRICE)
    private BigDecimal price;

    public DiveShopCourse() {
    }

    public DiveShopCourse(int diveShopCourseId, String diveShopUid, BigDecimal price) {
        this.diveShopCourseId = diveShopCourseId;
        this.diveShopUid = diveShopUid;
        this.price = price;
    }

    public int getDiveShopCourseId() {
        return diveShopCourseId;
    }

    public void setDiveShopCourseId(int diveShopCourseId) {
        this.diveShopCourseId = diveShopCourseId;
    }

    public String getDiveShopUid() {
        return diveShopUid;
    }

    public void setDiveShopUid(String diveShopUid) {
        this.diveShopUid = diveShopUid;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "DiveShopCourse{" +
                "diveShopCourseId=" + diveShopCourseId +
                ", diveShopUid='" + diveShopUid + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.diveShopCourseId);
        dest.writeString(this.diveShopUid);
        dest.writeSerializable(this.price);
    }

    protected DiveShopCourse(Parcel in) {
        super(in);
        this.diveShopCourseId = in.readInt();
        this.diveShopUid = in.readString();
        this.price = (BigDecimal) in.readSerializable();
    }

    public static final Creator<DiveShopCourse> CREATOR = new Creator<DiveShopCourse>() {
        @Override
        public DiveShopCourse createFromParcel(Parcel source) {
            return new DiveShopCourse(source);
        }

        @Override
        public DiveShopCourse[] newArray(int size) {
            return new DiveShopCourse[size];
        }
    };
}
