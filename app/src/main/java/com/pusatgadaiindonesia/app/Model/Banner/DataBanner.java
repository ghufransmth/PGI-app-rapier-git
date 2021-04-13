package com.pusatgadaiindonesia.app.Model.Banner;

import com.google.gson.annotations.SerializedName;
import com.pusatgadaiindonesia.app.Model.News.DataNewsDetail;

import java.util.List;

public class DataBanner {

    @SerializedName("banner")
    private DataBanner2 banner;

    public DataBanner(DataBanner2 banner) {
        this.banner = banner;
    }

    public DataBanner2 getdata() {
        return banner;
    }

}
