package com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.pusatgadaiindonesia.app.Adapter.AdapterNotifikasi;
import com.pusatgadaiindonesia.app.Database.Data_Profile;
import com.pusatgadaiindonesia.app.Model.Notification.DataNotificationDetail;
import com.pusatgadaiindonesia.app.Model.Notification.ResponseNotification;
import com.pusatgadaiindonesia.app.R;
import com.pusatgadaiindonesia.app.Services.Retrofit.APIService;
import com.pusatgadaiindonesia.app.Services.Retrofit.ApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class DetailPengajuan extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.tglTransaksi)
    TextView tglTransaksi;

    @BindView(R.id.kategori)
    TextView kategori;

    @BindView(R.id.noAntrian)
    TextView noAntrian;

    @BindView(R.id.namaNasabah)
    TextView namaNasabah;

    @BindView(R.id.jenis)
    TextView jenis;

    @BindView(R.id.merk)
    TextView merk;

    @BindView(R.id.tipe)
    TextView tipe;

    @BindView(R.id.warna)
    TextView warna;

    @BindView(R.id.tahun)
    TextView tahun;

    @BindView(R.id.total)
    TextView total;

    String getTglTransaksi="", getNoAntrian="", getJenis="", getMerk="", getTipe="", getWarna="", getTahun="", getTotal="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_detail_transaksi_pengajuan);
        ButterKnife.bind(this);

        back.setOnClickListener(this);

        getTglTransaksi = ""+changeDate(getIntent().getStringExtra("sendTglTransaksi"));
        getNoAntrian = ""+getIntent().getStringExtra("sendNoAntrian");
        getJenis = ""+getIntent().getStringExtra("sendJenis");
        getMerk = ""+getIntent().getStringExtra("sendMerk");
        getTipe = ""+getIntent().getStringExtra("sendTipe");
        getWarna = ""+getIntent().getStringExtra("sendWarna");
        getTahun = ""+getIntent().getStringExtra("sendTahun");
        getTotal = "Rp. "+currencyFormat(getIntent().getStringExtra("sendTotal"));

        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        tglTransaksi.setText(""+getTglTransaksi);
        kategori.setText("Pengajuan");
        noAntrian.setText(""+getNoAntrian);
        namaNasabah.setText(""+data_profile.nama);
        jenis.setText(""+getJenis);
        merk.setText(""+getMerk);
        tipe.setText(""+getTipe);
        warna.setText(""+getWarna);
        tahun.setText(""+getTahun);
        total.setText(""+getTotal);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.back:
                onBackPressed();
                break;
        }
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

    public static String currencyFormat(String amount) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0.00");
        return formatter.format(Double.parseDouble(amount));
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
