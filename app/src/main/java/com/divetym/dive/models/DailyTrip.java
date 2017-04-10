package com.divetym.dive.models;

import com.divetym.dive.rest.constants.ApiConstant;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by kali_root on 3/27/2017.
 */

public class DailyTrip {
    @SerializedName(ApiConstant.DAILY_TRIP_ID)
    private int dailyTripId;
    @SerializedName(ApiConstant.DIVE_SHOP_ID)
    private String diveShopUid;
    @SerializedName(ApiConstant.GROUP_SIZE)
    private int groupSize;
    @SerializedName(ApiConstant.NUMBER_OF_DIVE)
    private int numberOfDive;
    @SerializedName(ApiConstant.DATE)
    private String date;
    @SerializedName(ApiConstant.CREATE_TIME)
    private String createTime;
    @SerializedName(ApiConstant.PRICE)
    private BigDecimal price;
    @SerializedName(ApiConstant.PRICE_NOTE)
    private String priceNote;

    @SerializedName(ApiConstant.BOATS)
    private List<DailyTripBoat> boats;
    @SerializedName(ApiConstant.DIVE_SITES)
    private List<DailyTripDiveSite> sites;
    @SerializedName(ApiConstant.GUIDES)
    private List<DailyTripGuide> guides;
    @SerializedName(ApiConstant.GUESTS)
    private List<DailyTripGuest> guests;

    public DailyTrip() {
    }

    public DailyTrip(int dailyTripId, String diveShopUid, int groupSize, int numberOfDive, String date, String createTime, BigDecimal price, String priceNote) {
        this.dailyTripId = dailyTripId;
        this.diveShopUid = diveShopUid;
        this.groupSize = groupSize;
        this.numberOfDive = numberOfDive;
        this.date = date;
        this.createTime = createTime;
        this.price = price;
        this.priceNote = priceNote;
    }

    public DailyTrip(int dailyTripId, String diveShopUid, int groupSize, int numberOfDive, String date, String createTime, BigDecimal price, String priceNote, List<DailyTripBoat> boats, List<DailyTripDiveSite> sites, List<DailyTripGuide> guides, List<DailyTripGuest> guests) {
        this.dailyTripId = dailyTripId;
        this.diveShopUid = diveShopUid;
        this.groupSize = groupSize;
        this.numberOfDive = numberOfDive;
        this.date = date;
        this.createTime = createTime;
        this.price = price;
        this.priceNote = priceNote;
        this.boats = boats;
        this.sites = sites;
        this.guides = guides;
        this.guests = guests;
    }

    public int getDailyTripId() {
        return dailyTripId;
    }

    public void setDailyTripId(int dailyTripId) {
        this.dailyTripId = dailyTripId;
    }

    public String getDiveShopUid() {
        return diveShopUid;
    }

    public void setDiveShopUid(String diveShopUid) {
        this.diveShopUid = diveShopUid;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public int getNumberOfDive() {
        return numberOfDive;
    }

    public void setNumberOfDive(int numberOfDive) {
        this.numberOfDive = numberOfDive;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPriceNote() {
        return priceNote;
    }

    public void setPriceNote(String priceNote) {
        this.priceNote = priceNote;
    }

    public List<DailyTripBoat> getBoats() {
        return boats;
    }

    public void setBoats(List<DailyTripBoat> boats) {
        this.boats = boats;
    }

    public List<DailyTripDiveSite> getSites() {
        return sites;
    }

    public void setSites(List<DailyTripDiveSite> sites) {
        this.sites = sites;
    }

    public List<DailyTripGuide> getGuides() {
        return guides;
    }

    public void setGuides(List<DailyTripGuide> guides) {
        this.guides = guides;
    }

    public List<DailyTripGuest> getGuests() {
        return guests;
    }

    public void setGuests(List<DailyTripGuest> guests) {
        this.guests = guests;
    }

    @Override
    public String toString() {
        return "DailyTrip{" +
                "dailyTripId=" + dailyTripId +
                ", diveShopUid=" + diveShopUid +
                ", groupSize=" + groupSize +
                ", numberOfDive=" + numberOfDive +
                ", date='" + date + '\'' +
                ", createTime='" + createTime + '\'' +
                ", price=" + price +
                ", priceNote='" + priceNote + '\'' +
                ", boats=" + boats +
                ", sites=" + sites +
                ", guides=" + guides +
                ", guests=" + guests +
                '}';
    }
}
