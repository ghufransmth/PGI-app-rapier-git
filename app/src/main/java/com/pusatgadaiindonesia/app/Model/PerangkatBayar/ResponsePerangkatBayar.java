package com.pusatgadaiindonesia.app.Model.PerangkatBayar;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponsePerangkatBayar {

    @SerializedName("data")
    private List<DataPerangkat> data;

    @SerializedName("status")
    private String status;

    @SerializedName("reason")
    private String reason;

    public ResponsePerangkatBayar(List<DataPerangkat> data, String status, String reason) {
        this.data = data;
        this.status = status;
        this.reason = reason;
    }

    public List<DataPerangkat> getdata() {
        return data;
    }

    public String getstatus() {
        return status;
    }

    public String getreason() {
        return reason;
    }

}
