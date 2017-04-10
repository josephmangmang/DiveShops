package com.divetym.dive.models.response;

import com.divetym.dive.rest.constants.ApiConstant;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kali_root on 4/4/2017.
 */

public class Response {
    @SerializedName(ApiConstant.ERROR)
    @Expose
    private boolean error;
    @SerializedName(ApiConstant.MESSAGE)
    @Expose
    private String message;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return super.toString() + "\nResponse{" +
                "error=" + error +
                ", message='" + message + '\'' +
                '}';
    }
}
