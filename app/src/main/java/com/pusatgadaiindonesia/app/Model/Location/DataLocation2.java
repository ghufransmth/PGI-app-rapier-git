package com.pusatgadaiindonesia.app.Model.Location;

import com.google.gson.annotations.SerializedName;

public class DataLocation2 {
    @SerializedName("name")
    private String name;

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("longitude")
    private String longitude;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("provinsi")
    private String provinsi;

    @SerializedName("kota")
    private String kota;

    public DataLocation2(String name, String latitude, String longitude, String alamat, String provinsi, String kota) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.alamat = alamat;
        this.provinsi = provinsi;
        this.kota = kota;
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
        return alamat;
    }

    public String getProvince() {
        return provinsi;
    }

    public String getCity() {
        return kota;
    }
}
