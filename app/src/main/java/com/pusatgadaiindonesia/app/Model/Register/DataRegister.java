package com.pusatgadaiindonesia.app.Model.Register;

import com.google.gson.annotations.SerializedName;

public class DataRegister {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("phone")
    private String phone;

    public DataRegister(String id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getid() {
        return id;
    }

    public String getname() {
        return name;
    }

    public String getemail() {
        return email;
    }

    public String getphone() {
        return phone;
    }

}
