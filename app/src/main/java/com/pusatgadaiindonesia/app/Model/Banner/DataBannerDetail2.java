package com.pusatgadaiindonesia.app.Model.Banner;

import com.google.gson.annotations.SerializedName;

public class DataBannerDetail2 {

    @SerializedName("filePath")
    private String filePath;

    @SerializedName("title")
    private String title;

    @SerializedName("text")
    private String text;

    @SerializedName("url")
    private String url;

    public DataBannerDetail2(String filePath, String title, String text, String url) {
        this.filePath = filePath;
        this.title = title;
        this.text = text;
        this.url = url;
    }

    public String getfilePath() {
        return filePath;
    }

    public String gettitle() {
        return title;
    }

    public String gettext() {
        return text;
    }

    public String geturl() {
        return url;
    }

}
