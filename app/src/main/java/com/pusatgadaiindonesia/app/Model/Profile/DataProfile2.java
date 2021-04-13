package com.pusatgadaiindonesia.app.Model.Profile;

import com.google.gson.annotations.SerializedName;

public class DataProfile2 {

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("id")
    private String id;

    @SerializedName("jenisIdentitas")
    private String jenisIdentitas;

    @SerializedName("nama")
    private String nama;

    @SerializedName("nik")
    private String nik;

    @SerializedName("no")
    private String no;

    @SerializedName("noHandphone")
    private String noHandphone;

    public DataProfile2(String alamat, String id, String jenisIdentitas, String nama, String nik, String no, String noHandphone) {
        this.alamat = alamat;
        this.id = id;
        this.jenisIdentitas = jenisIdentitas;
        this.nama = nama;
        this.nik = nik;
        this.no = no;
        this.noHandphone = noHandphone;
    }

    public String getalamat() {
        return alamat;
    }

    public String getid() {
        return id;
    }

    public String getjenisIdentitas() {
        return jenisIdentitas;
    }

    public String getnama() {
        return nama;
    }

    public String getnik() {
        return nik;
    }

    public String getno() {
        return no;
    }

    public String getnoHandphone() {
        return noHandphone;
    }

}
