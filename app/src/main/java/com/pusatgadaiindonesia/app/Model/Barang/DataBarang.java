package com.pusatgadaiindonesia.app.Model.Barang;

import com.google.gson.annotations.SerializedName;
import com.pusatgadaiindonesia.app.Model.Banner.DataBanner2;

public class DataBarang {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    public DataBarang(String id, String name) {
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
