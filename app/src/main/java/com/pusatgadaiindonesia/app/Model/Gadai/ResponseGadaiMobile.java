package com.pusatgadaiindonesia.app.Model.Gadai;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseGadaiMobile {

    @SerializedName("status")
    private String status;

    @SerializedName("reason")
    private String reason;

    @SerializedName("data")
    private List<DataGadaiMobile> data;

    public ResponseGadaiMobile(String status, String reason, List<DataGadaiMobile> data) {
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

    public List<DataGadaiMobile> getdata() {
        return data;
    }

}
