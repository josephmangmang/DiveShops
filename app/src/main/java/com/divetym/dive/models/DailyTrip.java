package com.divetym.dive.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.divetym.dive.BuildConfig;
import com.divetym.dive.rest.constants.ApiConstant;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by kali_root on 3/27/2017.
 */

public class DailyTrip implements Parcelable {
    private static final String FORMAT_DATE_SERVER = "yyyy-mm-dd HH:mm:ss";
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

    @SerializedName(ApiConstant.DAILY_TRIP_BOATS)
    private List<DailyTripBoat> boats;
    @SerializedName(ApiConstant.DAILY_TRIP_DIVE_SITES)
    private List<DailyTripDiveSite> sites;
    @SerializedName(ApiConstant.DAILY_TRIP_GUIDES)
    private List<DailyTripGuide> guides;
    @SerializedName(ApiConstant.DAILY_TRIP_GUESTS)
    private List<DailyTripGuest> guests;

    private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMMM dd, yyyy");
    private SimpleDateFormat mTimeFormat = new SimpleDateFormat("H:m");
    private Date mDate;
    private List<ListPreview> mBoatPreviews;

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
        try {
            mDate = new SimpleDateFormat(FORMAT_DATE_SERVER).parse(this.date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
        try {
            mDate = new SimpleDateFormat(FORMAT_DATE_SERVER).parse(this.date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
        try {
            mDate = new SimpleDateFormat(FORMAT_DATE_SERVER).parse(this.date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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

    public void setDiveSites(List<DailyTripDiveSite> sites) {
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

    public String getDiveSites() {
        StringBuilder sitesBuilder = new StringBuilder();
        if (sites == null) return "";

        for (DailyTripDiveSite site : sites) {
            sitesBuilder.append(site.getName());
            sitesBuilder.append(", ");
        }
        int length = sitesBuilder.length();
        if (length > 2) {
            sitesBuilder = sitesBuilder.delete(length - 3, length - 1);
        }
        return sitesBuilder.toString();
    }

    public String getDateOnly() {// 2017-03-23
        if (mDate == null) {
            try {
                mDate = new SimpleDateFormat(FORMAT_DATE_SERVER).parse(this.date);
            } catch (ParseException e) {
                e.printStackTrace();
                return "Invalid Date";
            }
        }
        return mDateFormat.format(mDate).toString();

    }

    public String getTimeOnly() {
        if (mDate == null) {
            try {
                mDate = new SimpleDateFormat(FORMAT_DATE_SERVER).parse(this.date);
            } catch (ParseException e) {
                e.printStackTrace();
                return "Invalid Date";
            }
        }
        return mTimeFormat.format(mDate).toLowerCase();
    }

    public String getGuestNames() {
        StringBuilder stringBuilder = new StringBuilder();
        if (guests != null) {
            for (int i = 0; i < guests.size(); i++) {
                stringBuilder.append(i + 1 + ". " + guests.get(i).getName());
            }
        }
        return stringBuilder.toString();
    }

    public String getGuideNames() {
        StringBuilder stringBuilder = new StringBuilder();
        if (guides != null) {
            for (int i = 0; i < guides.size(); i++) {
                stringBuilder.append(i + 1 + ". " + guides.get(i).getGuideName());
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    public int getRemainingSlot() {
        int totalGuest = guests != null ? guests.size() : 0;
        int remainintSlot = groupSize - totalGuest;
        return remainintSlot;
    }

    public List<ListPreview> getBoatPreviews() {
        if (boats != null && boats.size() > 0) {
            mBoatPreviews = new ArrayList<>();
            for (int i = 0; i < boats.size(); i++) {
                DailyTripBoat boat = boats.get(i);
                ListPreview listPreview = new ListPreview(i, boat.getName(), "VIEW", boat.getImageUrl());
                mBoatPreviews.add(listPreview);
            }
        }else{
            mBoatPreviews = new ArrayList<>();
        }
        return mBoatPreviews;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.dailyTripId);
        dest.writeString(this.diveShopUid);
        dest.writeInt(this.groupSize);
        dest.writeInt(this.numberOfDive);
        dest.writeString(this.date);
        dest.writeString(this.createTime);
        dest.writeSerializable(this.price);
        dest.writeString(this.priceNote);
        dest.writeTypedList(this.boats);
        dest.writeList(this.sites);
        dest.writeList(this.guides);
        dest.writeList(this.guests);
    }

    protected DailyTrip(Parcel in) {
        this.dailyTripId = in.readInt();
        this.diveShopUid = in.readString();
        this.groupSize = in.readInt();
        this.numberOfDive = in.readInt();
        this.date = in.readString();
        this.createTime = in.readString();
        this.price = (BigDecimal) in.readSerializable();
        this.priceNote = in.readString();
        this.boats = in.createTypedArrayList(DailyTripBoat.CREATOR);
        this.sites = new ArrayList<DailyTripDiveSite>();
        in.readList(this.sites, DailyTripDiveSite.class.getClassLoader());
        this.guides = new ArrayList<DailyTripGuide>();
        in.readList(this.guides, DailyTripGuide.class.getClassLoader());
        this.guests = new ArrayList<DailyTripGuest>();
        in.readList(this.guests, DailyTripGuest.class.getClassLoader());
    }

    public static final Parcelable.Creator<DailyTrip> CREATOR = new Parcelable.Creator<DailyTrip>() {
        @Override
        public DailyTrip createFromParcel(Parcel source) {
            return new DailyTrip(source);
        }

        @Override
        public DailyTrip[] newArray(int size) {
            return new DailyTrip[size];
        }
    };

}
