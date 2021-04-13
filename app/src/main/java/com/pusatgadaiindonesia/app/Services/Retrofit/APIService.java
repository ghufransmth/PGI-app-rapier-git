package com.pusatgadaiindonesia.app.Services.Retrofit;

import com.pusatgadaiindonesia.app.Model.Banner.ResponseBanner;
import com.pusatgadaiindonesia.app.Model.Barang.ResponseBarang;
import com.pusatgadaiindonesia.app.Model.BayarListrik.SendListrik;
import com.pusatgadaiindonesia.app.Model.Bpkb.SendBpkb;
import com.pusatgadaiindonesia.app.Model.CreditNominal.ResponseCreditNominal;
import com.pusatgadaiindonesia.app.Model.CreditNominal.SendCreditNominal;
import com.pusatgadaiindonesia.app.Model.Device.SendDevice;
import com.pusatgadaiindonesia.app.Model.Gadai.ResponseGadai;
import com.pusatgadaiindonesia.app.Model.Gadai.ResponseGadai2;
import com.pusatgadaiindonesia.app.Model.Gadai.ResponseGadaiMobile;
import com.pusatgadaiindonesia.app.Model.Gadai.SendGadaiProcess;
import com.pusatgadaiindonesia.app.Model.Inquiry.ResponseInquiry;
import com.pusatgadaiindonesia.app.Model.Instruksi.ResponseInstruksi;
import com.pusatgadaiindonesia.app.Model.Kendaraan.SendKendaraan;
import com.pusatgadaiindonesia.app.Model.Location.ResponseLocation;
import com.pusatgadaiindonesia.app.Model.Location.ResponseLocation2;
import com.pusatgadaiindonesia.app.Model.Master.ResponseMaster;
import com.pusatgadaiindonesia.app.Model.Cities.ResponseCities;
import com.pusatgadaiindonesia.app.Model.Complain.ResponseComplain;
import com.pusatgadaiindonesia.app.Model.Complain.SendComplain;
import com.pusatgadaiindonesia.app.Model.ForgotPass.ResponseForgot;
import com.pusatgadaiindonesia.app.Model.ForgotPass.SendEmail;
import com.pusatgadaiindonesia.app.Model.Login.ResponseLogin;
import com.pusatgadaiindonesia.app.Model.Login.SendLogin;
import com.pusatgadaiindonesia.app.Model.MetodeBayar.ResponseMetodeBayar;
import com.pusatgadaiindonesia.app.Model.MetodeBayar.ResponsePerpanjangan;
import com.pusatgadaiindonesia.app.Model.MetodeBayar.ResponsePerpanjanganConvenience;
import com.pusatgadaiindonesia.app.Model.MetodeBayar.SendBayar;
import com.pusatgadaiindonesia.app.Model.News.ResponseNews;
import com.pusatgadaiindonesia.app.Model.Notification.ResponseNotification;
import com.pusatgadaiindonesia.app.Model.Pembayaran.ResponsePembayaran;
import com.pusatgadaiindonesia.app.Model.PerangkatBayar.ResponsePerangkatBayar;
import com.pusatgadaiindonesia.app.Model.Percentage.ResponsePercentage;
import com.pusatgadaiindonesia.app.Model.Percentage.SendPercentage;
import com.pusatgadaiindonesia.app.Model.Profile.ResponseProfile;
import com.pusatgadaiindonesia.app.Model.Profile.ResponseProfile2;
import com.pusatgadaiindonesia.app.Model.Profile.SendPassword;
import com.pusatgadaiindonesia.app.Model.Province.ResponseProvince;
import com.pusatgadaiindonesia.app.Model.Register.ResponseRegister;
import com.pusatgadaiindonesia.app.Model.Register.SendRegister;
import com.pusatgadaiindonesia.app.Model.ResponseNormal;
import com.pusatgadaiindonesia.app.Model.ResponseNormal2;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface APIService {

//    @POST("user/login")
//    Call<ResponseLogin> login(@Body SendLogin body);

    // @M - User - Login
    @FormUrlEncoded
    @POST("api/user/authenticate")
    Call<ResponseLogin> login(@Field("username") String username, @Field("password") String password, @Field("grant_type") String grant_type);

    // @M - User - Register
    @Headers("Accept: application/json")
    @POST("user/register")
    Call<ResponseRegister> register(@Body SendRegister body);

    // @M - User - Register
    @Headers("Accept: application/json")
    @POST("user/register-device")
    Call<ResponseNormal2> registerDevice(@Header("Authorization") String token, @Body SendDevice body);

    // @M - User - EditProfile
    @PUT("user/profile/edit")
    Call<ResponseNormal> editProfile(@Header("Authorization") String token,
                                     @Body SendRegister body);

    // @M - User - EditProfile - Password
    @POST("user/change-password")
    Call<ResponseNormal2> editPassword(@Header("Authorization") String token,
                                     @Body SendPassword body);

    @POST("user/forgot")
    Call<ResponseForgot> forgot(@Body SendEmail body);

    @Headers("Accept: application/json")
    @GET("user/detail")
    Call<ResponseProfile> getProfile(@Header("Authorization") String token);

    @GET
    Call<ResponseProfile2> getProfile2(@Header("Authorization") String token, @Url String url);

    @POST("user/logout")
    Call<ResponseProfile> logout(@Header("Authorization") String token);

    @Multipart
    @POST("user/profile/picture/upload")
    Call<ResponseNormal> updateAvatar(@Header("Authorization") String token,
                                      @Part MultipartBody.Part id,
                                      @Part MultipartBody.Part image);

    // @M - Master
    // @M - Get All Provinces
    @GET("master/provinces")
    Call<ResponseProvince> getAllProvince();

    // @M - Get Cities Based on Provinces
    @GET
    Call<ResponseCities> getCities(@Url String url);

    // @M - Get Category / Jenis
    @GET("master/categories")
    Call<ResponseMaster> getAllCategory(@Header("Authorization") String token);

    // @M - Get Category / Jenis Elektronikl
    @GET("master/categories?gtype=ge")
    Call<ResponseMaster> getAllCategoryElektronik(@Header("Authorization") String token);

    // @M - Get Merk
    @GET
    Call<ResponseMaster> getMerk(@Header("Authorization") String token,
                                 @Url String url);

    // @M - Get Tipe
    @GET
    Call<ResponseMaster> getTipe(@Header("Authorization") String token,
                                 @Url String url);

//    @GET("message/notification")
//    Call<ResponseNotification> getNotification(@Header("Authorization") String token);

    @GET
    Call<ResponseNotification> getNotification(@Header("Authorization") String token, @Url String url);

    @POST
    Call<ResponseNormal2> setReadNotification(@Header("Authorization") String token, @Url String url);

    @POST
    Call<ResponseInquiry> getInquiry(@Header("Authorization") String token, @Url String url);

    @GET
    Call<ResponseGadai> getDataAktif(@Header("Authorization") String token, @Url String url);

    @GET
    Call<ResponseGadai2> getRiwayatGadai(@Header("Authorization") String token, @Url String url);

    @GET
    Call<ResponseGadaiMobile> getGadaiMobile(@Header("Authorization") String token, @Url String url);

    @GET
    Call<ResponsePembayaran> getPembayaranList(@Header("Authorization") String token, @Url String url);

    @POST
    Call<ResponseNormal2> batalkanTransaksi(@Header("Authorization") String token, @Url String url);

    @POST
    Call<ResponseNormal2> checkPerpanjangan(@Header("Authorization") String token, @Url String url, @Body SendBayar body);

    @GET("news/article")
    Call<ResponseNews> getNews(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("post_keluhan_saran")
    Call<ResponseComplain> complain(@Field("no_anggota") String no_anggota,
                                    @Field("nama_nasabah") String nama_nasabah,
                                    @Field("no_telp") String no_telp,
                                    @Field("kategori") String kategori,
                                    @Field("keterangan") String keterangan);

    @GET("/banner/")
    Call<ResponseBanner> getBanner();

    @GET
    Call<ResponseBanner> getBanner2(@Header("Authorization") String token, @Url String url);

    @GET
    Call<ResponseInstruksi> getInstruksiDetail(@Header("Authorization") String token, @Url String url);

    @GET
    Call<ResponsePerangkatBayar> getInstruksiList(@Header("Authorization") String token, @Url String url);

    @GET
    Call<ResponseMetodeBayar> getMetodePembayaran(@Header("Authorization") String token, @Url String url);

    @POST
    Call<ResponsePerpanjangan> bayarBank(@Header("Authorization") String token,
                                         @Body SendBayar body, @Url String url);

    @POST
    Call<ResponsePerpanjanganConvenience> bayarConvenienceStore(@Header("Authorization") String token,
                                                                @Body SendBayar body, @Url String url);

    @GET
    Call<ResponseNormal2> getCountNotif(@Header("Authorization") String token, @Url String url);

    @GET
    Call<ResponseNormal2> getCountRiwayat(@Header("Authorization") String token, @Url String url);


    // @M - Post Gadai Elektronik
    @Headers("Accept: application/json")
    @Multipart
    @POST("transaction/gadai/elektronik/add")
    Call<ResponseNormal> gadaiElektronik(@Header("Authorization") String token,
                                         @Part MultipartBody.Part name,
                                         @Part MultipartBody.Part image,
                                         @Part MultipartBody.Part category,
                                         @Part MultipartBody.Part brand,
                                         @Part MultipartBody.Part type,
                                         @Part MultipartBody.Part imei,
                                         @Part MultipartBody.Part grade,
                                         @Part MultipartBody.Part condition,
                                         @Part MultipartBody.Part description,
                                         @Part MultipartBody.Part creditDate,
                                         @Part MultipartBody.Part creaditNominal,
                                         @Part MultipartBody.Part creditTarifSewa,
                                         @Part MultipartBody.Part locationId,
                                         @Part MultipartBody.Part year,
                                         @Part MultipartBody.Part supportingItems,
                                         @Part MultipartBody.Part idLocation);

    // @M - Beli Pulsa Listrik
    @POST("transaction/bayar/listrik/add")
    Call<ResponseNormal> bayarListrik(@Header("Authorization") String token,
                                      @Body SendListrik body);

    // @M - Gadai Kendaraan
    @Headers("Accept: application/json")
    @POST("transaction/gadai/kendaraan/add")
    Call<ResponseNormal> gadaiKendaraan(@Header("Authorization") String token,
                                        @Body SendKendaraan body);

    // @M - Gadai BPKB
    @Headers("Accept: application/json")
    @POST("transaction/gadai/bpkb/add")
    Call<ResponseNormal> gadaiBpkb(@Header("Authorization") String token,
                                   @Body SendBpkb body);

    @POST("gadai/process")
    Call<ResponseNormal2> gadaiProcess(@Header("Authorization") String token,
                                   @Body SendGadaiProcess body);

    @POST("gadai/hitung")
    Call<ResponseNormal2> gadaiHitung(@Header("Authorization") String token,
                                       @Body SendGadaiProcess body);

    // @M - Get All Barang of PGI
    @GET
    Call<ResponseBarang> getAllBarang(@Header("Authorization") String token,
                                      @Url String url);

    // @M - Get Location of PGI
    @GET
    Call<ResponseLocation> getAllLocation(@Header("Authorization") String token,
                                           @Url String url);

    // @M - Get Location of PGI
    @GET
    Call<ResponseLocation2> getAllLocation2(@Header("Authorization") String token,
                                           @Url String url);

    // @M - Get Percentage
    @POST("master/brand/percentage")
    Call<ResponsePercentage> getPercentage(@Header("Authorization") String token,
                                           @Body SendPercentage body);

    // @M - Get Credit Nominal
    @POST("master/brand/credit")
    Call<ResponseCreditNominal> getCreditNominal(@Header("Authorization") String token,
                                                 @Body SendCreditNominal body);


}