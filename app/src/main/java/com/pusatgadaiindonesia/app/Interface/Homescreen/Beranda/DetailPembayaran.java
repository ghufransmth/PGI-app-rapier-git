package com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.pusatgadaiindonesia.app.Database.Data_Profile;
import com.pusatgadaiindonesia.app.Model.ResponseNormal2;
import com.pusatgadaiindonesia.app.R;
import com.pusatgadaiindonesia.app.Services.Retrofit.APIService;
import com.pusatgadaiindonesia.app.Services.Retrofit.ApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class DetailPembayaran extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.tglTransaksi)
    TextView tglTransaksi;

    @BindView(R.id.kategori)
    TextView kategori;

    @BindView(R.id.noFaktur)
    TextView noFaktur;

    @BindView(R.id.namaBarang)
    TextView namaBarang;

    @BindView(R.id.va)
    TextView va;

    @BindView(R.id.metodeBayar)
    TextView metodeBayar;

    @BindView(R.id.totalTagihan)
    TextView total;

    @BindView(R.id.status)
    TextView status;

    @BindView(R.id.batalkan)
    CardView batalkan;

    String getPembayaranId="", getTglTransaksi="", getStatus="", getNoFaktur="", getNamaBarang="", getVa="", getMetodeBayar="", getTotal="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_detail_transaksi_pembayaran);
        ButterKnife.bind(this);

        back.setOnClickListener(this);

        getStatus = ""+getIntent().getStringExtra("sendStatus");
        getPembayaranId = ""+getIntent().getStringExtra("sendPembayaranId");
        getTglTransaksi = ""+getIntent().getStringExtra("sendTglTransaksi");
        getNoFaktur = ""+getIntent().getStringExtra("sendNoFaktur");
        getNamaBarang = ""+getIntent().getStringExtra("sendNamaBarang");
        getVa = ""+getIntent().getStringExtra("sendVa");
        getMetodeBayar = ""+getIntent().getStringExtra("sendMetodeBayar");
        getTotal = ""+getIntent().getStringExtra("sendTotal");

        tglTransaksi.setText(""+changeDate(getTglTransaksi));
        kategori.setText("Pembayaran");
        noFaktur.setText(""+getNoFaktur);
        namaBarang.setText(""+getNamaBarang);
        va.setText(""+getVa);
        metodeBayar.setText(""+getMetodeBayar);
        total.setText("Rp. "+currencyFormat(getTotal));

        Log.d("Status",""+getStatus);
        if(getStatus.equals("1")){
            batalkan.setVisibility(View.GONE);
            status.setText("Berhasil");
            status.setTextColor(Color.GREEN);
        }else if(getStatus.equals("2")){
            batalkan.setVisibility(View.GONE);
            status.setText("Batal");
            status.setTextColor(Color.RED);
        }else {
            batalkan.setVisibility(View.VISIBLE);
            status.setText("Pending");
            status.setTextColor(Color.GRAY);
        }

        batalkan.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.back:
                onBackPressed();
                break;

            case R.id.batalkan:
                dialogBatalkan();
                break;
        }
    }

    public void batalkanPembayaran()
    {
        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseNormal2> userCall = service.batalkanTransaksi(tokenBearer,"pembayaran/cancel/"+getPembayaranId+"");
        userCall.enqueue(new Callback<ResponseNormal2>() {
            @Override
            public void onResponse(Call<ResponseNormal2> call, retrofit2.Response<ResponseNormal2> response) {
//                swipeRefreshLayout2.setRefreshing(false);
                //Log.w("", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful())
                {
                    String myMessage = ""+response.body().getreason();
                    String status = ""+response.body().getstatus();
                    if(status.equals("true"))
                    {
                        dialogSuccess(""+myMessage);
                    }
                    else
                    {
                        dialogWarning(""+myMessage);
                    }

                }
                else
                {
                    // @M - If response from server is not success, get the message and show it in dialog
                    JSONObject jObjError = null;
                    String message = ""+getResources().getString(R.string.gagalCobaLagi);
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                        message = ""+jObjError.getString("reason");
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
            public void onFailure(Call<ResponseNormal2> call, Throwable t) {
//                swipeRefreshLayout2.setRefreshing(false);
                dialogWarning(getResources().getString(R.string.gagalCobaLagi));
            }
        });
    }

    public static String currencyFormat(String amount) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0.00");
        return formatter.format(Double.parseDouble(amount));
    }

    public static String changeDate(String dates){
        Locale us = new Locale("US");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", us);
        String stringDate = "";
        try {
            Date date = format.parse(dates);
            stringDate = new SimpleDateFormat("dd-MMM-yyyy", us).format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return stringDate;
    }

    public void dialogBatalkan()
    {
        // @M - showing the warning dialog in old style dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.modal_dialog_batal_transaksi);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        CardView batal = dialog.findViewById(R.id.batalkan);
        CardView lanjutkan = dialog.findViewById(R.id.lanjut);

        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                batalkanPembayaran();
            }
        });

        lanjutkan.setOnClickListener(new View.OnClickListener() {
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
            }
        });
        dialog.show();
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

}
