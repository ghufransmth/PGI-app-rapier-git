package com.pusatgadaiindonesia.app.Model.Cities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataCities {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("cities")
    private List<DataCitiesDetail> cities;

    public DataCities(String id, String name, List<DataCitiesDetail> cities) {
        this.id = id;
        this.name = name;
        this.cities = cities;
    }

    public String getid() {
        return id;
    }

    public String getname() {
        return name;
    }

    public List<DataCitiesDetail> getcities() {
        return cities;
    }

}
