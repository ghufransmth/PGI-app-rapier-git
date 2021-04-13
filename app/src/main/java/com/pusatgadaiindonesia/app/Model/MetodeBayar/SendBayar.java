package com.pusatgadaiindonesia.app.Model.MetodeBayar;

import com.google.gson.annotations.SerializedName;

public class SendBayar {

    @SerializedName("angsuranPokok")
    private String angsuranPokok;

    @SerializedName("bayarBulanBerikutnya")
    private String bayarBulanBerikutnya;

    @SerializedName("paymentMethodId")
    private String paymentMethodId;

    public SendBayar(String angsuranPokok, String bayarBulanBerikutnya, String paymentMethodId) {
        this.angsuranPokok = angsuranPokok;
        this.bayarBulanBerikutnya = bayarBulanBerikutnya;
        this.paymentMethodId = paymentMethodId;
    }

}
