package com.pusatgadaiindonesia.app.Model.Profile;

import com.google.gson.annotations.SerializedName;
import com.pusatgadaiindonesia.app.Model.Register.DataRegister;

public class ResponseProfile {

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private DataProfile data;

    public ResponseProfile(String status, String message, DataProfile data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getstatus() {
        return status;
    }

    public String getmessage() {
        return message;
    }

    public DataProfile getdata() {
        return data;
    }

}
