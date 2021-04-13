package com.pusatgadaiindonesia.app.Model.MetodeBayar;

import com.google.gson.annotations.SerializedName;

public class DataMetodeBayar {

    @SerializedName("id")
    private String id;

    @SerializedName("logo")
    private String logo;

    @SerializedName("name")
    private String name;

    @SerializedName("type")
    private String type;

    public DataMetodeBayar(String id, String logo, String name, String type) {
        this.id = id;
        this.logo = logo;
        this.name = name;
        this.type = type;
    }

    public String getid() {
        return id;
    }

    public String getlogo() {
        return logo;
    }

    public String getname() {
        return name;
    }

    public String gettype() {
        return type;
    }

}
