package com.pusatgadaiindonesia.app.Model.Pembayaran;

import com.google.gson.annotations.SerializedName;
import com.pusatgadaiindonesia.app.Model.Gadai.DataGadaiBerjalan;

import java.util.List;

public class DataPembayaranDetail {

    @SerializedName("bank")
    private DataBank bank;

    @SerializedName("convenienceStore")
    private DataConvenienceStore convenienceStore;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("gadai")
    private DataGadaiPembayaran gadai;

    @SerializedName("jumlahHarusBayar")
    private String jumlahHarusBayar;

    @SerializedName("metode")
    private DataMetode metode;

    @SerializedName("paidAt")
    private String paidAt;

    @SerializedName("pembayaranId")
    private String pembayaranId;

    @SerializedName("status")
    private String status;

    public DataPembayaranDetail(DataBank bank, DataConvenienceStore convenienceStore, String createdAt, DataGadaiPembayaran gadai,
                                String jumlahHarusBayar, DataMetode metode, String paidAt, String pembayaranId, String status) {
        this.bank = bank;
        this.convenienceStore = convenienceStore;
        this.createdAt = createdAt;
        this.gadai = gadai;
        this.jumlahHarusBayar = jumlahHarusBayar;
        this.metode = metode;
        this.paidAt = paidAt;
        this.pembayaranId = pembayaranId;
        this.status = status;
    }

    public DataBank getbank() {
        return bank;
    }

    public DataConvenienceStore getconvenienceStore() {
        return convenienceStore;
    }

    public String getcreatedAt() {
        return createdAt;
    }

    public DataGadaiPembayaran getgadai() {
        return gadai;
    }

    public String getjumlahHarusBayar() {
        return jumlahHarusBayar;
    }

    public DataMetode getmetode() {
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
