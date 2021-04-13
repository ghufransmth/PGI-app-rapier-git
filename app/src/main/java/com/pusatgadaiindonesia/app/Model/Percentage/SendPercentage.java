package com.pusatgadaiindonesia.app.Model.Percentage;

import com.google.gson.annotations.SerializedName;

public class SendPercentage {
    @SerializedName("brandId")
    private String brandId;

    @SerializedName("loanType")
    private String loanType;

    @SerializedName("grade")
    private String grade;

    public SendPercentage(String brandId, String loanType, String grade) {
        this.brandId = brandId;
        this.loanType = loanType;
        this.grade = grade;
    }

    public String getbrandId() {
        return brandId;
    }

    public String getloanType() {
        return loanType;
    }

    public String getgrade() {
        return grade;
    }

}
