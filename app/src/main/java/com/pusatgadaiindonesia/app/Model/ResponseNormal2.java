package com.pusatgadaiindonesia.app.Model;

import com.google.gson.annotations.SerializedName;

public class ResponseNormal2 {

    @SerializedName("data")
    private String data;

    @SerializedName("status")
    private String status;

    @SerializedName("reason")
    private String reason;

    public ResponseNormal2(String data, String status, String reason) {
        this.data = data;
        this.status = status;
        this.reason = reason;
    }

    public String getdata() {
        return data;
    }

    public String getstatus() {
        return status;
    }

    public String getreason() {
        return reason;
    }

}
