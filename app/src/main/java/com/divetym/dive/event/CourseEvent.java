package com.divetym.dive.event;

import com.divetym.dive.models.Course;

/**
 * Created by kali_root on 6/9/2017.
 */

public class CourseEvent {
    private Course course;

    public CourseEvent(Course course) {
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
