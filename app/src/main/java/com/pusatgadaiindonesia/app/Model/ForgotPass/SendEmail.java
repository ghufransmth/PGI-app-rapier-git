package com.pusatgadaiindonesia.app.Model.ForgotPass;

import com.google.gson.annotations.SerializedName;

public class SendEmail {
    @SerializedName("email")
    private String email;

    public SendEmail(String email) {
        this.email = email;
    }
}
