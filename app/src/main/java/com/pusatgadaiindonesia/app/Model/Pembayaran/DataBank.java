package com.pusatgadaiindonesia.app.Model.Pembayaran;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataBank {

    @SerializedName("bank")
    private String bank;

    @SerializedName("bill_key")
    private String bill_key;

    @SerializedName("biller_code")
    private String biller_code;

    @SerializedName("pembayaranId")
    private String pembayaranId;

    @SerializedName("va_number")
    private String va_number;

    public DataBank(String bank, String bill_key, String biller_code, String pembayaranId, String va_number) {
        this.bank = bank;
        this.bill_key = bill_key;
        this.biller_code = biller_code;
        this.pembayaranId = pembayaranId;
        this.va_number = va_number;
    }

    public String getbank() {
        return bank;
    }

    public String getbill_key() {
        return bill_key;
    }

    public String getbiller_code() {
        return biller_code;
    }

    public String getpembayaranId() {
        return pembayaranId;
    }

    public String getva_number() {
        return va_number;
    }

}
