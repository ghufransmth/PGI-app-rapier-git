package com.pusatgadaiindonesia.app.Model.Notification;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataNotification {
    @SerializedName("data")
    private List<DataNotificationDetail> data;

    @SerializedName("totalElements")
    private String totalElements;

    @SerializedName("totalPages")
    private String totalPages;
    

    public DataNotification(List<DataNotificationDetail> data, String totalElements, String totalPages) {
        this.data = data;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public List<DataNotificationDetail> getdata() {
        return data;
    }

    public String gettotalElements() {
        return totalElements;
    }

    public String gettotalPages() {
        return totalPages;
    }
}
