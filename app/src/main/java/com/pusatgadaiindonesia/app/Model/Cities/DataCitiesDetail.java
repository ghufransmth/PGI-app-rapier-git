package com.pusatgadaiindonesia.app.Model.Cities;

import com.google.gson.annotations.SerializedName;
import com.pusatgadaiindonesia.app.Model.Province.DataProvince;

import java.util.List;

public class DataCitiesDetail {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    public DataCitiesDetail(String id, String name) {
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
