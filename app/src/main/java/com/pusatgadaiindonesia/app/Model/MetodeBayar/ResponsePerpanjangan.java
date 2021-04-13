package com.pusatgadaiindonesia.app.Model.MetodeBayar;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponsePerpanjangan {

    @SerializedName("status")
    private String status;

    @SerializedName("reason")
    private String reason;

    @SerializedName("data")
    private DataPerpanjangan data;

    public ResponsePerpanjangan(String status, String reason, DataPerpanjangan data) {
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

    public DataPerpanjangan getdata() {
        return data;
    }

}
