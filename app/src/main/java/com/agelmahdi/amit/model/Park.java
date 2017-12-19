
package com.agelmahdi.amit.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Park {

    @SerializedName("data")
    @Expose
    private List<Datum> mData;
    @SerializedName("result")
    @Expose
    private Long mResult;

    public List<Datum> getData() {
        return mData;
    }

    public void setData(List<Datum> data) {
        mData = data;
    }

    public Long getResult() {
        return mResult;
    }

    public void setResult(Long result) {
        mResult = result;
    }

}
