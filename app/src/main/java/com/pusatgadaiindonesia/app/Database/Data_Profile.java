package com.pusatgadaiindonesia.app.Database;

import com.orm.SugarRecord;

public class Data_Profile extends SugarRecord {
    public String accessToken, refreshToken, accessTokenExpAt, refreshTokenExpAt, mustChangePassword;
    public boolean login;

    public String userId, userName, userEmail, nama, nik, jenisIdentitas, alamat, noHandphone;


    public Data_Profile() {
    }

    public Data_Profile(String accessToken, String refreshToken, String accessTokenExpAt, String refreshTokenExpAt, String mustChangePassword, String nik,
                        String nama, String jenisIdentitas, String alamat, String noHandphone) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpAt = accessTokenExpAt;
        this.refreshTokenExpAt = refreshTokenExpAt;
        this.mustChangePassword = mustChangePassword;
        this.nik = nik;
        this.nama = nama;
        this.jenisIdentitas = jenisIdentitas;
        this.alamat = alamat;
        this.noHandphone = noHandphone;
    }
}