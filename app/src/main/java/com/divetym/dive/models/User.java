package com.divetym.dive.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.divetym.dive.models.common.ThumbnailEntity;
import com.divetym.dive.rest.constants.ApiConstant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kali_root on 3/27/2017.
 */

public class User extends ThumbnailEntity implements Parcelable {
    @SerializedName(ApiConstant.USER_ID)
    private String userUid;
    @SerializedName(ApiConstant.EMAIL)
    private String email;
    @SerializedName(ApiConstant.CREATE_TIME)
    private String createdTime;
    @SerializedName(ApiConstant.ACCOUNT_TYPE)
    private AccountType accountType;
    @SerializedName(ApiConstant.AUTH_KEY)
    private String authKey;

    public enum AccountType {
        @SerializedName(ApiConstant.DIVER)
        Diver,
        @SerializedName(ApiConstant.DIVE_SHOP)
        Dive_Shop
    }

    public User() {
    }

    public User(String userUid, String email) {
        this.userUid = userUid;
        this.email = email;
    }

    public User(String userUid, String email, String authKey) {
        this.userUid = userUid;
        this.email = email;
        this.authKey = authKey;
    }

    public User(String userUid, String email, AccountType accountType) {
        this.userUid = userUid;
        this.email = email;
        this.accountType = accountType;
    }

    public User(String userUid, String email, String createdTime, AccountType accountType) {
        this.userUid = userUid;
        this.email = email;
        this.createdTime = createdTime;
        this.accountType = accountType;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return "User{" +
                "userUid=" + userUid +
                ", email='" + email + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", accountType=" + accountType +
                ", authKey='" + authKey + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.userUid);
        dest.writeString(this.email);
        dest.writeString(this.createdTime);
        dest.writeInt(this.accountType == null ? -1 : this.accountType.ordinal());
        dest.writeString(this.authKey);
    }

    protected User(Parcel in) {
        super(in);
        this.userUid = in.readString();
        this.email = in.readString();
        this.createdTime = in.readString();
        int tmpAccountType = in.readInt();
        this.accountType = tmpAccountType == -1 ? null : AccountType.values()[tmpAccountType];
        this.authKey = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
