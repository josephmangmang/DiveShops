package com.divetym.dive.ui.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kali_root on 3/27/2017.
 */

public class Course {
    @SerializedName("course_id")
    private int courseId;
    @SerializedName("name")
    private int description;
    @SerializedName("photo_cover")
    private String photoCoverUrl;
    @SerializedName("offered_by")
    private String offeredBy;

    public Course() {
    }

    public Course(int courseId, int description, String photoCoverUrl, String offeredBy) {
        this.courseId = courseId;
        this.description = description;
        this.photoCoverUrl = photoCoverUrl;
        this.offeredBy = offeredBy;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getDescription() {
        return description;
    }

    public void setDescription(int description) {
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
