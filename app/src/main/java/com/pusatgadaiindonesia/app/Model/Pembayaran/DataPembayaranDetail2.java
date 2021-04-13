package com.pusatgadaiindonesia.app.Model.Pembayaran;

import com.google.gson.annotations.SerializedName;

public class DataPembayaranDetail2 {

    @SerializedName("bank")
    private String bank;

    @SerializedName("bill_key")
    private String bill_key;

    @SerializedName("biller_code")
    private String biller_code;

    @SerializedName("va_number")
    private String va_number;

    @SerializedName("no_faktur")
    private String no_faktur;

    @SerializedName("convenienceStore")
    private String convenienceStore;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("gadai")
    private String gadai;

    @SerializedName("jumlahHarusBayar")
    private String jumlahHarusBayar;

    @SerializedName("metode")
    private String metode;

    @SerializedName("paidAt")
    private String paidAt;

    @SerializedName("pembayaranId")
    private String pembayaranId;

    @SerializedName("status")
    private String status;

    public DataPembayaranDetail2(String bank, String convenienceStore, String no_faktur, String bill_key, String biller_code, String va_number,String createdAt, String gadai,
                                String jumlahHarusBayar, String metode, String paidAt, String pembayaranId, String status) {
        this.bank = bank;
        this.bill_key = bill_key;
        this.biller_code = biller_code;
        this.va_number = va_number;
        this.no_faktur = no_faktur;
        this.convenienceStore = convenienceStore;
        this.createdAt = createdAt;
        this.gadai = gadai;
        this.jumlahHarusBayar = jumlahHarusBayar;
        this.metode = metode;
        this.paidAt = paidAt;
        this.pembayaranId = pembayaranId;
        this.status = status;
    }

    public String getbank() {
        return bank;
    }

    public String getno_faktur() {
        return no_faktur;
    }

    public String getbill_key() {
        return bill_key;
    }

    public String getbiller_code() {
        return biller_code;
    }
    public String getva_number() {
        return va_number;
    }
    public String getconvenienceStore() {
        return convenienceStore;
    }

    public String getcreatedAt() {
        return createdAt;
    }

    public String getgadai() {
        return gadai;
    }

    public String getjumlahHarusBayar() {
        return jumlahHarusBayar;
    }

    public String getmetode() {
        return metode;
    }

    public String getpaidAt() {
        return paidAt;
    }

    public String getpembayaranId() {
        return pembayaranId;
    }

    public String getstatus() {
        return status;
    }

}
