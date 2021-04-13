package com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.pusatgadaiindonesia.app.Adapter.AdapterNotifikasi;
import com.pusatgadaiindonesia.app.Adapter.AdapterRiwayatPembayaran;
import com.pusatgadaiindonesia.app.Adapter.AdapterRiwayatPengajuan;
import com.pusatgadaiindonesia.app.Database.Data_Profile;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Homescreen;
import com.pusatgadaiindonesia.app.Model.Gadai.DataGadaiMobile;
import com.pusatgadaiindonesia.app.Model.Gadai.ResponseGadaiMobile;
import com.pusatgadaiindonesia.app.Model.Notification.DataNotificationDetail;
import com.pusatgadaiindonesia.app.Model.Notification.ResponseNotification;
import com.pusatgadaiindonesia.app.Model.Pembayaran.DataPembayaran;
import com.pusatgadaiindonesia.app.Model.Pembayaran.DataPembayaranDetail;
import com.pusatgadaiindonesia.app.Model.Pembayaran.DataPembayaranDetail2;
import com.pusatgadaiindonesia.app.Model.Pembayaran.ResponsePembayaran;
import com.pusatgadaiindonesia.app.Model.ResponseNormal2;
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

public class Riwayat extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.rv)
    RecyclerView recyclerView;

    @BindView(R.id.refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.rv2)
    RecyclerView recyclerView2;

    @BindView(R.id.refresh2)
    SwipeRefreshLayout swipeRefreshLayout2;

    @BindView(R.id.pengajuan)
    RelativeLayout pengajuan;

    @BindView(R.id.pembayaran)
    RelativeLayout pembayaran;

    @BindView(R.id.layAktif)
    LinearLayout layAktif;

    @BindView(R.id.layRiwayat)
    LinearLayout layRiwayat;

    @BindView(R.id.aktifSelected)
    LinearLayout aktifSelected;

    @BindView(R.id.riwayatSelected)
    LinearLayout riwayatSelected;

    @BindView(R.id.textstatus)
    TextView textstatus;

    @BindView(R.id.txtPengajuanGadai)
    TextView txtPengajuanGadai;

    @BindView(R.id.txtPembayaran)
    TextView txtPembayaran;

    @BindView(R.id.status)
    CardView status;

    AdapterRiwayatPengajuan myAdapter;
    AdapterRiwayatPembayaran myAdapter2;
    List<DataGadaiMobile> listAllData = new ArrayList<>();
    List<DataPembayaranDetail2> listAllData2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_riwayat_transaksi);
        ButterKnife.bind(this);

        back.setOnClickListener(this);
        status.setOnClickListener(this);
        pengajuan.setOnClickListener(this);
        pembayaran.setOnClickListener(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                getPengajuan();
            }
        });
        swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                    txtPengajuanGadai.setTextColor(getResources().getColor(R.color.blue_dark_beranda));
                    txtPembayaran.setTextColor(getResources().getColor(R.color.grey_menu_text));
                    getPengajuan();
                }
            }
        );
        swipeRefreshLayout2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout2.setRefreshing(true);
                getPembayaran();
            }
        });
        swipeRefreshLayout2.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout2.setRefreshing(true);
                    getPembayaran();
                }
            }
        );

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new AdapterRiwayatPengajuan(this,recyclerView,listAllData);

        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        myAdapter2 = new AdapterRiwayatPembayaran(this,recyclerView2,listAllData2,Riwayat.this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.back:
                onBackPressed();
                break;

            case R.id.pengajuan:
                layAktif.setVisibility(View.VISIBLE);
                layRiwayat.setVisibility(View.GONE);
                aktifSelected.setVisibility(View.VISIBLE);
                riwayatSelected.setVisibility(View.GONE);
                txtPengajuanGadai.setTextColor(getResources().getColor(R.color.blue_dark_beranda));
                txtPembayaran.setTextColor(getResources().getColor(R.color.grey_menu_text));
                txtPengajuanGadai.setTypeface(txtPengajuanGadai.getTypeface(), Typeface.BOLD);
                txtPembayaran.setTypeface(txtPembayaran.getTypeface(), Typeface.NORMAL);
                status.setVisibility(View.GONE);
                break;

            case R.id.pembayaran:
                layAktif.setVisibility(View.GONE);
                layRiwayat.setVisibility(View.VISIBLE);
                aktifSelected.setVisibility(View.GONE);
                riwayatSelected.setVisibility(View.VISIBLE);
                txtPengajuanGadai.setTextColor(getResources().getColor(R.color.grey_menu_text));
                txtPembayaran.setTextColor(getResources().getColor(R.color.blue_dark_beranda));
                txtPengajuanGadai.setTypeface(txtPengajuanGadai.getTypeface(), Typeface.NORMAL);
                txtPembayaran.setTypeface(txtPembayaran.getTypeface(), Typeface.BOLD);
                status.setVisibility(View.VISIBLE);
                break;

            case R.id.status:
                Intent intent = new Intent(Riwayat.this, GadaiDetail.class);
                intent.putExtra("id", "0");
                intent.putExtra("val", "7");
                startActivityForResult(intent, 8);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 8 && resultCode == RESULT_OK && data != null)
        {
            if(data.getStringExtra("id").equals("1")){
                textstatus.setText("Semua");
                swipeRefreshLayout2.post(new Runnable() {
                         @Override
                         public void run() {
                             swipeRefreshLayout2.setRefreshing(true);
                             getPembayaran();
                         }
                     }
                );
            }else if(data.getStringExtra("id").equals("2")){
                textstatus.setText("Berhasil");
                swipeRefreshLayout2.post(new Runnable() {
                         @Override
                         public void run() {
                             swipeRefreshLayout2.setRefreshing(true);
                             getPembayaranBerhasil();
                         }
                     }
                );
            }else if(data.getStringExtra("id").equals("3")){
                textstatus.setText("Pending");
                swipeRefreshLayout2.post(new Runnable() {
                         @Override
                         public void run() {
                             swipeRefreshLayout2.setRefreshing(true);
                             getPembayaranPending();
                         }
                     }
                );
            }else if(data.getStringExtra("id").equals("4")){
                textstatus.setText("Batal");
                swipeRefreshLayout2.post(new Runnable() {
                                             @Override
                                             public void run() {
                                                 swipeRefreshLayout2.setRefreshing(true);
                                                 getPembayaranBatal();
                                             }
                                         }
                );
            }

        }else{

        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    public void setData(String id){
        dialogBatalkan(id);
    }

    public void getPengajuan()
    {
        listAllData.clear();
        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseGadaiMobile> userCall = service.getGadaiMobile(tokenBearer,"gadai/mobile");
        userCall.enqueue(new Callback<ResponseGadaiMobile>() {
            @Override
            public void onResponse(Call<ResponseGadaiMobile> call, retrofit2.Response<ResponseGadaiMobile> response) {
                swipeRefreshLayout.setRefreshing(false);
                //Log.w("", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful())
                {
                    String myMessage = ""+response.body().getreason();
                    String status = ""+response.body().getstatus();
                    if(status.equals("true"))
                    {
                        try {
                            List<DataGadaiMobile> myList = null;
                            myList = (List<DataGadaiMobile>) response.body().getdata();

                            if(myList.size()==0)
                            {

                            }
                            else
                            {
                                for (int i = 0; i < myList.size(); i++)
                                {
                                    // @M - Add data to model "DataCommisionJob"
                                    DataGadaiMobile dataGadaiMobile = new DataGadaiMobile(
                                            ""+myList.get(i).getid(),
                                            ""+myList.get(i).gettipe(),
                                            ""+myList.get(i).getmerk(),
                                            ""+myList.get(i).getjenisBarang(),
                                            ""+myList.get(i).getwarnaBarang(),
                                            ""+myList.get(i).getnoReferensi(),
                                            ""+myList.get(i).getcreatedAt(),
                                            ""+myList.get(i).gettahun(),
                                            ""+myList.get(i).getestimasiPinjaman()
                                    );
                                    // @M - Add to List
                                    listAllData.add(dataGadaiMobile);
                                }

                                recyclerView.setAdapter(myAdapter);
                                myAdapter.notifyDataSetChanged();
                                myAdapter.setLoaded();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            //Toast.makeText(getApplicationContext(),"catch",Toast.LENGTH_SHORT).show();
                        }
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
            public void onFailure(Call<ResponseGadaiMobile> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                dialogWarning(getResources().getString(R.string.gagalCobaLagi));
            }
        });
    }

    public void getPembayaran()
    {
        listAllData2.clear();
        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponsePembayaran> userCall = service.getPembayaranList(tokenBearer,"pembayaran/?page=0&size=10");
        userCall.enqueue(new Callback<ResponsePembayaran>() {
            @Override
            public void onResponse(Call<ResponsePembayaran> call, retrofit2.Response<ResponsePembayaran> response) {
                swipeRefreshLayout2.setRefreshing(false);
                //Log.w("", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful())
                {
                    String myMessage = ""+response.body().getreason();
                    String status = ""+response.body().getstatus();
                    if(status.equals("true"))
                    {
                        try {
                            List<DataPembayaranDetail> myList = null;
                            myList = (List<DataPembayaranDetail>) response.body().getdata().getdata();

                            if(myList.size()==0)
                            {

                            }
                            else
                            {
                                for (int i = 0; i < myList.size(); i++)
                                {

                                    if (myList.get(i).getconvenienceStore() != null) {
                                        // @M - Add data to model "DataCommisionJob"
                                        DataPembayaranDetail2 dataPembayaranDetail2 = new DataPembayaranDetail2(
                                                ""+myList.get(i).getbank().getbank(),
                                                ""+myList.get(i).getconvenienceStore().getpayment_code(),
                                                ""+myList.get(i).getgadai().getnoFaktur(),
                                                ""+myList.get(i).getbank().getbill_key(),
                                                ""+myList.get(i).getbank().getbiller_code(),
                                                ""+myList.get(i).getbank().getva_number(),
                                                ""+myList.get(i).getcreatedAt(),
                                                ""+myList.get(i).getgadai().getmerk()+""+myList.get(i).getgadai().gettipe(),
                                                ""+myList.get(i).getjumlahHarusBayar(),
                                                ""+myList.get(i).getmetode().getname(),
                                                ""+myList.get(i).getpaidAt(),
                                                ""+myList.get(i).getpembayaranId(),
                                                ""+myList.get(i).getstatus()
                                        );
                                        // @M - Add to List
                                        listAllData2.add(dataPembayaranDetail2);
                                    }else{
                                        // @M - Add data to model "DataCommisionJob"
                                        DataPembayaranDetail2 dataPembayaranDetail2 = new DataPembayaranDetail2(
                                                ""+myList.get(i).getbank().getbank(),
                                                "",
                                                ""+myList.get(i).getgadai().getnoFaktur(),
                                                ""+myList.get(i).getbank().getbill_key(),
                                                ""+myList.get(i).getbank().getbiller_code(),
                                                ""+myList.get(i).getbank().getva_number(),
                                                ""+myList.get(i).getcreatedAt(),
                                                ""+myList.get(i).getgadai().getmerk()+""+myList.get(i).getgadai().gettipe(),
                                                ""+myList.get(i).getjumlahHarusBayar(),
                                                ""+myList.get(i).getmetode().getname(),
                                                ""+myList.get(i).getpaidAt(),
                                                ""+myList.get(i).getpembayaranId(),
                                                ""+myList.get(i).getstatus()
                                        );
                                        // @M - Add to List
                                        listAllData2.add(dataPembayaranDetail2);
                                    }


                                }

                                recyclerView2.setAdapter(myAdapter2);
                                myAdapter2.notifyDataSetChanged();
                                myAdapter2.setLoaded();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            //Toast.makeText(getApplicationContext(),"catch",Toast.LENGTH_SHORT).show();
                        }
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
            public void onFailure(Call<ResponsePembayaran> call, Throwable t) {
                swipeRefreshLayout2.setRefreshing(false);
                dialogWarning(getResources().getString(R.string.gagalCobaLagi));
            }
        });
    }

    public void getPembayaranBerhasil()
    {
        listAllData2.clear();
        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponsePembayaran> userCall = service.getPembayaranList(tokenBearer,"pembayaran/berhasil?page=0&size=10");
        userCall.enqueue(new Callback<ResponsePembayaran>() {
            @Override
            public void onResponse(Call<ResponsePembayaran> call, retrofit2.Response<ResponsePembayaran> response) {
                swipeRefreshLayout2.setRefreshing(false);
                //Log.w("", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful())
                {
                    String myMessage = ""+response.body().getreason();
                    String status = ""+response.body().getstatus();
                    if(status.equals("true"))
                    {
                        try {
                            List<DataPembayaranDetail> myList = null;
                            myList = (List<DataPembayaranDetail>) response.body().getdata().getdata();

                            if(myList.size()==0)
                            {

                            }
                            else
                            {
                                for (int i = 0; i < myList.size(); i++)
                                {

                                    if (myList.get(i).getconvenienceStore() != null) {
                                        // @M - Add data to model "DataCommisionJob"
                                        DataPembayaranDetail2 dataPembayaranDetail2 = new DataPembayaranDetail2(
                                                ""+myList.get(i).getbank().getbank(),
                                                ""+myList.get(i).getconvenienceStore().getpayment_code(),
                                                ""+myList.get(i).getgadai().getnoFaktur(),
                                                ""+myList.get(i).getbank().getbill_key(),
                                                ""+myList.get(i).getbank().getbiller_code(),
                                                ""+myList.get(i).getbank().getva_number(),
                                                ""+myList.get(i).getcreatedAt(),
                                                ""+myList.get(i).getgadai().getmerk()+""+myList.get(i).getgadai().gettipe(),
                                                ""+myList.get(i).getjumlahHarusBayar(),
                                                ""+myList.get(i).getmetode().getname(),
                                                ""+myList.get(i).getpaidAt(),
                                                ""+myList.get(i).getpembayaranId(),
                                                ""+myList.get(i).getstatus()
                                        );
                                        // @M - Add to List
                                        listAllData2.add(dataPembayaranDetail2);
                                    }else{
                                        // @M - Add data to model "DataCommisionJob"
                                        DataPembayaranDetail2 dataPembayaranDetail2 = new DataPembayaranDetail2(
                                                ""+myList.get(i).getbank().getbank(),
                                                "",
                                                ""+myList.get(i).getgadai().getnoFaktur(),
                                                ""+myList.get(i).getbank().getbill_key(),
                                                ""+myList.get(i).getbank().getbiller_code(),
                                                ""+myList.get(i).getbank().getva_number(),
                                                ""+myList.get(i).getcreatedAt(),
                                                ""+myList.get(i).getgadai().getmerk()+""+myList.get(i).getgadai().gettipe(),
                                                ""+myList.get(i).getjumlahHarusBayar(),
                                                ""+myList.get(i).getmetode().getname(),
                                                ""+myList.get(i).getpaidAt(),
                                                ""+myList.get(i).getpembayaranId(),
                                                ""+myList.get(i).getstatus()
                                        );
                                        // @M - Add to List
                                        listAllData2.add(dataPembayaranDetail2);
                                    }
                                }

                                recyclerView2.setAdapter(myAdapter2);
                                myAdapter2.notifyDataSetChanged();
                                myAdapter2.setLoaded();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            //Toast.makeText(getApplicationContext(),"catch",Toast.LENGTH_SHORT).show();
                        }
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
            public void onFailure(Call<ResponsePembayaran> call, Throwable t) {
                swipeRefreshLayout2.setRefreshing(false);
                dialogWarning(getResources().getString(R.string.gagalCobaLagi));
            }
        });
    }

    public void getPembayaranPending()
    {
        listAllData2.clear();
        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponsePembayaran> userCall = service.getPembayaranList(tokenBearer,"pembayaran/pending?page=0&size=10");
        userCall.enqueue(new Callback<ResponsePembayaran>() {
            @Override
            public void onResponse(Call<ResponsePembayaran> call, retrofit2.Response<ResponsePembayaran> response) {
                swipeRefreshLayout2.setRefreshing(false);
                //Log.w("", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful())
                {
                    String myMessage = ""+response.body().getreason();
                    String status = ""+response.body().getstatus();
                    if(status.equals("true"))
                    {
                        try {
                            List<DataPembayaranDetail> myList = null;
                            myList = (List<DataPembayaranDetail>) response.body().getdata().getdata();

                            if(myList.size()==0)
                            {

                            }
                            else
                            {
                                for (int i = 0; i < myList.size(); i++)
                                {

                                    if (myList.get(i).getconvenienceStore() != null) {
                                        // @M - Add data to model "DataCommisionJob"
                                        DataPembayaranDetail2 dataPembayaranDetail2 = new DataPembayaranDetail2(
                                                ""+myList.get(i).getbank().getbank(),
                                                ""+myList.get(i).getconvenienceStore().getpayment_code(),
                                                ""+myList.get(i).getgadai().getnoFaktur(),
                                                ""+myList.get(i).getbank().getbill_key(),
                                                ""+myList.get(i).getbank().getbiller_code(),
                                                ""+myList.get(i).getbank().getva_number(),
                                                ""+myList.get(i).getcreatedAt(),
                                                ""+myList.get(i).getgadai().getmerk()+""+myList.get(i).getgadai().gettipe(),
                                                ""+myList.get(i).getjumlahHarusBayar(),
                                                ""+myList.get(i).getmetode().getname(),
                                                ""+myList.get(i).getpaidAt(),
                                                ""+myList.get(i).getpembayaranId(),
                                                ""+myList.get(i).getstatus()
                                        );
                                        // @M - Add to List
                                        listAllData2.add(dataPembayaranDetail2);
                                    }else{
                                        // @M - Add data to model "DataCommisionJob"
                                        DataPembayaranDetail2 dataPembayaranDetail2 = new DataPembayaranDetail2(
                                                ""+myList.get(i).getbank().getbank(),
                                                "",
                                                ""+myList.get(i).getgadai().getnoFaktur(),
                                                ""+myList.get(i).getbank().getbill_key(),
                                                ""+myList.get(i).getbank().getbiller_code(),
                                                ""+myList.get(i).getbank().getva_number(),
                                                ""+myList.get(i).getcreatedAt(),
                                                ""+myList.get(i).getgadai().getmerk()+""+myList.get(i).getgadai().gettipe(),
                                                ""+myList.get(i).getjumlahHarusBayar(),
                                                ""+myList.get(i).getmetode().getname(),
                                                ""+myList.get(i).getpaidAt(),
                                                ""+myList.get(i).getpembayaranId(),
                                                ""+myList.get(i).getstatus()
                                        );
                                        // @M - Add to List
                                        listAllData2.add(dataPembayaranDetail2);
                                    }
                                }

                                recyclerView2.setAdapter(myAdapter2);
                                myAdapter2.notifyDataSetChanged();
                                myAdapter2.setLoaded();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            //Toast.makeText(getApplicationContext(),"catch",Toast.LENGTH_SHORT).show();
                        }
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
            public void onFailure(Call<ResponsePembayaran> call, Throwable t) {
                swipeRefreshLayout2.setRefreshing(false);
                dialogWarning(getResources().getString(R.string.gagalCobaLagi));
            }
        });
    }

    public void getPembayaranBatal()
    {
        listAllData2.clear();
        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponsePembayaran> userCall = service.getPembayaranList(tokenBearer,"pembayaran/batal?page=0&size=10");
        userCall.enqueue(new Callback<ResponsePembayaran>() {
            @Override
            public void onResponse(Call<ResponsePembayaran> call, retrofit2.Response<ResponsePembayaran> response) {
                swipeRefreshLayout2.setRefreshing(false);
                //Log.w("", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful())
                {
                    String myMessage = ""+response.body().getreason();
                    String status = ""+response.body().getstatus();
                    if(status.equals("true"))
                    {
                        try {
                            List<DataPembayaranDetail> myList = null;
                            myList = (List<DataPembayaranDetail>) response.body().getdata().getdata();

                            if(myList.size()==0)
                            {

                            }
                            else
                            {
                                for (int i = 0; i < myList.size(); i++)
                                {

                                    if (myList.get(i).getconvenienceStore() != null) {
                                        // @M - Add data to model "DataCommisionJob"
                                        DataPembayaranDetail2 dataPembayaranDetail2 = new DataPembayaranDetail2(
                                                ""+myList.get(i).getbank().getbank(),
                                                ""+myList.get(i).getconvenienceStore().getpayment_code(),
                                                ""+myList.get(i).getgadai().getnoFaktur(),
                                                ""+myList.get(i).getbank().getbill_key(),
                                                ""+myList.get(i).getbank().getbiller_code(),
                                                ""+myList.get(i).getbank().getva_number(),
                                                ""+myList.get(i).getcreatedAt(),
                                                ""+myList.get(i).getgadai().getmerk()+""+myList.get(i).getgadai().gettipe(),
                                                ""+myList.get(i).getjumlahHarusBayar(),
                                                ""+myList.get(i).getmetode().getname(),
                                                ""+myList.get(i).getpaidAt(),
                                                ""+myList.get(i).getpembayaranId(),
                                                ""+myList.get(i).getstatus()
                                        );
                                        // @M - Add to List
                                        listAllData2.add(dataPembayaranDetail2);
                                    }else{
                                        // @M - Add data to model "DataCommisionJob"
                                        DataPembayaranDetail2 dataPembayaranDetail2 = new DataPembayaranDetail2(
                                                ""+myList.get(i).getbank().getbank(),
                                                "",
                                                ""+myList.get(i).getgadai().getnoFaktur(),
                                                ""+myList.get(i).getbank().getbill_key(),
                                                ""+myList.get(i).getbank().getbiller_code(),
                                                ""+myList.get(i).getbank().getva_number(),
                                                ""+myList.get(i).getcreatedAt(),
                                                ""+myList.get(i).getgadai().getmerk()+""+myList.get(i).getgadai().gettipe(),
                                                ""+myList.get(i).getjumlahHarusBayar(),
                                                ""+myList.get(i).getmetode().getname(),
                                                ""+myList.get(i).getpaidAt(),
                                                ""+myList.get(i).getpembayaranId(),
                                                ""+myList.get(i).getstatus()
                                        );
                                        // @M - Add to List
                                        listAllData2.add(dataPembayaranDetail2);
                                    }
                                }

                                recyclerView2.setAdapter(myAdapter2);
                                myAdapter2.notifyDataSetChanged();
                                myAdapter2.setLoaded();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            //Toast.makeText(getApplicationContext(),"catch",Toast.LENGTH_SHORT).show();
                        }
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
            public void onFailure(Call<ResponsePembayaran> call, Throwable t) {
                swipeRefreshLayout2.setRefreshing(false);
                dialogWarning(getResources().getString(R.string.gagalCobaLagi));
            }
        });
    }

    public void batalkanPembayaran(String id)
    {
        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseNormal2> userCall = service.batalkanTransaksi(tokenBearer,"pembayaran/cancel/"+id+"");
        userCall.enqueue(new Callback<ResponseNormal2>() {
            @Override
            public void onResponse(Call<ResponseNormal2> call, retrofit2.Response<ResponseNormal2> response) {
                swipeRefreshLayout2.setRefreshing(false);
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
                swipeRefreshLayout2.setRefreshing(false);
                dialogWarning(getResources().getString(R.string.gagalCobaLagi));
            }
        });
    }



    public void dialogBatalkan(String id)
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
                batalkanPembayaran(id);
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
                swipeRefreshLayout2.post(new Runnable() {
                         @Override
                         public void run() {
                             swipeRefreshLayout2.setRefreshing(true);
                             getPembayaran();
                         }
                     }
                );
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
