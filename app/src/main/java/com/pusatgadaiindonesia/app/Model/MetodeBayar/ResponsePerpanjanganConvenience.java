package com.pusatgadaiindonesia.app.Model.MetodeBayar;

import com.google.gson.annotations.SerializedName;

public class ResponsePerpanjanganConvenience {

    @SerializedName("status")
    private String status;

    @SerializedName("reason")
    private String reason;

    @SerializedName("data")
    private DataPerpanjanganConvenience data;

    public ResponsePerpanjanganConvenience(String status, String reason, DataPerpanjanganConvenience data) {
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

    public DataPerpanjanganConvenience getdata() {
        return data;
    }

}
