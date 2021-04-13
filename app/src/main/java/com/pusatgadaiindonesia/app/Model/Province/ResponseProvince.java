package com.pusatgadaiindonesia.app.Model.Province;

import com.google.gson.annotations.SerializedName;
import com.pusatgadaiindonesia.app.Model.Profile.DataProfile;

import java.util.List;

public class ResponseProvince {
    @SerializedName("code")
    private String code;

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<DataProvince> data;

    public ResponseProvince(String code, String status, String message, List<DataProvince> data) {
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

    public List<DataProvince> getdata() {
        return data;
    }

}
