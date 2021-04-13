package com.pusatgadaiindonesia.app.Model.Barang;

import com.google.gson.annotations.SerializedName;
import com.pusatgadaiindonesia.app.Model.Banner.DataBannerDetail2;

import java.util.List;

public class ResponseBarang {

    @SerializedName("status")
    private String status;

    @SerializedName("reason")
    private String reason;

    @SerializedName("data")
    List<DataBarang> data;
    //private DataBanner data;

    public ResponseBarang(String status, String message, List<DataBarang> data) {
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

    public List<DataBarang> getdata() {
        return data;
    }

}
