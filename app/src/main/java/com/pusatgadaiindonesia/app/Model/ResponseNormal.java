package com.pusatgadaiindonesia.app.Model;

import com.google.gson.annotations.SerializedName;
import com.pusatgadaiindonesia.app.Model.Register.DataRegister;

public class ResponseNormal {
    @SerializedName("code")
    private String code;

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    public ResponseNormal(String code, String status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public String getcode() {
        return code;
    }

    public String getstatus() {
        return status;
    }

    public String getmessage() {
        return message;
    }

}
