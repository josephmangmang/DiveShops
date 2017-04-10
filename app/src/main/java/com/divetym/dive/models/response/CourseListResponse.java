package com.divetym.dive.models.response;

import com.divetym.dive.models.Course;
import com.divetym.dive.rest.constants.ApiConstant;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kali_root on 4/4/2017.
 */

public class CourseListResponse extends Response{
    @SerializedName(ApiConstant.COURSES)
    private List<Course> courses;

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return super.toString() + "\nCourseListResponse{" +
                "courses=" + courses +
                '}';
    }
}
