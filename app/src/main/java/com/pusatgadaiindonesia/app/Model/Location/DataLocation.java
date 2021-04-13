package com.pusatgadaiindonesia.app.Model.Location;

import com.google.gson.annotations.SerializedName;

public class DataLocation {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("longitude")
    private String longitude;

    @SerializedName("address")
    private String address;

    @SerializedName("province")
    private String province;

    @SerializedName("city")
    private String city;

    public DataLocation(String id, String name, String latitude, String longitude, String address, String province, String city) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.province = province;
        this.city = city;
    }

    public String getid() {
        return id;
    }

    public String getname() {
        return name;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

}
