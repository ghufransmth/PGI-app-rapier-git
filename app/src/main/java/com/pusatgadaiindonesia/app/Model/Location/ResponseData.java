package com.pusatgadaiindonesia.app.Model.Location;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseData {
    @SerializedName("data")
    private List<DataLocation> data;

    public ResponseData(List<DataLocation> data) {
        this.data = data;
    }
    public List<DataLocation> getdata() {
        return data;
    }

}
