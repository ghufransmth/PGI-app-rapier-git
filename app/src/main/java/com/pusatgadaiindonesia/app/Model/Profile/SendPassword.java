package com.pusatgadaiindonesia.app.Model.Profile;

import com.google.gson.annotations.SerializedName;

public class SendPassword {

    @SerializedName("newPassword")
    private String newPassword;

    @SerializedName("oldPassword")
    private String oldPassword;

    public SendPassword(String newPassword, String oldPassword) {
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
    }

}
