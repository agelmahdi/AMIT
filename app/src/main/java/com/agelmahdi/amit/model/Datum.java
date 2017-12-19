
package com.agelmahdi.amit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("address")
    @Expose
    private String mAddress;
    @SerializedName("id")
    @Expose
    private String mId;
    @SerializedName("langtitude")
    @Expose
    private String mLangtitude;
    @SerializedName("latitude")
    @Expose
    private String mLatitude;
    @SerializedName("userFK")
    @Expose
    private String mUserFK;
    @SerializedName("userNumber")
    @Expose
    private String mUserNumber;

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getLangtitude() {
        return mLangtitude;
    }

    public void setLangtitude(String langtitude) {
        mLangtitude = langtitude;
    }

    public String getLatitude() {
        return mLatitude;
    }

    public void setLatitude(String latitude) {
        mLatitude = latitude;
    }

    public String getUserFK() {
        return mUserFK;
    }

    public void setUserFK(String userFK) {
        mUserFK = userFK;
    }

    public String getUserNumber() {
        return mUserNumber;
    }

    public void setUserNumber(String userNumber) {
        mUserNumber = userNumber;
    }

}
