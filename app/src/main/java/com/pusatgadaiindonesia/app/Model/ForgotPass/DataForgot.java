package com.pusatgadaiindonesia.app.Model.ForgotPass;

import com.google.gson.annotations.SerializedName;

public class DataForgot {
    @SerializedName("message")
    private String message;


    public DataForgot(String message) {
        this.message = message;
    }

    public String getmessage() {
        return message;
    }
}
