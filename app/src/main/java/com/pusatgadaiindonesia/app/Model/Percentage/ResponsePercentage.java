package com.pusatgadaiindonesia.app.Model.Percentage;

import com.google.gson.annotations.SerializedName;

public class ResponsePercentage {
    @SerializedName("code")
    private String code;

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private DataPercentage data;

    public ResponsePercentage(String code, String status, String message, DataPercentage data) {
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

    public DataPercentage getData() {
        return data;
    }

}
