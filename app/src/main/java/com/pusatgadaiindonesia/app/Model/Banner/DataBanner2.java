package com.pusatgadaiindonesia.app.Model.Banner;

import com.google.gson.annotations.SerializedName;
import com.pusatgadaiindonesia.app.Model.News.DataNewsDetail;

import java.util.List;

public class DataBanner2 {

    @SerializedName("data")
    private List<DataBannerDetail> data;


    public DataBanner2(List<DataBannerDetail> data) {
        this.data = data;
    }

    public List<DataBannerDetail> getdata() {
        return data;
    }
}
