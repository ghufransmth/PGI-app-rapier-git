package com.pusatgadaiindonesia.app.Model.Gadai;

import com.google.gson.annotations.SerializedName;

public class DataGadaiMobile {

    @SerializedName("id")
    private String id;

    @SerializedName("tipe")
    private String tipe;

    @SerializedName("merk")
    private String merk;

    @SerializedName("jenisBarang")
    private String jenisBarang;

    @SerializedName("warnaBarang")
    private String warnaBarang;

    @SerializedName("noReferensi")
    private String noReferensi;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("tahun")
    private String tahun;

    @SerializedName("estimasiPinjaman")
    private String estimasiPinjaman;

    public DataGadaiMobile(String id,String tipe, String merk, String jenisBarang, String warnaBarang, String noReferensi,
                             String createdAt, String tahun, String estimasiPinjaman) {
        this.id = id;
        this.tipe = tipe;
        this.merk = merk;
        this.jenisBarang = jenisBarang;
        this.warnaBarang = warnaBarang;
        this.noReferensi = noReferensi;
        this.createdAt = createdAt;
        this.tahun = tahun;
        this.estimasiPinjaman = estimasiPinjaman;
    }

    public String getid() {
        return id;
    }

    public String gettipe() {
        return tipe;
    }

    public String getmerk() {
        return merk;
    }

    public String getjenisBarang() {
        return jenisBarang;
    }

    public String getwarnaBarang() {
        return warnaBarang;
    }

    public String getnoReferensi() {
        return noReferensi;
    }

    public String getcreatedAt() {
        return createdAt;
    }

    public String gettahun() {
        return tahun;
    }

    public String getestimasiPinjaman() {
        return estimasiPinjaman;
    }

}
