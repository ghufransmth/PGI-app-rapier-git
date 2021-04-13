package com.pusatgadaiindonesia.app.Model.Instruksi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataInstruksiDetail {

    @SerializedName("sequence")
    private String sequence;

    @SerializedName("content")
    private String content;

    @SerializedName("imagePath")
    private String imagePath;


    public DataInstruksiDetail(String sequence, String content, String imagePath) {
        this.sequence = sequence;
        this.content = content;
        this.imagePath = imagePath;
    }

    public String getsequence() {
        return sequence;
    }

    public String getcontent() {
        return content;
    }

    public String getimagePath() {
        return imagePath;
    }

}
