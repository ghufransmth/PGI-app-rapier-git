package com.pusatgadaiindonesia.app.Model.Profile;

import com.google.gson.annotations.SerializedName;

public class DataProfile {
    @SerializedName("id")
    private String id;

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

    @SerializedName("placeOfBirth")
    private String placeOfBirth;

    @SerializedName("dateOfBirth")
    private String dateOfBirth;

    @SerializedName("gender")
    private String gender;

    @SerializedName("address")
    private String address;

    @SerializedName("province")
    private String province;

    @SerializedName("city")
    private String city;

    @SerializedName("profilePicture")
    private String profilePicture;

    @SerializedName("dateOfBirth2")
    private String dateOfBirth2;

    @SerializedName("provinceId")
    private String provinceId;

    @SerializedName("cityId")
    private String cityId;

    public DataProfile(String id, String name, String email, String phone, String identityType, String identityNumber, String placeOfBirth, String dateOfBirth,
                       String gender, String address, String province, String city, String profilePicture, String dateOfBirth2, String provinceId, String cityId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.identityType = identityType;
        this.identityNumber = identityNumber;
        this.placeOfBirth = placeOfBirth;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.province = province;
        this.city = city;
        this.profilePicture = profilePicture;
        this.dateOfBirth2 = dateOfBirth2;
        this.provinceId = provinceId;
        this.cityId = cityId;
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

    public String getidentityType() {
        return identityType;
    }

    public String getidentityNumber() {
        return identityNumber;
    }

    public String getplaceOfBirth() {
        return placeOfBirth;
    }

    public String getdateOfBirth() {
        return dateOfBirth;
    }

    public String getgender() {
        return gender;
    }

    public String getaddress() {
        return address;
    }

    public String getprovince() {
        return province;
    }

    public String getcity() {
        return city;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getDateOfBirth2() {
        return dateOfBirth2;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public String getCityId() {
        return cityId;
    }
}
