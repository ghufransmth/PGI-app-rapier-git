package com.pusatgadaiindonesia.app.Model.Complain;

import com.google.gson.annotations.SerializedName;

public class SendComplain {
    @SerializedName("no_anggota")
    private String no_anggota;

    @SerializedName("nama_nasabah")
    private String nama_nasabah;

    @SerializedName("no_telp")
    private String no_telp;

    @SerializedName("kategori")
    private String kategori;

    @SerializedName("keterangan")
    private String keterangan;

    public SendComplain(String no_anggota, String nama_nasabah, String no_telp, String kategori, String keterangan) {
        this.no_anggota = no_anggota;
        this.nama_nasabah = nama_nasabah;
        this.no_telp = no_telp;
        this.kategori = kategori;
        this.keterangan = keterangan;
    }
}
