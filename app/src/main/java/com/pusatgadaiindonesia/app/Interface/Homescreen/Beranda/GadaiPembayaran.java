package com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.pusatgadaiindonesia.app.Interface.Homescreen.FragmentDaftarGadai;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Homescreen;
import com.pusatgadaiindonesia.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GadaiPembayaran extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.perpanjangan)
    CardView perpanjangan;

    @BindView(R.id.pelunasan)
    CardView pelunasan;

    @BindView(R.id.pulsa)
    CardView pulsa;

    @BindView(R.id.listrik)
    CardView listrik;

    @BindView(R.id.air)
    CardView air;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_pembayaran_kategori);
        ButterKnife.bind(this);

        back.setOnClickListener(this);
        perpanjangan.setOnClickListener(this);
        pelunasan.setOnClickListener(this);
        pulsa.setOnClickListener(this);
        listrik.setOnClickListener(this);
        air.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.back:
                onBackPressed();
                break;

            case R.id.perpanjangan:
                Intent intent = new Intent(GadaiPembayaran.this, Homescreen.class);
                intent.putExtra("frgToLoad", "1");
                startActivityForResult(intent, 9);
                break;

            case R.id.pelunasan:
                Intent intent2 = new Intent(GadaiPembayaran.this, Homescreen.class);
                intent2.putExtra("frgToLoad", "2");
                startActivityForResult(intent2, 9);
                break;

            case R.id.pulsa:
                Toast.makeText(this,"Fitur masih dalam pengembangan",Toast.LENGTH_SHORT).show();
                break;

            case R.id.listrik:
                Toast.makeText(this,"Fitur masih dalam pengembangan",Toast.LENGTH_SHORT).show();
                break;

            case R.id.air:
                Toast.makeText(this,"Fitur masih dalam pengembangan",Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
