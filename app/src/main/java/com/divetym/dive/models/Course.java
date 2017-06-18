package com.divetym.dive.models;

import android.os.Parcel;

import com.divetym.dive.models.common.ThumbnailEntity;
import com.divetym.dive.rest.constants.ApiConstant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kali_root on 3/27/2017.
 */

public class Course extends ThumbnailEntity {
    @SerializedName(ApiConstant.COURSE_ID)
    private int courseId;
    @SerializedName(ApiConstant.WHAT_YOU_WILL_LEARN)
    private String whatYouWillLearn;
    @SerializedName(ApiConstant.WHO_SHOULD_TAKE_THIS_COURSE)
    private String whoShouldTakeThisCourse;
    @SerializedName(ApiConstant.SCUBA_GEAR_YOU_WILL_USE)
    private String scubaGearYouWillUse;
    @SerializedName(ApiConstant.OFFERED_BY)
    private String offeredBy;

    public String getWhoShouldTakeThisCourse() {
        return whoShouldTakeThisCourse;
    }

    public void setWhoShouldTakeThisCourse(String whoShouldTakeThisCourse) {
        this.whoShouldTakeThisCourse = whoShouldTakeThisCourse;
    }

    public String getScubaGearYouWillUse() {
        return scubaGearYouWillUse;
    }

    public void setScubaGearYouWillUse(String scubaGearYouWillUse) {
        this.scubaGearYouWillUse = scubaGearYouWillUse;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getWhatYouWillLearn() {
        return whatYouWillLearn;
    }

    public void setWhatYouWillLearn(String whatYouWillLearn) {
        this.whatYouWillLearn = whatYouWillLearn;
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
                ", whatYouWillLearn='" + whatYouWillLearn + '\'' +
                ", whoShouldTakeThisCourse='" + whoShouldTakeThisCourse + '\'' +
                ", scubaGearYouWillUse='" + scubaGearYouWillUse + '\'' +
                ", offeredBy='" + offeredBy + '\'' +
                '}';
    }

    public Course() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.courseId);
        dest.writeString(this.whatYouWillLearn);
        dest.writeString(this.whoShouldTakeThisCourse);
        dest.writeString(this.scubaGearYouWillUse);
        dest.writeString(this.offeredBy);
    }

    protected Course(Parcel in) {
        super(in);
        this.courseId = in.readInt();
        this.whatYouWillLearn = in.readString();
        this.whoShouldTakeThisCourse = in.readString();
        this.scubaGearYouWillUse = in.readString();
        this.offeredBy = in.readString();
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel source) {
            return new Course(source);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };
}
