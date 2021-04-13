package com.pusatgadaiindonesia.app.Model.News;

import com.google.gson.annotations.SerializedName;

public class ResponseNews {
    @SerializedName("code")
    private String code;

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private DataNews data;

    public ResponseNews(String code, String status, String message, DataNews data) {
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

    public DataNews getdata() {
        return data;
    }

}
