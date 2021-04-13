package com.pusatgadaiindonesia.app.Model.PerangkatBayar;

import com.google.gson.annotations.SerializedName;

public class DataPerangkat {

    @SerializedName("id")
    private String id;

    @SerializedName("header")
    private String header;

    public DataPerangkat(String id, String header) {
        this.id = id;
        this.header = header;
    }

    public String getid() {
        return id;
    }

    public String getheader() {
        return header;
    }


}
