package com.pusatgadaiindonesia.app.Model.Pembayaran;

import com.google.gson.annotations.SerializedName;
import com.pusatgadaiindonesia.app.Model.Gadai.DataGadaiBerjalan;

import java.util.List;

public class DataPembayaran {

    @SerializedName("data")
    private List<DataPembayaranDetail> data;

    @SerializedName("totalElements")
    private String totalElements;

    @SerializedName("totalPages")
    private String totalPages;

    public DataPembayaran(List<DataPembayaranDetail> data, String totalElements, String totalPages) {
        this.data = data;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public List<DataPembayaranDetail> getdata() {
        return data;
    }

    public String gettotalElements() {
        return totalElements;
    }

    public String gettotalPages() {
        return totalPages;
    }


}
