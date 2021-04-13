package com.pusatgadaiindonesia.app.Model.Master;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseMaster {
    @SerializedName("code")
    private String code;

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<DataMaster> data;

    public ResponseMaster(String code, String status, String message, List<DataMaster> data) {
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

    public List<DataMaster> getdata() {
        return data;
    }

}
