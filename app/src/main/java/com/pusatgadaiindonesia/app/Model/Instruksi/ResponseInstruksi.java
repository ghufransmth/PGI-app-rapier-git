package com.pusatgadaiindonesia.app.Model.Instruksi;

import com.google.gson.annotations.SerializedName;
import com.pusatgadaiindonesia.app.Model.Inquiry.DataInquiry;

public class ResponseInstruksi {

    @SerializedName("status")
    private String status;

    @SerializedName("reason")
    private String reason;

    @SerializedName("data")
    private DataInstruksi data;

    public ResponseInstruksi(String status, String reason, DataInstruksi data) {
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

    public DataInstruksi getdata() {
        return data;
    }

}
