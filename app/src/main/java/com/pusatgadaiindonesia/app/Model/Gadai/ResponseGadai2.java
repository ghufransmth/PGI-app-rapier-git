package com.pusatgadaiindonesia.app.Model.Gadai;

import com.google.gson.annotations.SerializedName;

public class ResponseGadai2 {

    @SerializedName("status")
    private String status;

    @SerializedName("reason")
    private String reason;

    @SerializedName("data")
    private DataGadai2 data;

    public ResponseGadai2(String status, String reason, DataGadai2 data) {
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

    public DataGadai2 getdata() {
        return data;
    }

}
