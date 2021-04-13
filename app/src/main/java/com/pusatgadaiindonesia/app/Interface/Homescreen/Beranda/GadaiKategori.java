package com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda;

import android.app.Dialog;
import android.content.Intent;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class GadaiKategori extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.elektronik)
    CardView elektronik;

    @BindView(R.id.kendaraan)
    CardView kendaraan;

    @BindView(R.id.bpkb)
    CardView bpkb;

    @BindView(R.id.emas)
    CardView emas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_gadai_kategori);
        ButterKnife.bind(this);

        back.setOnClickListener(this);
        elektronik.setOnClickListener(this);
        kendaraan.setOnClickListener(this);
        bpkb.setOnClickListener(this);
        emas.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.back:
                onBackPressed();
                break;

            case R.id.elektronik:
                Intent intent = new Intent(GadaiKategori.this, GadaiElektronik3.class);
                startActivity(intent);
                break;

            case R.id.kendaraan:
                Toast.makeText(this,"Fitur masih dalam pengembangan",Toast.LENGTH_SHORT).show();
                break;

            case R.id.bpkb:
                Intent intent2 = new Intent(GadaiKategori.this, GadaiKendaraan4.class);
                startActivity(intent2);
                break;

            case R.id.emas:
                Toast.makeText(this,"Fitur masih dalam pengembangan",Toast.LENGTH_SHORT).show();
                break;
        }
    }


}
