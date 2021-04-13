package com.pusatgadaiindonesia.app.Model.MetodeBayar;

import com.google.gson.annotations.SerializedName;

public class DataPerpanjanganConvenience {

    @SerializedName("name")
    private String name;

    @SerializedName("payment_code")
    private String payment_code;

    @SerializedName("pembayaranId")
    private String pembayaranId;

    @SerializedName("product_code")
    private String product_code;

    public DataPerpanjanganConvenience(String name, String payment_code, String pembayaranId, String product_code) {
        this.name = name;
        this.payment_code = payment_code;
        this.pembayaranId = pembayaranId;
        this.product_code = product_code;
    }

    public String getname() {
        return name;
    }

    public String getpayment_code() {
        return payment_code;
    }

    public String getpembayaranId() {
        return pembayaranId;
    }

    public String getproduct_code() {
        return product_code;
    }

}
