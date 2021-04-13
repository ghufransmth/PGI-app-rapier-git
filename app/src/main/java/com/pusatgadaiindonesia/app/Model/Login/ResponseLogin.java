package com.pusatgadaiindonesia.app.Model.Login;

import com.google.gson.annotations.SerializedName;

public class ResponseLogin {

    @SerializedName("status")
    private String status;

    @SerializedName("reason")
    private String reason;

    @SerializedName("data")
    private DataLogin data;

    public ResponseLogin(String status, String reason, DataLogin data) {
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

    public DataLogin getdata() {
        return data;
    }

}
