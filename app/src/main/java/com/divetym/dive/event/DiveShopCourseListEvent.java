package com.divetym.dive.event;

import com.divetym.dive.models.DiveShopCourse;

import java.util.List;

/**
 * Created by kali_root on 6/9/2017.
 */

public class DiveShopCourseListEvent {
    private List<DiveShopCourse> diveShopCourses;

    public DiveShopCourseListEvent(List<DiveShopCourse> diveShopCourses) {
        this.diveShopCourses = diveShopCourses;
    }

    public List<DiveShopCourse> getDiveShopCourses() {
        return diveShopCourses;
    }

    public void setDiveShopCourses(List<DiveShopCourse> diveShopCourses) {
        this.diveShopCourses = diveShopCourses;
    }
}
