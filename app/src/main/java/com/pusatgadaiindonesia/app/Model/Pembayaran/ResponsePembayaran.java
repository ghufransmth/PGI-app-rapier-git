package com.pusatgadaiindonesia.app.Model.Pembayaran;

import com.google.gson.annotations.SerializedName;
import com.pusatgadaiindonesia.app.Model.Gadai.DataGadaiMobile;

import java.util.List;

public class ResponsePembayaran {

    @SerializedName("status")
    private String status;

    @SerializedName("reason")
    private String reason;

    @SerializedName("data")
    private DataPembayaran data;

    public ResponsePembayaran(String status, String reason, DataPembayaran data) {
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

    public DataPembayaran getdata() {
        return data;
    }

}
