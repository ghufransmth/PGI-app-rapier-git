package com.pusatgadaiindonesia.app.Model.CreditNominal;

import com.google.gson.annotations.SerializedName;

public class DataCreditNominal {
    @SerializedName("loanType")
    private String loanType;

    @SerializedName("grade")
    private String grade;

    @SerializedName("completeness")
    private String completeness;

    @SerializedName("price")
    private String price;

    @SerializedName("percentage")
    private String percentage;

    @SerializedName("creditNominal")
    private String creditNominal;

    public DataCreditNominal(String loanType, String grade, String completeness, String price, String percentage, String creditNominal) {
        this.loanType = loanType;
        this.grade = grade;
        this.completeness = completeness;
        this.price = price;
        this.percentage = percentage;
        this.creditNominal = creditNominal;
    }

    public String getloanType() {
        return loanType;
    }

    public String getgrade() {
        return grade;
    }

    public String getcompleteness() {
        return completeness;
    }

    public String getprice() {
        return price;
    }

    public String getpercentage() {
        return percentage;
    }

    public String getcreditNominal() {
        return creditNominal;
    }
}
