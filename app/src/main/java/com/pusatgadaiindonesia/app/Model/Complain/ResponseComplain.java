package com.pusatgadaiindonesia.app.Model.Complain;

import com.google.gson.annotations.SerializedName;
import com.pusatgadaiindonesia.app.Model.Login.DataLogin;

public class ResponseComplain {
    @SerializedName("status")
    private String status;

    @SerializedName("massage")
    private String massage;

    public ResponseComplain(String code, String status, String massage) {
        this.status = status;
        this.massage = massage;
    }

    public String getstatus() {
        return status;
    }

    public String getmassage() {
        return massage;
    }
}
