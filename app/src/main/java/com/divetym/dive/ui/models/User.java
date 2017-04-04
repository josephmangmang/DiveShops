package com.divetym.dive.ui.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kali_root on 3/27/2017.
 */

public class User {
    @SerializedName("user_id")
    private int userUid;
    @SerializedName("email")
    private String email;
    @SerializedName("create_time")
    private String createdTime;
    @SerializedName("is_diver")
    private boolean isDiver;
    @SerializedName("auth_key")
    private String authKey;

    public User() {
    }

    public User(int userUid, String email, boolean isDiver, String authKey) {
        this.userUid = userUid;
        this.email = email;
        this.isDiver = isDiver;
        this.authKey = authKey;
    }

    public User(int userUid, String email, String createdTime, boolean isDiver) {
        this.userUid = userUid;
        this.email = email;
        this.createdTime = createdTime;
        this.isDiver = isDiver;
    }

    public int getUserUid() {
        return userUid;
    }

    public void setUserUid(int userUid) {
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

    public boolean isDiver() {
        return isDiver;
    }

    public void setDiver(boolean diver) {
        isDiver = diver;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }
}
