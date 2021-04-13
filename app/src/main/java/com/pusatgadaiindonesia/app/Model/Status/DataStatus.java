package com.pusatgadaiindonesia.app.Model.Status;

import com.google.gson.annotations.SerializedName;

public class DataStatus {

    @SerializedName("id")
    private String id;

    @SerializedName("nama")
    private String nama;

    public DataStatus(String id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public String getid() {
        return id;
    }

    public String getnama() {
        return nama;
    }

}
