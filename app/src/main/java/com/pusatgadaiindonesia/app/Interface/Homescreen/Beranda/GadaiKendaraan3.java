package com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import com.pusatgadaiindonesia.app.Database.Data_Profile;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Homescreen;
import com.pusatgadaiindonesia.app.Model.CreditNominal.ResponseCreditNominal;
import com.pusatgadaiindonesia.app.Model.CreditNominal.SendCreditNominal;
import com.pusatgadaiindonesia.app.Model.Kendaraan.SendKendaraan;
import com.pusatgadaiindonesia.app.Model.ResponseNormal;
import com.pusatgadaiindonesia.app.R;
import com.pusatgadaiindonesia.app.Services.Retrofit.APIService;
import com.pusatgadaiindonesia.app.Services.Retrofit.ApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class GadaiKendaraan3 extends AppCompatActivity implements View.OnClickListener {

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

    String sendNama="", sendNopol="", sendKategori="", sendMerk="", sendTipe="",sendYear="", sendGrade="", sendAtasNamaId="", sendAtasNamaText="";
    String sendStnkNama="", sendStnkAlamat="", sendBpkbNo="", sendBpkbNama="", sendBpkbNoMesin="", sendBpkbNoRangka="", sendPajak="", sendSubTipe="";
    String sendMaxLoan="";/*, getPercentage="";*/
    String sendAtasNamaNumb="";

    String day = "30";
    int sendAmount = 0;
    String creditDate = "";

    Dialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_gadai_kendaraan3);
        ButterKnife.bind(this);

        back.setOnClickListener(this);
        simpan.setOnClickListener(this);
        lokasi.setOnClickListener(this);

        sendNama = ""+getIntent().getStringExtra("sendNama");
        sendNopol = ""+getIntent().getStringExtra("sendNopol");
        sendKategori = ""+getIntent().getStringExtra("sendKategori");
        sendMerk = ""+getIntent().getStringExtra("sendMerk");
        sendTipe = ""+getIntent().getStringExtra("sendTipe");
        sendYear = ""+getIntent().getStringExtra("sendYear");
        sendGrade = ""+getIntent().getStringExtra("sendGrade");
        sendAtasNamaId = ""+getIntent().getStringExtra("sendAtasNamaId");
        sendAtasNamaText = ""+getIntent().getStringExtra("sendAtasNamaText");
        sendStnkNama = ""+getIntent().getStringExtra("sendStnkNama");
        sendStnkAlamat = ""+getIntent().getStringExtra("sendStnkAlamat");
        sendBpkbNo = ""+getIntent().getStringExtra("sendBpkbNo");
        sendBpkbNama = ""+getIntent().getStringExtra("sendBpkbNama");
        sendBpkbNoMesin = ""+getIntent().getStringExtra("sendBpkbNoMesin");
        sendBpkbNoRangka = ""+getIntent().getStringExtra("sendBpkbNoRangka");
        sendPajak = ""+getIntent().getStringExtra("sendPajak");
        sendMaxLoan = ""+getIntent().getStringExtra("sendMaxLoan");
        sendSubTipe = ""+getIntent().getStringExtra("sendSubTipe");
        sendAtasNamaNumb = ""+getIntent().getStringExtra("sendAtasNamaNumb");
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
                Intent intent = new Intent(GadaiKendaraan3.this, CabangTerdekatSelect.class);
                intent.putExtra("id", idLocation);
                startActivityForResult(intent, 8);
                break;
        }
    }

    public void getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdFormat = new SimpleDateFormat("dd-MMMM-yyyy");
        SimpleDateFormat mdFormat2 = new SimpleDateFormat("yyyy-MM-dd");

        // @M - Set the sendDate and nowDate variable is current date by default
        String getDate = mdFormat.format(calendar.getTime());
        String[] splitter = getDate.split("-");
        textDay.setText(""+splitter[0]);
        textMonth.setText(""+splitter[1]);
        textYear.setText(""+splitter[2]);
        String getDate2 = mdFormat2.format(calendar.getTime());
        creditDate = ""+getDate2;

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
                Intent intent = new Intent(GadaiKendaraan3.this, Homescreen.class);
                startActivity(intent);
            }
        });
        dialog.show();
    }

    public void sendData()
    {
        dialogLoading();

        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        SendKendaraan sendKendaraan = new SendKendaraan(""+sendNama,""+sendKategori,""+sendMerk,""+sendTipe,""+sendNopol,""+sendYear,""+sendGrade,""+sendGrade,""+sendAtasNamaText,
                                                        ""+sendStnkNama, ""+sendStnkNama, ""+sendStnkAlamat, ""+sendBpkbNo, ""+sendBpkbNama,
                                            ""+sendBpkbNoMesin, ""+sendBpkbNoRangka,""+creditDate, ""+sendAmount, ""+day, ""+idLocation, ""+idLocation, ""+sendPajak, ""+sendSubTipe);
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseNormal> userCall = service.gadaiKendaraan(tokenBearer, sendKendaraan);
        userCall.enqueue(new Callback<ResponseNormal>() {
            @Override
            public void onResponse(Call<ResponseNormal> call, retrofit2.Response<ResponseNormal> response) {
                loading.dismiss();
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
                        //message = response.body().getmessage();
                    } catch (IOException e) {
                        e.printStackTrace();
                        //message = response.body().getmessage();
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

        SendCreditNominal sendCreditNominal = new SendCreditNominal(""+sendTipe,""+sendAtasNamaNumb,""+sendGrade);
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
