package com.divetym.dive.models;

import com.divetym.dive.rest.constants.ApiConstant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kali_root on 3/27/2017.
 */

public class Course {
    @SerializedName(ApiConstant.COURSE_ID)
    private int courseId;
    @SerializedName(ApiConstant.NAME)
    private String name;
    @SerializedName(ApiConstant.DESCRIPTION)
    private String description;
    @SerializedName(ApiConstant.PHOTO_COVER)
    private String photoCoverUrl;
    @SerializedName(ApiConstant.OFFERED_BY)
    private String offeredBy;

    public Course() {
    }

    public Course(int courseId, String name, String description, String photoCoverUrl, String offeredBy) {
        this.courseId = courseId;
        this.name = name;
        this.description = description;
        this.photoCoverUrl = photoCoverUrl;
        this.offeredBy = offeredBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoCoverUrl() {
        return photoCoverUrl;
    }

    public void setPhotoCoverUrl(String photoCoverUrl) {
        this.photoCoverUrl = photoCoverUrl;
    }

    public String getOfferedBy() {
        return offeredBy;
    }

    public void setOfferedBy(String offeredBy) {
        this.offeredBy = offeredBy;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", description=" + description +
                ", photoCoverUrl='" + photoCoverUrl + '\'' +
                ", offeredBy='" + offeredBy + '\'' +
                '}';
    }
}
