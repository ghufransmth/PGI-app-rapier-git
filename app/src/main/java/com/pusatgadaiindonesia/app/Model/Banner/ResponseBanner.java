package com.pusatgadaiindonesia.app.Model.Banner;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseBanner {

    @SerializedName("status")
    private String status;

    @SerializedName("reason")
    private String reason;

    @SerializedName("data")
    List<DataBannerDetail2> data;
    //private DataBanner data;

    public ResponseBanner(String status, String message, List<DataBannerDetail2> data) {
        this.status = status;
        this.reason = reason;
        this.data = data;
    }

    public String getstatus() {
        return status;
    }

    public String getreason() {
        return reason;
    }

    public List<DataBannerDetail2> getdata() {
        return data;
    }

}
