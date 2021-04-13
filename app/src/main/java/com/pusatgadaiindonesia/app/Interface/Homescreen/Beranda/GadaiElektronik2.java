package com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.pusatgadaiindonesia.app.Database.Data_Profile;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Homescreen;
import com.pusatgadaiindonesia.app.Model.CreditNominal.ResponseCreditNominal;
import com.pusatgadaiindonesia.app.Model.CreditNominal.SendCreditNominal;
import com.pusatgadaiindonesia.app.Model.ResponseNormal;
import com.pusatgadaiindonesia.app.R;
import com.pusatgadaiindonesia.app.Services.Retrofit.APIService;
import com.pusatgadaiindonesia.app.Services.Retrofit.ApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class GadaiElektronik2 extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.simpan)
    CardView simpan;

    @BindView(R.id.textDay)
    TextView textDay;

    @BindView(R.id.textMonth)
    TextView textMonth;

    @BindView(R.id.textYear)
    TextView textYear;

    @BindView(R.id.lokasi)
    LinearLayout lokasi;

    @BindView(R.id.textLokasi)
    TextView textLokasi;

    @BindView(R.id.textLoan)
    TextView textLoan;

    String idLocation = "";

    String getNama="", getImei="", getNote="", getJenis="", getMerk="", getYear="";
    String getTipe="", getGrade="", getDus="", getImage="", getKelengkapan="", getMaxLoan="";/*, getPercentage="";*/
    String sendDate = "2020-10-20";
    String sendDay = "30";
    int sendAmount = 0;

    Dialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_gadai_elektronik2);
        ButterKnife.bind(this);

        back.setOnClickListener(this);
        simpan.setOnClickListener(this);
        lokasi.setOnClickListener(this);

        getNama = ""+getIntent().getStringExtra("sendNama");
        getImei = ""+getIntent().getStringExtra("sendImei");
        getNote = ""+getIntent().getStringExtra("sendNote");
        getJenis = ""+getIntent().getStringExtra("sendJenis");
        getMerk = ""+getIntent().getStringExtra("sendMerk");
        getTipe = ""+getIntent().getStringExtra("sendTipe");
        getGrade = ""+getIntent().getStringExtra("sendGrade");
        getDus = ""+getIntent().getStringExtra("sendDus");
        getImage = ""+getIntent().getStringExtra("sendImage");
        getYear = ""+getIntent().getStringExtra("sendYear");
        getKelengkapan = ""+getIntent().getStringExtra("sendKelengkapan");
        getMaxLoan = ""+getIntent().getStringExtra("sendMaxLoan");
        //getPercentage = ""+getIntent().getStringExtra("percentage");

        getCreditNominal();

        getCurrentDate();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.back:
                onBackPressed();
                break;

            case R.id.simpan:
                if(idLocation.equals(""))
                {
                    dialogWarning("Silakan pilih lokasi cabang terlebih dahulu");
                }
                else
                {
                    sendData();
                }

                break;

            case R.id.lokasi:
                Intent intent = new Intent(GadaiElektronik2.this, CabangTerdekatSelect.class);
                intent.putExtra("id", idLocation);
                startActivityForResult(intent, 8);
                break;
        }
    }


    public void getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdFormat = new SimpleDateFormat("dd-MMMM-yyyy");

        // @M - Set the sendDate and nowDate variable is current date by default
        String getDate = mdFormat.format(calendar.getTime());
        String[] splitter = getDate.split("-");
        textDay.setText(""+splitter[0]);
        textMonth.setText(""+splitter[1]);
        textYear.setText(""+splitter[2]);

    }


    public void dialogLoading()
    {
        // @M - showing the loading dialog
        loading = new Dialog(this);
        loading.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loading.setContentView(R.layout.dialog_loading);
        loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loading.setCancelable(false);
        loading.show();
    }

    public void dialogWarning(String message)
    {
        // @M - showing the warning dialog in old style dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_warning_1button);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView textMessage = dialog.findViewById(R.id.teks);
        textMessage.setText(""+message);

        Button oke = dialog.findViewById(R.id.ok);
        oke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void dialogSuccess(String message)
    {
        // @M - showing the warning dialog in old style dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_success);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView textMessage = dialog.findViewById(R.id.teks);
        textMessage.setText(""+message);

        Button oke = dialog.findViewById(R.id.ok);
        oke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
                Intent intent = new Intent(GadaiElektronik2.this, Homescreen.class);
                startActivity(intent);
            }
        });
        dialog.show();
    }

    public void sendData()
    {
        dialogLoading();

        Uri uriGambar = Uri.parse(getImage);
        File file1 = new File(uriGambar.getPath());
            
        MultipartBody.Part sendName = MultipartBody.Part.createFormData("name", ""+getNama);
        RequestBody requestBody = RequestBody.create(MediaType.parse("**/*//*"), file1);
        MultipartBody.Part sendImage = MultipartBody.Part.createFormData("image", file1.getName(), requestBody);
        MultipartBody.Part sendCategory = MultipartBody.Part.createFormData("category", ""+getJenis);
        MultipartBody.Part sendBrand = MultipartBody.Part.createFormData("brand", ""+getMerk);
        MultipartBody.Part sendType = MultipartBody.Part.createFormData("type", ""+getTipe);
        MultipartBody.Part sendImei = MultipartBody.Part.createFormData("imei", ""+getImei);
        MultipartBody.Part sendGrade = MultipartBody.Part.createFormData("grade", ""+getGrade);
        MultipartBody.Part sendCondition = MultipartBody.Part.createFormData("condition", ""+getDus);
        MultipartBody.Part sendDescription = MultipartBody.Part.createFormData("description", ""+getNote);
        MultipartBody.Part sendCreditDate = MultipartBody.Part.createFormData("creditDate", ""+sendDate);
        MultipartBody.Part sendCreditNominal = MultipartBody.Part.createFormData("creditNominal", ""+sendAmount);
        MultipartBody.Part sendCreditTarifSewa = MultipartBody.Part.createFormData("creditTarifSewa", ""+sendDay);
        MultipartBody.Part locationId = MultipartBody.Part.createFormData("locationId", ""+idLocation);
        MultipartBody.Part idLocationku = MultipartBody.Part.createFormData("idLocation", ""+idLocation);
        MultipartBody.Part year = MultipartBody.Part.createFormData("year", ""+getYear);
        MultipartBody.Part completeness = MultipartBody.Part.createFormData("supportingItems", ""+getKelengkapan);

        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;
        
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseNormal> userCall = service.gadaiElektronik(tokenBearer, sendName, sendImage, sendCategory, sendBrand, sendType,
                                                                    sendImei, sendGrade, sendCondition, sendDescription, sendCreditDate,
                                                                    sendCreditNominal, sendCreditTarifSewa, locationId, year, completeness,  idLocationku);
        userCall.enqueue(new Callback<ResponseNormal>() {
            @Override
            public void onResponse(Call<ResponseNormal> call, retrofit2.Response<ResponseNormal> response) {
                loading.dismiss();
                Log.w("Elektronik", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful()){

                    String myMessage = ""+response.body().getmessage();
                    String status = ""+response.body().getstatus();
                    if(status.equals("success"))
                    {
                        //Toast.makeText(getApplicationContext(),""+response.body().getmessage(), Toast.LENGTH_SHORT).show();
                        dialogSuccess(""+response.body().getmessage());
                    }
                    else
                    {
                        dialogWarning(""+myMessage);
                    }



                }
                else {
                    // @M - If response from server is not success, get the message and show it in dialog
                    JSONObject jObjError = null;
                    String message = ""+getResources().getString(R.string.gagalCobaLagi);
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                        message = ""+jObjError.getString("message");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    final String myMessage = ""+message;
                    Handler handler =  new Handler();
                    Runnable myRunnable = new Runnable() {
                        public void run() {
                            // do something
                            dialogWarning(""+myMessage);
                        }
                    };
                    handler.postDelayed(myRunnable,500);
                }
            }
            @Override
            public void onFailure(Call<ResponseNormal> call, Throwable t) {
                loading.dismiss();
                dialogWarning(getResources().getString(R.string.gagalCobaLagi));
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 8 && resultCode == RESULT_OK)
        {
            idLocation = ""+data.getStringExtra("id");
            textLokasi.setText(""+data.getStringExtra("nama"));
        }
    }


    public void getCreditNominal()
    {
        dialogLoading();
        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        String complete = "";
        if(getKelengkapan.contains("5"))
        {
            complete = "1";
        }
        else
        {
            complete = "2";
        }
        SendCreditNominal sendCreditNominal = new SendCreditNominal(""+getTipe,""+complete,""+getGrade);
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseCreditNominal> userCall = service.getCreditNominal(tokenBearer, sendCreditNominal);
        userCall.enqueue(new Callback<ResponseCreditNominal>() {
            @Override
            public void onResponse(Call<ResponseCreditNominal> call, retrofit2.Response<ResponseCreditNominal> response) {
                loading.dismiss();
                if(response.isSuccessful())
                {
                    Locale localeID = new Locale("in", "ID");
                    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

                    try {
                        sendAmount = Integer.parseInt(""+response.body().getData().getcreditNominal());
                    } catch (NumberFormatException e) {
                        sendAmount = 0;
                        e.printStackTrace();
                    }

                    String text = formatRupiah.format((double)sendAmount);
                    text = text.replace("Rp","");
                    textLoan.setText(text);
                }
                else {

                    // @M - If response from server is not success, get the message and show it in dialog
                    String message = "Gagal. Silakan coba lagi";
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                        message = ""+jObjError.getString("message");
                        Log.e("",""+message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    dialogNominalGagal();
                }
            }
            @Override
            public void onFailure(Call<ResponseCreditNominal> call, Throwable t) {
                loading.dismiss();
                Log.w("Jenis","Error : "+t.getMessage());
                dialogNominalGagal();
            }
        });
    }

    public void dialogNominalGagal()
    {
        // @M - showing the warning dialog in old style dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_warning_1button);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView textMessage = dialog.findViewById(R.id.teks);
        textMessage.setText("Gagal mengambil nominal gadai");

        Button oke = dialog.findViewById(R.id.ok);
        oke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                getCreditNominal();
            }
        });
        dialog.show();
    }
}
