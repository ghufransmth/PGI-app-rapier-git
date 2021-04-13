package com.pusatgadaiindonesia.app.Model.Pembayaran;

import com.google.gson.annotations.SerializedName;

public class DataGadaiPembayaran {

    @SerializedName("id")
    private String id;

    @SerializedName("imeiSn")
    private String imeiSn;

    @SerializedName("jenisBarang")
    private String jenisBarang;

    @SerializedName("jenisPinjaman")
    private String jenisPinjaman;

    @SerializedName("merk")
    private String merk;

    @SerializedName("nilaiPinjamanAwal")
    private String nilaiPinjamanAwal;

    @SerializedName("nilaiPinjamanEfektif")
    private String nilaiPinjamanEfektif;

    @SerializedName("noFaktur")
    private String noFaktur;

    @SerializedName("status")
    private String status;

    @SerializedName("tahun")
    private String tahun;

    @SerializedName("tanggalGadai")
    private String tanggalGadai;

    @SerializedName("tanggalJatuhTempo")
    private String tanggalJatuhTempo;

    @SerializedName("tipe")
    private String tipe;

    @SerializedName("warna")
    private String warna;

    public DataGadaiPembayaran(String id, String imeiSn, String jenisBarang, String jenisPinjaman, String merk,  String nilaiPinjamanAwal, String nilaiPinjamanEfektif, String noFaktur, String status,
                               String tahun, String tanggalGadai, String tanggalJatuhTempo, String warna, String tipe) {
        this.id = id;
        this.imeiSn = imeiSn;
        this.jenisBarang = jenisBarang;
        this.jenisPinjaman = jenisPinjaman;
        this.merk = merk;
        this.nilaiPinjamanAwal = nilaiPinjamanAwal;
        this.nilaiPinjamanEfektif = nilaiPinjamanEfektif;
        this.noFaktur = noFaktur;
        this.status = status;
        this.tahun = tahun;
        this.tanggalGadai = tanggalGadai;
        this.tanggalJatuhTempo = tanggalJatuhTempo;
        this.warna = warna;
        this.tipe = tipe;
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
