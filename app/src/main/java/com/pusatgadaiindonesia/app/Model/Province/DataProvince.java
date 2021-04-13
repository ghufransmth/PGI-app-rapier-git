package com.pusatgadaiindonesia.app.Model.Province;

import com.google.gson.annotations.SerializedName;

public class DataProvince {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    public DataProvince(String id, String name) {
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
