package com.pusatgadaiindonesia.app.Model.Location;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseLocation2 {

    @SerializedName("status")
    private String status;

    @SerializedName("reason")
    private String reason;

    @SerializedName("data")
    private List<DataLocation2> data;

    public ResponseLocation2(String status, String reason, List<DataLocation2> data) {
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

    public List<DataLocation2> getdata() {
        return data;
    }

}
