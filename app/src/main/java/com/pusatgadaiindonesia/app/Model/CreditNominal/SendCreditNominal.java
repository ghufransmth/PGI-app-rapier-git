package com.pusatgadaiindonesia.app.Model.CreditNominal;

import com.google.gson.annotations.SerializedName;

public class SendCreditNominal {
    @SerializedName("typeId")
    private String typeId;

    @SerializedName("completeness")
    private String completeness;

    @SerializedName("grade")
    private String grade;

    public SendCreditNominal(String typeId, String completeness, String grade) {
        this.typeId = typeId;
        this.completeness = completeness;
        this.grade = grade;
    }

    public String getTypeId() {
        return typeId;
    }

    public String getCompleteness() {
        return completeness;
    }

    public String getGrade() {
        return grade;
    }

}
