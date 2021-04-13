package com.pusatgadaiindonesia.app.Model.Master;

import com.google.gson.annotations.SerializedName;

public class DataMaster {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("maxLoanPrice")
    private String maxLoanPrice;

    public DataMaster(String id, String name, String maxLoanPrice) {
        this.id = id;
        this.name = name;
        this.maxLoanPrice = maxLoanPrice;
    }

    public String getid() {
        return id;
    }

    public String getname() {
        return name;
    }

    public String getMaxLoanPrice() {
        return maxLoanPrice;
    }

}
