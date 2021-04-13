package com.pusatgadaiindonesia.app.Model.Notification;

import com.google.gson.annotations.SerializedName;

public class ResponseNotification {

    @SerializedName("status")
    private String status;

    @SerializedName("reason")
    private String reason;

    @SerializedName("data")
    private DataNotification data;

    public ResponseNotification(String status, String reason, DataNotification data) {
        this.status = status;
        this.reason = reason;
        this.data = data;
    }

    public String getstatus() {
        return status;
    }

    public String getreason() {
        return reason;
    }

    public DataNotification getdata() {
        return data;
    }

}
