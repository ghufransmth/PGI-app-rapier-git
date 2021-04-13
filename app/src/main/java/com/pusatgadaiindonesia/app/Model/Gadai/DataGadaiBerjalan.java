package com.pusatgadaiindonesia.app.Model.Gadai;

import com.google.gson.annotations.SerializedName;

public class DataGadaiBerjalan {
    @SerializedName("id")
    private String id;

    @SerializedName("noFaktur")
    private String noFaktur;

    @SerializedName("tipe")
    private String tipe;

    @SerializedName("merk")
    private String merk;

    @SerializedName("jenisBarang")
    private String jenisBarang;

    @SerializedName("jenisPinjaman")
    private String jenisPinjaman;

    @SerializedName("status")
    private String status;

    @SerializedName("nilaiPinjamanAwal")
    private String nilaiPinjamanAwal;

    @SerializedName("nilaiPinjamanEfektif")
    private String nilaiPinjamanEfektif;

    @SerializedName("tanggalGadai")
    private String tanggalGadai;

    @SerializedName("imeiSn")
    private String imeiSn;

    @SerializedName("tanggalJatuhTempo")
    private String tanggalJatuhTempo;

    @SerializedName("warna")
    private String warna;

    @SerializedName("tahun")
    private String tahun;

    public DataGadaiBerjalan(String id, String noFaktur, String tipe, String merk, String jenisBarang, String jenisPinjaman, String status,
                             String nilaiPinjamanAwal, String nilaiPinjamanEfektif, String tanggalGadai, String imeiSn,
                             String tanggalJatuhTempo, String warna, String tahun) {
        this.id = id;
        this.noFaktur = noFaktur;
        this.tipe = tipe;
        this.merk = merk;
        this.jenisBarang = jenisBarang;
        this.jenisPinjaman = jenisPinjaman;
        this.status = status;
        this.nilaiPinjamanAwal = nilaiPinjamanAwal;
        this.nilaiPinjamanEfektif = nilaiPinjamanEfektif;
        this.tanggalGadai = tanggalGadai;
        this.imeiSn = imeiSn;
        this.tanggalJatuhTempo = tanggalJatuhTempo;
        this.warna = warna;
        this.tahun = tahun;
    }

    public String getid() {
        return id;
    }

    public String getnoFaktur() {
        return noFaktur;
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

    public String getjenisPinjaman() {
        return jenisPinjaman;
    }

    public String getstatus() {
        return status;
    }

    public String getnilaiPinjamanAwal() {
        return nilaiPinjamanAwal;
    }

    public String getnilaiPinjamanEfektif() {
        return nilaiPinjamanEfektif;
    }

    public String gettanggalGadai() {
        return tanggalGadai;
    }

    public String getimeiSn() {
        return imeiSn;
    }

    public String gettanggalJatuhTempo() {
        return tanggalJatuhTempo;
    }

    public String getwarna() {
        return warna;
    }

    public String gettahun() {
        return tahun;
    }
}
