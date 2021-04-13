package com.pusatgadaiindonesia.app.Model.ForgotPass;

import com.google.gson.annotations.SerializedName;
import com.pusatgadaiindonesia.app.Model.Login.DataLogin;

public class ResponseForgot {
    @SerializedName("code")
    private String code;

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private DataForgot data;

    public ResponseForgot(String code, String status, String message, DataForgot data) {
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

    public DataForgot getdata() {
        return data;
    }

}
