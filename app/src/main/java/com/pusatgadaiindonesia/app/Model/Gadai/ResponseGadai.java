package com.pusatgadaiindonesia.app.Model.Gadai;

import com.google.gson.annotations.SerializedName;
import com.pusatgadaiindonesia.app.Model.Register.DataRegister;

public class ResponseGadai {

    @SerializedName("status")
    private String status;

    @SerializedName("reason")
    private String reason;

    @SerializedName("data")
    private DataGadai data;

    public ResponseGadai(String status, String reason, DataGadai data) {
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

    public DataGadai getdata() {
        return data;
    }
}
