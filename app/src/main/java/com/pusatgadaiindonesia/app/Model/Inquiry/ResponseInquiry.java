package com.pusatgadaiindonesia.app.Model.Inquiry;

import com.google.gson.annotations.SerializedName;
import com.pusatgadaiindonesia.app.Model.Gadai.DataGadai2;

public class ResponseInquiry {

    @SerializedName("status")
    private String status;

    @SerializedName("reason")
    private String reason;

    @SerializedName("data")
    private DataInquiry data;

    public ResponseInquiry(String status, String reason, DataInquiry data) {
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

    public DataInquiry getdata() {
        return data;
    }

}
