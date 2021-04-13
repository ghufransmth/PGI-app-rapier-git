package com.pusatgadaiindonesia.app.Model.Inquiry;

import com.google.gson.annotations.SerializedName;

public class DataJasaDetail {

    @SerializedName("name")
    private String name;

    @SerializedName("nominal")
    private String nominal;

    @SerializedName("jenis")
    private String jenis;

    @SerializedName("startDate")
    private String startDate;

    @SerializedName("endDate")
    private String endDate;

    public DataJasaDetail(String name, String nominal, String jenis, String startDate, String endDate) {
        this.name = name;
        this.nominal = nominal;
        this.jenis = jenis;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getname() {
        return name;
    }

    public String getnominal() {
        return nominal;
    }

    public String getjenis() {
        return jenis;
    }

    public String getstartDate() {
        return startDate;
    }

    public String getendDate() {
        return endDate;
    }

}
