package com.divetym.dive.ui.models.response;

import com.divetym.dive.ui.models.Course;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kali_root on 4/4/2017.
 */

public class CourseListResponse extends Response{
    @SerializedName("courses")
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
