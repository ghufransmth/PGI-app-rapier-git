package com.pusatgadaiindonesia.app.Model.Login;

import com.google.gson.annotations.SerializedName;

public class SendLogin {
    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("grant_type")
    private String grant_type;

    public SendLogin(String username, String password, String grant_type) {
        this.username = username;
        this.password = password;
        this.grant_type = grant_type;
    }
}
