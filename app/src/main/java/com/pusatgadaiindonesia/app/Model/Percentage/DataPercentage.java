package com.pusatgadaiindonesia.app.Model.Percentage;

import com.google.gson.annotations.SerializedName;

public class DataPercentage {
    @SerializedName("fullset")
    private String fullset;

    @SerializedName("batangan")
    private String batangan;

    public DataPercentage(String fullset, String batangan) {
        this.fullset = fullset;
        this.batangan = batangan;
    }

    public String getfullset() {
        return fullset;
    }

    public String getbatangan() {
        return batangan;
    }
}
