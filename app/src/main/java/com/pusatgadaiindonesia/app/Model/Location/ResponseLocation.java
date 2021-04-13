package com.pusatgadaiindonesia.app.Model.Location;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseLocation {
    @SerializedName("code")
    private String code;

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private ResponseData data;

    public ResponseLocation(String code, String status, String message, ResponseData data) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getcode() {
        return code;
    }

    public String getstatus() {
        return status;
    }

    public String getmessage() {
        return message;
    }

    public ResponseData getdata() {
        return data;
    }

}
