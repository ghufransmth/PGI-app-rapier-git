package com.pusatgadaiindonesia.app.Model.News;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataNews {
    @SerializedName("data")
    private List<DataNewsDetail> data;


    public DataNews(List<DataNewsDetail> data) {
        this.data = data;
    }

    public List<DataNewsDetail> getdata() {
        return data;
    }
}
