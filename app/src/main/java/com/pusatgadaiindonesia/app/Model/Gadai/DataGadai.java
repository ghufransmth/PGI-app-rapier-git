package com.pusatgadaiindonesia.app.Model.Gadai;

import com.google.gson.annotations.SerializedName;
import com.pusatgadaiindonesia.app.Model.Banner.DataBanner2;
import com.pusatgadaiindonesia.app.Model.Banner.DataBannerDetail;

import java.util.List;

public class DataGadai {
    @SerializedName("data")
    private List<DataGadaiBerjalan> data;

    @SerializedName("totalElements")
    private String totalElements;

    @SerializedName("totalPages")
    private String totalPages;

    public DataGadai(List<DataGadaiBerjalan> data, String totalElements, String totalPages) {
        this.data = data;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public List<DataGadaiBerjalan> getdata() {
        return data;
    }

    public String gettotalElements() {
        return totalElements;
    }

    public String gettotalPages() {
        return totalPages;
    }
}
