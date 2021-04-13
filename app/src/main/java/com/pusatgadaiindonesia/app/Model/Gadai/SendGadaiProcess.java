package com.pusatgadaiindonesia.app.Model.Gadai;

import com.google.gson.annotations.SerializedName;

public class SendGadaiProcess {

    @SerializedName("tahunId")
    private String tahunId;

    @SerializedName("tipeId")
    private String tipeId;

    @SerializedName("warnaId")
    private String warnaId;

    public SendGadaiProcess(String tahunId, String tipeId, String warnaId) {
        this.tahunId = tahunId;
        this.tipeId = tipeId;
        this.warnaId = warnaId;
    }

}
