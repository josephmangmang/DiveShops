package com.divetym.dive.ui.models;

import com.divetym.dive.ui.rest.constants.ApiConstant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kali_root on 3/27/2017.
 */

public class User {
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
        DiveShop
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
}
