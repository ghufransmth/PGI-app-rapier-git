package com.pusatgadaiindonesia.app.Model.CreditNominal;

import com.google.gson.annotations.SerializedName;
import com.pusatgadaiindonesia.app.Model.Percentage.DataPercentage;

public class ResponseCreditNominal {
    @SerializedName("code")
    private String code;

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private DataCreditNominal data;

    public ResponseCreditNominal(String code, String status, String message, DataCreditNominal data) {
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

    public DataCreditNominal getData() {
        return data;
    }

}
