package com.pusatgadaiindonesia.app.Model.MetodeBayar;

import com.google.gson.annotations.SerializedName;
import com.pusatgadaiindonesia.app.Model.Inquiry.DataInquiry;

import java.util.List;

public class ResponseMetodeBayar {

    @SerializedName("status")
    private String status;

    @SerializedName("reason")
    private String reason;

    @SerializedName("data")
    private List<DataMetodeBayar> data;

    public ResponseMetodeBayar(String status, String reason, List<DataMetodeBayar> data) {
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

    public List<DataMetodeBayar> getdata() {
        return data;
    }

}
