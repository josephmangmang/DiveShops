package com.divetym.dive.models.response;

import com.divetym.dive.models.User;
import com.divetym.dive.rest.constants.ApiConstant;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kali_root on 3/30/2017.
 */

public class UserResponse extends Response {

    @SerializedName(ApiConstant.USER)
    @Expose
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return super.toString() + "\nUserResponse{" +
                "user=" + user +
                '}';
    }
}
