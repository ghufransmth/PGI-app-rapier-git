package com.pusatgadaiindonesia.app.Model.Gadai;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataGadai2 {

    @SerializedName("data")
    private List<DataGadaiLunasLelang> data;

    @SerializedName("totalElements")
    private String totalElements;

    @SerializedName("totalPages")
    private String totalPages;

    public DataGadai2(List<DataGadaiLunasLelang> data, String totalElements, String totalPages) {
        this.data = data;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public List<DataGadaiLunasLelang> getdata() {
        return data;
    }

    public String gettotalElements() {
        return totalElements;
    }

    public String gettotalPages() {
        return totalPages;
    }

}
