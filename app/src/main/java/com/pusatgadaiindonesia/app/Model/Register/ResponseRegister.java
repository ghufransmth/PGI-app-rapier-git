package com.pusatgadaiindonesia.app.Model.Register;

import com.google.gson.annotations.SerializedName;

public class ResponseRegister {
    @SerializedName("code")
    private String code;

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private DataRegister data;

    public ResponseRegister(String code, String status, String message, DataRegister data) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.data = data;
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

    public DataRegister getdata() {
        return data;
    }

}
