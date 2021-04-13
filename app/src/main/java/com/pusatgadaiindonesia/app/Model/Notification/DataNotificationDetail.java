package com.pusatgadaiindonesia.app.Model.Notification;

import com.google.gson.annotations.SerializedName;

public class DataNotificationDetail {
    @SerializedName("id")
    private String id;

    @SerializedName("gadaiId")
    private String gadaiId;

    @SerializedName("message")
    private String message;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("read")
    private String read;

    public DataNotificationDetail(String id, String gadaiId, String message, String createdAt, String read) {
        this.id = id;
        this.gadaiId = gadaiId;
        this.message = message;
        this.createdAt = createdAt;
        this.read = read;
    }

    public String getid() {
        return id;
    }

    public String getgadaiId() {
        return gadaiId;
    }

    public String getmessage() {
        return message;
    }

    public String getcreatedAt() {
        return createdAt;
    }

    public String getread() {
        return read;
    }

}
