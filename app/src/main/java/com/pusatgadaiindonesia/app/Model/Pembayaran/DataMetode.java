package com.pusatgadaiindonesia.app.Model.Pembayaran;

import com.google.gson.annotations.SerializedName;

public class DataMetode {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    public DataMetode(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getid() {
        return id;
    }

    public String getname() {
        return name;
    }

}
