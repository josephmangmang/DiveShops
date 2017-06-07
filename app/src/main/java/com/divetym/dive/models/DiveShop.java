package com.divetym.dive.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.divetym.dive.rest.constants.ApiConstant;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kali_root on 3/27/2017.
 */

public class DiveShop extends User  {
    private static final String PREVIEW_ACTION_BOAT = "VIEW";
    private static final String PREVIEW_ACTION_COURSE = "BOOK NOW";

    @SerializedName(ApiConstant.DIVE_SHOP_ID)
    private String diveShopUid;
    @SerializedName(ApiConstant.DESCRIPTION)
    private String description;
    @SerializedName(ApiConstant.CONTACT_NUMBER)
    private String contactNumber;
    @SerializedName(ApiConstant.ADDRESS)
    private String address;
    @SerializedName(ApiConstant.LATITUDE)
    private double latitiude;
    @SerializedName(ApiConstant.LONGITUDE)
    private double longitude;
    @SerializedName(ApiConstant.PRICE_PER_DIVE)
    private BigDecimal pricePerDive;
    @SerializedName(ApiConstant.SPECIAL_SERVICE)
    private String specialService;
    @SerializedName(ApiConstant.COURSES)
    private List<DiveShopCourse> courses;
    @SerializedName(ApiConstant.BOATS)
    private List<Boat> boats;
    @SerializedName(ApiConstant.GUIDES)
    private List<Guide> guides;

    private List<ListPreview> coursePreviews;
    private List<ListPreview> boatPreviews;
    private List<ListPreview> guidePreviews;

    public String getDiveShopUid() {
        return diveShopUid;
    }

    public void setDiveShopUid(String diveShopUid) {
        this.diveShopUid = diveShopUid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitiude() {
        return latitiude;
    }

    public void setLatitiude(double latitiude) {
        this.latitiude = latitiude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getPricePerDive() {
        return pricePerDive;
    }

    public void setPricePerDive(BigDecimal pricePerDive) {
        this.pricePerDive = pricePerDive;
    }

    public String getSpecialService() {
        return specialService;
    }

    public void setSpecialService(String specialService) {
        this.specialService = specialService;
    }

    public List<DiveShopCourse> getCourses() {
        return courses;
    }

    public void setCourses(List<DiveShopCourse> courses) {
        this.courses = courses;
        prepareCoursePreviews(courses);
    }

    public List<Boat> getBoats() {
        return boats;
    }

    public void setBoats(List<Boat> boats) {
        this.boats = boats;
        prepareBoatPreviews(boats);
    }

    public List<Guide> getGuides() {
        return guides;
    }

    public void setGuides(List<Guide> guides) {
        this.guides = guides;
        prepareGuidePreviews(guides);
    }

    private void prepareGuidePreviews(List<Guide> guides) {
        guidePreviews = new ArrayList<>();
        if (guides != null) {
            for (int i = 0; i < guides.size(); i++) {
                Guide guide = guides.get(i);
                guidePreviews.add(new ListPreview(i, guide.getName(), guide.getImageUrl()));
            }
        }
    }

    public void prepareCoursePreviews(List<DiveShopCourse> courses) {
        coursePreviews = new ArrayList<>();
        if (courses != null) {
            for (int i = 0; i < courses.size(); i++) {
                DiveShopCourse course = courses.get(i);
                coursePreviews.add(new ListPreview(i, course.getName(), course.getPrice().toString(), PREVIEW_ACTION_COURSE, course.getImageUrl()));
            }
        }
    }

    public void prepareBoatPreviews(List<Boat> boats) {
        boatPreviews = new ArrayList<>();
        if (boats != null) {
            for (int i = 0; i < boats.size(); i++) {
                Boat boat = boats.get(i);
                boatPreviews.add(new ListPreview(i, boat.getName(), boat.getImageUrl()));
            }
        }
    }

    public List<ListPreview> getCoursePreviews() {
        if (coursePreviews == null) {
            prepareCoursePreviews(courses);
        }
        return coursePreviews;
    }

    public List<ListPreview> getBoatPreviews() {
        if (boatPreviews == null) {
            prepareBoatPreviews(boats);
        }
        return boatPreviews;
    }

    public List<ListPreview> getGuidePreviews() {
        if (guidePreviews == null) {
            prepareGuidePreviews(guides);
        }
        return guidePreviews;
    }

    @Override
    public String toString() {
        return "Dive_Shop{" +
                "diveShopUid=" + diveShopUid +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", address='" + address + '\'' +
                ", latitiude=" + latitiude +
                ", longitude=" + longitude +
                ", pricePerDive=" + pricePerDive +
                ", specialService='" + specialService + '\'' +
                '}';
    }

    public DiveShop() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.diveShopUid);
        dest.writeString(this.description);
        dest.writeString(this.contactNumber);
        dest.writeString(this.address);
        dest.writeDouble(this.latitiude);
        dest.writeDouble(this.longitude);
        dest.writeSerializable(this.pricePerDive);
        dest.writeString(this.specialService);
        dest.writeTypedList(this.courses);
        dest.writeTypedList(this.boats);
        dest.writeTypedList(this.guides);
        dest.writeTypedList(this.coursePreviews);
        dest.writeTypedList(this.boatPreviews);
        dest.writeTypedList(this.guidePreviews);
    }

    protected DiveShop(Parcel in) {
        super(in);
        this.diveShopUid = in.readString();
        this.description = in.readString();
        this.contactNumber = in.readString();
        this.address = in.readString();
        this.latitiude = in.readDouble();
        this.longitude = in.readDouble();
        this.pricePerDive = (BigDecimal) in.readSerializable();
        this.specialService = in.readString();
        this.courses = in.createTypedArrayList(DiveShopCourse.CREATOR);
        this.boats = in.createTypedArrayList(Boat.CREATOR);
        this.guides = in.createTypedArrayList(Guide.CREATOR);
        this.coursePreviews = in.createTypedArrayList(ListPreview.CREATOR);
        this.boatPreviews = in.createTypedArrayList(ListPreview.CREATOR);
        this.guidePreviews = in.createTypedArrayList(ListPreview.CREATOR);
    }

    public static final Creator<DiveShop> CREATOR = new Creator<DiveShop>() {
        @Override
        public DiveShop createFromParcel(Parcel source) {
            return new DiveShop(source);
        }

        @Override
        public DiveShop[] newArray(int size) {
            return new DiveShop[size];
        }
    };
}
