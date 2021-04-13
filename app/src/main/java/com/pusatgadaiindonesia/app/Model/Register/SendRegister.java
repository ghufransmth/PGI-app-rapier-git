package com.pusatgadaiindonesia.app.Model.Register;

import com.google.gson.annotations.SerializedName;

public class SendRegister {
    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("phone")
    private String phone;

    @SerializedName("identityType")
    private String identityType;

    @SerializedName("identityNumber")
    private String identityNumber;

    @SerializedName("pob")
    private String pob;

    @SerializedName("dob")
    private String dob;

    @SerializedName("gender")
    private String gender;

    @SerializedName("address")
    private String address;

    @SerializedName("provinceId")
    private String provinceId;

    @SerializedName("cityId")
    private String cityId;

    @SerializedName("password")
    private String password;

    @SerializedName("cpassword")
    private String cpassword;


    public SendRegister(String name, String email, String identityType, String phone, String identityNumber, String pob, String dob,
                        String gender, String address, String provinceId, String cityId, String password, String cpassword ) {
        this.name = name;
        this.email = email;
        this.identityType = identityType;
        this.phone = phone;
        this.identityNumber = identityNumber;
        this.pob = pob;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.provinceId = provinceId;
        this.cityId = cityId;
        this.password = password;
        this.cpassword = cpassword;
    }

}
