package com.pusatgadaiindonesia.app.Interface.Homescreen.DaftarGadai;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.pusatgadaiindonesia.app.Adapter.AdapterMetodePembayaran;
import com.pusatgadaiindonesia.app.Adapter.AdapterPerangkatBayar;
import com.pusatgadaiindonesia.app.Database.Data_Profile;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Homescreen;
import com.pusatgadaiindonesia.app.Model.Instruksi.DataInstruksiDetail;
import com.pusatgadaiindonesia.app.Model.Instruksi.ResponseInstruksi;
import com.pusatgadaiindonesia.app.Model.MetodeBayar.DataMetodeBayar;
import com.pusatgadaiindonesia.app.Model.MetodeBayar.ResponseMetodeBayar;
import com.pusatgadaiindonesia.app.Model.Notification.DataNotificationDetail;
import com.pusatgadaiindonesia.app.Model.PerangkatBayar.DataPerangkat;
import com.pusatgadaiindonesia.app.Model.PerangkatBayar.ResponsePerangkatBayar;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class ConfirmPembayaran extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.rv)
    RecyclerView recyclerView;

    @BindView(R.id.rv2)
    RecyclerView recyclerView2;

    @BindView(R.id.rv3)
    RecyclerView recyclerView3;

    @BindView(R.id.refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.refresh2)
    SwipeRefreshLayout swipeRefreshLayout2;

    @BindView(R.id.refresh3)
    SwipeRefreshLayout swipeRefreshLayout3;

    @BindView(R.id.layUtama)
    LinearLayout layUtama;

    @BindView(R.id.layBill)
    LinearLayout layBill;

    @BindView(R.id.atm)
    RelativeLayout atm;

    @BindView(R.id.Internet)
    RelativeLayout Internet;

    @BindView(R.id.Mbanking)
    RelativeLayout Mbanking;

    @BindView(R.id.layAtm)
    LinearLayout layAtm;

    @BindView(R.id.layInternet)
    LinearLayout layInternet;

    @BindView(R.id.laymbanking)
    LinearLayout laymbanking;

    @BindView(R.id.LaySelesai)
    LinearLayout LaySelesai;

    @BindView(R.id.atmSelected)
    LinearLayout atmSelected;

    @BindView(R.id.internetSelected)
    LinearLayout internetSelected;

    @BindView(R.id.mbankSelected)
    LinearLayout mbankSelected;

    @BindView(R.id.txtVirtualAccount)
    TextView txtVirtualAccount;

    @BindView(R.id.txtBillKey)
    TextView txtBillKey;

    @BindView(R.id.txtBillerCode)
    TextView txtBillerCode;

    @BindView(R.id.txtBank)
    TextView txtBank;

    @BindView(R.id.textTotal)
    TextView textTotal;

    @BindView(R.id.txtBatasAkhir)
    TextView txtBatasAkhir;

    @BindView(R.id.txtTime)
    TextView txtTime;

    @BindView(R.id.salinVa)
    TextView salinVa;

    @BindView(R.id.salinBillerCode)
    TextView salinBillerCode;

    @BindView(R.id.salinBillKey)
    TextView salinBillKey;

    AdapterPerangkatBayar myAdapter;
    AdapterPerangkatBayar myAdapter2;
    AdapterPerangkatBayar myAdapter3;
    String getBank="", getBillKey="", getBillerCode="", getPembayaranId="", getVaNumber="", getTotalBayar="", getAngsuranPokok="";
    List<DataInstruksiDetail> listAllData = new ArrayList<>();
    List<DataInstruksiDetail> listAllData2 = new ArrayList<>();
    List<DataInstruksiDetail> listAllData3 = new ArrayList<>();
    private String EVENT_DATE_TIME = "23:00:00";
    private String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private Handler handler = new Handler();
    private Runnable runnable;
    Dialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_confirm_pembayaran);
        ButterKnife.bind(this);

        atm.setOnClickListener(this);
        Internet.setOnClickListener(this);
        Mbanking.setOnClickListener(this);
        LaySelesai.setOnClickListener(this);

        getBank = ""+getIntent().getStringExtra("sendBank");
        getBillKey = ""+getIntent().getStringExtra("sendBillKey");
        getBillerCode = ""+getIntent().getStringExtra("sendBillerCode");
        getPembayaranId = ""+getIntent().getStringExtra("sendPembayaranId");
        getVaNumber = ""+getIntent().getStringExtra("sendVaNumber");
        getTotalBayar = ""+getIntent().getStringExtra("sendTotalBayar");
        getAngsuranPokok = ""+getIntent().getStringExtra("sendAngsuranPokok");

        SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy");
        Date date = new Date();

        textTotal.setText("Rp. "+currencyFormat(getTotalBayar));
        txtBatasAkhir.setText(""+formatter.format(date)+" 23:00 WIB");

        if (getBillKey.equals("") || getBillKey.equals("null")){
            layBill.setVisibility(View.GONE);
            layUtama.setVisibility(View.VISIBLE);

            txtVirtualAccount.setText(""+getVaNumber);
            txtBank.setText(""+getBank);
        }else{
            layBill.setVisibility(View.VISIBLE);
            layUtama.setVisibility(View.GONE);

            txtBillKey.setText(""+getBillKey);
            txtBillerCode.setText(""+getBillerCode);
            txtBank.setText(""+getBank);
        }

        getPerangkatBayar();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                getPerangkatBayar();
            }
        });



        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new AdapterPerangkatBayar(this,recyclerView,listAllData);

        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        myAdapter2 = new AdapterPerangkatBayar(this,recyclerView2,listAllData2);

        recyclerView3.setLayoutManager(new LinearLayoutManager(this));
        myAdapter3 = new AdapterPerangkatBayar(this,recyclerView3,listAllData3);

        salinVa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                Toast.makeText(getApplicationContext(), "Copied !", Toast.LENGTH_SHORT).show();
            }
        });

        salinBillerCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                Toast.makeText(getApplicationContext(), "Copied !", Toast.LENGTH_SHORT).show();
            }
        });

        salinBillKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                Toast.makeText(getApplicationContext(), "Copied !", Toast.LENGTH_SHORT).show();
            }
        });

        txtVirtualAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                Toast.makeText(getApplicationContext(), "Copied !", Toast.LENGTH_SHORT).show();
            }
        });

        txtBillerCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                Toast.makeText(getApplicationContext(), "Copied !", Toast.LENGTH_SHORT).show();
            }
        });

        txtBillKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                Toast.makeText(getApplicationContext(), "Copied !", Toast.LENGTH_SHORT).show();
            }
        });

        countDownStart();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {

            case R.id.atm:
                layAtm.setVisibility(View.VISIBLE);
                layInternet.setVisibility(View.GONE);
                laymbanking.setVisibility(View.GONE);

                atmSelected.setVisibility(View.VISIBLE);
                internetSelected.setVisibility(View.GONE);
                mbankSelected.setVisibility(View.GONE);
                break;

            case R.id.Internet:
                layAtm.setVisibility(View.GONE);
                layInternet.setVisibility(View.VISIBLE);
                laymbanking.setVisibility(View.GONE);

                atmSelected.setVisibility(View.GONE);
                internetSelected.setVisibility(View.VISIBLE);
                mbankSelected.setVisibility(View.GONE);
                break;

            case R.id.Mbanking:
                layAtm.setVisibility(View.GONE);
                layInternet.setVisibility(View.GONE);
                laymbanking.setVisibility(View.VISIBLE);

                atmSelected.setVisibility(View.GONE);
                internetSelected.setVisibility(View.GONE);
                mbankSelected.setVisibility(View.VISIBLE);
                break;

            case R.id.LaySelesai:
                dialogLoading();
                loading.dismiss();
                Intent intent = new Intent(getApplicationContext(), Homescreen.class);
                startActivity(intent);
                break;
        }
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

    private void countDownStart() {
        runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    handler.postDelayed(this, 1000);
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

                    Date current_date = new Date();
                    Date event_date = dateFormat.parse(""+formatter.format(current_date)+" 23:00:00");

                    if (!current_date.after(event_date)) {
                        long diff = event_date.getTime() - current_date.getTime();
                        long Days = diff / (24 * 60 * 60 * 1000);
                        long Hours = diff / (60 * 60 * 1000) % 24;
                        long Minutes = diff / (60 * 1000) % 60;
                        long Seconds = diff / 1000 % 60;
                        //
//                        tv_days.setText(String.format("%02d", Days));
//                        tv_hour.setText(String.format("%02d", Hours));
//                        tv_minute.setText(String.format("%02d", Minutes));
//                        tv_second.setText(String.format("%02d", Seconds));

                        txtTime.setText(""+String.format("%02d", Hours)+":"+String.format("%02d", Minutes)+":"+String.format("%02d", Seconds));
                    } else {

                        handler.removeCallbacks(runnable);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }

    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
    }

    public static String currencyFormat(String amount) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0");
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

    @Override
    public void onBackPressed() {
        dialogBatalkan();
    }


    public void getPerangkatBayar()
    {

        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponsePerangkatBayar> userCall = service.getInstruksiList(tokenBearer,"pembayaran/instruksi/"+getPembayaranId+"/list");
        userCall.enqueue(new Callback<ResponsePerangkatBayar>() {
            @Override
            public void onResponse(Call<ResponsePerangkatBayar> call, retrofit2.Response<ResponsePerangkatBayar> response) {
                swipeRefreshLayout.setRefreshing(false);
                //Log.w("", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful())
                {
                    String myMessage = ""+response.body().getreason();
                    String status = ""+response.body().getstatus();
                    if(status.equals("true"))
                    {
                        try {
                            List<DataPerangkat> myList = null;
                            myList = (List<DataPerangkat>) response.body().getdata();

                            if(myList.size()==0)
                            {

                            }
                            else
                            {
                                String one = myList.get(0).getid();
                                String two = myList.get(1).getid();
                                String three = myList.get(2).getid();

                                if(myList.size() == 1){

                                    swipeRefreshLayout.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                swipeRefreshLayout.setRefreshing(true);
                                                getAtm(one);
                                            }
                                        }
                                    );

                                    Log.w("con :","1");
                                }else if(myList.size() == 2){

                                    swipeRefreshLayout.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                swipeRefreshLayout.setRefreshing(true);
                                                getAtm(one);
                                            }
                                        }
                                    );

                                    swipeRefreshLayout2.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                swipeRefreshLayout2.setRefreshing(true);
                                                getInternet(two);
                                            }
                                        }
                                    );

                                    Log.w("con :","2");
                                }else if(myList.size() == 3){

                                    swipeRefreshLayout.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                swipeRefreshLayout.setRefreshing(true);
                                                getAtm(one);
                                            }
                                        }
                                    );

                                    swipeRefreshLayout2.post(new Runnable() {
                                             @Override
                                             public void run() {
                                                 swipeRefreshLayout2.setRefreshing(true);
                                                 getInternet(two);
                                             }
                                         }
                                    );

                                    swipeRefreshLayout3.post(new Runnable() {
                                             @Override
                                             public void run() {
                                                 swipeRefreshLayout3.setRefreshing(true);
                                                 getMbanking(three);
                                             }
                                         }
                                    );

                                    Log.w("con :","3");
                                }else{
                                    atm.setVisibility(View.GONE);
                                    Internet.setVisibility(View.GONE);
                                    Mbanking.setVisibility(View.GONE);
                                }

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
            public void onFailure(Call<ResponsePerangkatBayar> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                dialogWarning(getResources().getString(R.string.gagalCobaLagi));
            }
        });
    }

    public void getAtm(String id)
    {
        listAllData.clear();
        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseInstruksi> userCall = service.getInstruksiDetail(tokenBearer,"pembayaran/instruksi/"+id+"/detail");
        userCall.enqueue(new Callback<ResponseInstruksi>() {
            @Override
            public void onResponse(Call<ResponseInstruksi> call, retrofit2.Response<ResponseInstruksi> response) {
                swipeRefreshLayout.setRefreshing(false);
                //Log.w("", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful())
                {
                    String myMessage = ""+response.body().getreason();
                    String status = ""+response.body().getstatus();
                    if(status.equals("true"))
                    {
                        try {
                            List<DataInstruksiDetail> myList = null;
                            myList = (List<DataInstruksiDetail>) response.body().getdata().getinstruction();

                            if(myList.size()==0)
                            {

                            }
                            else
                            {
                                for (int i = 0; i < myList.size(); i++)
                                {
                                    // @M - Add data to model "DataCommisionJob"
                                    DataInstruksiDetail dataInstruksiDetail = new DataInstruksiDetail(
                                            ""+myList.get(i).getsequence(),
                                            ""+myList.get(i).getcontent(),
                                            ""+myList.get(i).getimagePath()
                                    );
                                    // @M - Add to List
                                    listAllData.add(dataInstruksiDetail);
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
            public void onFailure(Call<ResponseInstruksi> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                dialogWarning(getResources().getString(R.string.gagalCobaLagi));
            }
        });
    }

    public void getInternet(String id)
    {
        listAllData2.clear();
        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseInstruksi> userCall = service.getInstruksiDetail(tokenBearer,"pembayaran/instruksi/"+id+"/detail");
        userCall.enqueue(new Callback<ResponseInstruksi>() {
            @Override
            public void onResponse(Call<ResponseInstruksi> call, retrofit2.Response<ResponseInstruksi> response) {
                swipeRefreshLayout2.setRefreshing(false);
                //Log.w("", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful())
                {
                    String myMessage = ""+response.body().getreason();
                    String status = ""+response.body().getstatus();
                    if(status.equals("true"))
                    {
                        try {
                            List<DataInstruksiDetail> myList = null;
                            myList = (List<DataInstruksiDetail>) response.body().getdata().getinstruction();

                            if(myList.size()==0)
                            {

                            }
                            else
                            {
                                for (int i = 0; i < myList.size(); i++)
                                {
                                    // @M - Add data to model "DataCommisionJob"
                                    DataInstruksiDetail dataInstruksiDetail = new DataInstruksiDetail(
                                            ""+myList.get(i).getsequence(),
                                            ""+myList.get(i).getcontent(),
                                            ""+myList.get(i).getimagePath()
                                    );
                                    // @M - Add to List
                                    listAllData2.add(dataInstruksiDetail);
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
            public void onFailure(Call<ResponseInstruksi> call, Throwable t) {
                swipeRefreshLayout2.setRefreshing(false);
                dialogWarning(getResources().getString(R.string.gagalCobaLagi));
            }
        });
    }

    public void getMbanking(String id)
    {
        listAllData3.clear();
        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseInstruksi> userCall = service.getInstruksiDetail(tokenBearer,"pembayaran/instruksi/"+id+"/detail");
        userCall.enqueue(new Callback<ResponseInstruksi>() {
            @Override
            public void onResponse(Call<ResponseInstruksi> call, retrofit2.Response<ResponseInstruksi> response) {
                swipeRefreshLayout3.setRefreshing(false);
                //Log.w("", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful())
                {
                    String myMessage = ""+response.body().getreason();
                    String status = ""+response.body().getstatus();
                    if(status.equals("true"))
                    {
                        try {
                            List<DataInstruksiDetail> myList = null;
                            myList = (List<DataInstruksiDetail>) response.body().getdata().getinstruction();

                            if(myList.size()==0)
                            {

                            }
                            else
                            {
                                for (int i = 0; i < myList.size(); i++)
                                {
                                    // @M - Add data to model "DataCommisionJob"
                                    DataInstruksiDetail dataInstruksiDetail = new DataInstruksiDetail(
                                            ""+myList.get(i).getsequence(),
                                            ""+myList.get(i).getcontent(),
                                            ""+myList.get(i).getimagePath()
                                    );
                                    // @M - Add to List
                                    listAllData3.add(dataInstruksiDetail);
                                }

                                recyclerView3.setAdapter(myAdapter3);
                                myAdapter3.notifyDataSetChanged();
                                myAdapter3.setLoaded();
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
            public void onFailure(Call<ResponseInstruksi> call, Throwable t) {
                swipeRefreshLayout3.setRefreshing(false);
                dialogWarning(getResources().getString(R.string.gagalCobaLagi));
            }
        });
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
            }
        });
        dialog.show();
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
                swipeRefreshLayout2.setRefreshing(false);
                //Log.w("", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful())
                {
                    String myMessage = ""+response.body().getreason();
                    String status = ""+response.body().getstatus();
                    if(status.equals("true"))
                    {
                        Intent intent = new Intent();
                        setResult(Activity.RESULT_OK,intent);
                        finish();
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


}
