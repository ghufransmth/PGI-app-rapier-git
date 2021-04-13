package com.pusatgadaiindonesia.app.Model.Login;

import com.google.gson.annotations.SerializedName;

public class DataLogin {
    @SerializedName("accessToken")
    private String accessToken;

    @SerializedName("refreshToken")
    private String refreshToken;

    @SerializedName("accessTokenExpAt")
    private String accessTokenExpAt;

    @SerializedName("refreshTokenExpAt")
    private String refreshTokenExpAt;

    @SerializedName("mustChangePassword")
    private String mustChangePassword;

    public DataLogin(String accessToken, String refreshToken, String accessTokenExpAt, String refreshTokenExpAt, String mustChangePassword) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpAt = accessTokenExpAt;
        this.refreshTokenExpAt = refreshTokenExpAt;
        this.mustChangePassword = mustChangePassword;
    }

    public String getaccessToken() {
        return accessToken;
    }

    public String getrefreshToken() {
        return refreshToken;
    }

    public String getAccessTokenExpAt() {
        return accessTokenExpAt;
    }

    public String getrefreshTokenExpAt() {
        return refreshTokenExpAt;
    }

    public String getmustChangePassword() {
        return mustChangePassword;
    }

}
