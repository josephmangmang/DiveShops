package com.divetym.dive.models.response;

import com.divetym.dive.rest.constants.ApiConstant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kali_root on 5/18/2017.
 */

public class GuideResponse extends Response {
    @SerializedName(ApiConstant.GUIDE_ID)
    private int guideId;
    @SerializedName(ApiConstant.DIVE_SHOP_ID)
    private String shopUid;
    @SerializedName(ApiConstant.NAME)
    private String name;
    @SerializedName(ApiConstant.IMAGE)
    private String image;

    public int getGuideId() {
        return guideId;
    }

    public void setGuideId(int guideId) {
        this.guideId = guideId;
    }

    public String getShopUid() {
        return shopUid;
    }

    public void setShopUid(String shopUid) {
        this.shopUid = shopUid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
