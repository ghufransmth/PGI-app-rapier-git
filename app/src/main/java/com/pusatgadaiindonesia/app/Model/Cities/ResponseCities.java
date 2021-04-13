package com.pusatgadaiindonesia.app.Model.Cities;

import com.google.gson.annotations.SerializedName;
import com.pusatgadaiindonesia.app.Model.Province.DataProvince;

import java.util.List;

public class ResponseCities {
    @SerializedName("code")
    private String code;

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private DataCities data;

    public ResponseCities(String code, String status, String message, DataCities data) {
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

    public DataCities getdata() {
        return data;
    }

}
