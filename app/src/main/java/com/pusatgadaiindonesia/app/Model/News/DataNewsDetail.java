package com.pusatgadaiindonesia.app.Model.News;

import com.google.gson.annotations.SerializedName;

public class DataNewsDetail {
    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("status")
    private String status;

    public DataNewsDetail(String id, String title, String description, String createdAt, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.status = status;
    }

    public String getid() {
        return id;
    }

    public String gettitle() {
        return title;
    }

    public String getdescription() {
        return description;
    }

    public String getcreatedAt() {
        return createdAt;
    }

    public String getStatus() {
        return status;
    }

}
