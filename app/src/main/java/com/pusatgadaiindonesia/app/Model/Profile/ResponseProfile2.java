package com.pusatgadaiindonesia.app.Model.Profile;

import com.google.gson.annotations.SerializedName;
import com.pusatgadaiindonesia.app.Model.Register.DataRegister;

public class ResponseProfile2 {

    @SerializedName("status")
    private String status;

    @SerializedName("reason")
    private String reason;

    @SerializedName("data")
    private DataProfile2 data;

    public ResponseProfile2(String status, String reason, DataProfile2 data) {
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

    public DataProfile2 getdata() {
        return data;
    }

}
