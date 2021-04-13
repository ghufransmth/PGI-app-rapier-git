package com.pusatgadaiindonesia.app.Model.Bpkb;

import com.google.gson.annotations.SerializedName;

public class SendBpkb {
    @SerializedName("name")
    private String name;

    @SerializedName("stnkOwner")
    private String stnkOwner;

    @SerializedName("stnkAddress")
    private String stnkAddress;

    @SerializedName("bpkbNumber")
    private String bpkbNumber;

    @SerializedName("bpkbOwner")
    private String bpkbOwner;

    @SerializedName("bpkbNumberMachine")
    private String bpkbNumberMachine;

    @SerializedName("bpkbNumberBone")
    private String bpkbNumberBone;

    @SerializedName("creditDate")
    private String creditDate;

    @SerializedName("creditNominal")
    private String creditNominal;

    @SerializedName("creditSewaModal")
    private String creditSewaModal;

    @SerializedName("idLocation")
    private String idLocation;

    @SerializedName("taxExpiredDate")
    private String taxExpiredDate;

    @SerializedName("category")
    private String category;

    @SerializedName("brand")
    private String brand;

    @SerializedName("type")
    private String type;

    @SerializedName("policeNumber")
    private String policeNumber;

    @SerializedName("year")
    private String year;

    @SerializedName("grade")
    private String grade;

    @SerializedName("condition")
    private String condition;

    @SerializedName("statusNameOwner")
    private String statusNameOwner;

    @SerializedName("owner")
    private String owner;

    @SerializedName("locationId")
    private String locationId;

    @SerializedName("subtype")
    private String subtype;

    public SendBpkb(String name, String stnkOwner, String stnkAddress, String bpkbNumber, String bpkbOwner, String bpkbNumberMachine, String bpkbNumberBone,
                    String creditDate, String creditNominal, String creditSewaModal, String idLocation, String taxExpiredDate,
                    String category, String brand, String type, String policeNumber, String year, String grade, String condition, String statusNameOwner,
                    String owner, String locationId, String subtype) {
        this.name = name;
        this.stnkOwner = stnkOwner;
        this.stnkAddress = stnkAddress;
        this.bpkbNumber = bpkbNumber;
        this.bpkbOwner = bpkbOwner;
        this.bpkbNumberMachine = bpkbNumberMachine;
        this.bpkbNumberBone = bpkbNumberBone;
        this.creditDate = creditDate;
        this.creditNominal = creditNominal;
        this.creditSewaModal = creditSewaModal;
        this.idLocation = idLocation;
        this.taxExpiredDate = taxExpiredDate;
        this.category = category;
        this.brand = brand;
        this.type = type;
        this.policeNumber = policeNumber;
        this.year = year;
        this.grade = grade;
        this.condition = condition;
        this.statusNameOwner = statusNameOwner;
        this.owner = owner;
        this.locationId = locationId;
        this.subtype = subtype;
    }
}
