package com.divetym.dive.ui.models;

import com.divetym.dive.ui.rest.constants.ApiConstant;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kali_root on 3/27/2017.
 */

public class DiveShop extends User {
    private static final String PREVIEW_ACTION_BOAT = "VIEW";
    private static final String PREVIEW_ACTION_COURSE = "BOOK NOW";

    @SerializedName(ApiConstant.DIVE_SHOP_ID)
    private String diveShopUid;
    @SerializedName(ApiConstant.NAME)
    private String name;
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

    private List<ListPreview> coursePreviews;
    private List<ListPreview> boatPreviews;

    public DiveShop() {
    }

    public DiveShop(String userId, String email) {
        super(userId, email, AccountType.Dive_Shop);
    }

    public DiveShop(String userId, String email, String diveShopUid, String name,
                    String description, String contactNumber, String address,
                    double latitiude, double longitude, BigDecimal pricePerDive,
                    String specialService, List<DiveShopCourse> courses, List<Boat> boats) {
        super(userId, email, AccountType.Dive_Shop);
        this.diveShopUid = diveShopUid;
        this.name = name;
        this.description = description;
        this.contactNumber = contactNumber;
        this.address = address;
        this.latitiude = latitiude;
        this.longitude = longitude;
        this.pricePerDive = pricePerDive;
        this.specialService = specialService;
        this.courses = courses;
        this.boats = boats;
        prepareBoatPreviews(boats);
        prepareCoursePreviews(courses);
    }

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

    public void prepareCoursePreviews(List<DiveShopCourse> courses) {
        coursePreviews = new ArrayList<>();
        if (courses != null) {
            for (int i = 0; i < courses.size(); i++) {
                DiveShopCourse course = courses.get(i);
                coursePreviews.add(new ListPreview(course.getName(), course.getPrice().toString(), PREVIEW_ACTION_COURSE, course.getPhotoCoverUrl()));
            }
        }
    }

    public void prepareBoatPreviews(List<Boat> boats) {
        boatPreviews = new ArrayList<>();
        if (boats != null) {
            for (int i = 0; i < boats.size(); i++) {
                Boat boat = boats.get(i);
                boatPreviews.add(new ListPreview(boat.getName(),PREVIEW_ACTION_BOAT, boat.getImageUrl()));
            }
        }
    }

    public List<ListPreview> getCoursePreviews() {
        if (coursePreviews == null && courses != null && courses.size() > 0) {
            prepareCoursePreviews(courses);
        }
        return coursePreviews;
    }

    public List<ListPreview> getBoatPreviews() {
        if (boatPreviews == null && boats != null && boats.size() > 0) {
            prepareBoatPreviews(boats);
        }
        return boatPreviews;
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
}
