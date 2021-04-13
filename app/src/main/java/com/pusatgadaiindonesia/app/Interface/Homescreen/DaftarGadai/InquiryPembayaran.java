package com.pusatgadaiindonesia.app.Interface.Homescreen.DaftarGadai;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.pusatgadaiindonesia.app.Adapter.AdapterInquiryPembayaran;
import com.pusatgadaiindonesia.app.Adapter.AdapterNotifikasi;
import com.pusatgadaiindonesia.app.Database.Data_Profile;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda.GadaiDetail;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda.GadaiElektronik3;
import com.pusatgadaiindonesia.app.Model.Inquiry.DataItemsDetail;
import com.pusatgadaiindonesia.app.Model.Inquiry.ResponseInquiry;
import com.pusatgadaiindonesia.app.Model.MetodeBayar.SendBayar;
import com.pusatgadaiindonesia.app.Model.Notification.DataNotificationDetail;
import com.pusatgadaiindonesia.app.Model.Notification.ResponseNotification;
import com.pusatgadaiindonesia.app.Model.PerangkatBayar.DataPerangkat;
import com.pusatgadaiindonesia.app.Model.PerangkatBayar.ResponsePerangkatBayar;
import com.pusatgadaiindonesia.app.Model.ResponseNormal2;
import com.pusatgadaiindonesia.app.R;
import com.pusatgadaiindonesia.app.Services.Retrofit.APIService;
import com.pusatgadaiindonesia.app.Services.Retrofit.ApiClient;
import com.pusatgadaiindonesia.app.Utils.NumberTextWatcher;

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

public class InquiryPembayaran extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.rv)
    RecyclerView recyclerView;

    @BindView(R.id.refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.parent)
    LinearLayout parent;

    @BindView(R.id.LinearLayout4)
    LinearLayout LinearLayout4;

    @BindView(R.id.note)
    EditText note;

    @BindView(R.id.txtNoFaktur)
    TextView txtNoFaktur;

    @BindView(R.id.txtJenis)
    TextView txtJenis;

    @BindView(R.id.txtNominal)
    TextView txtNominal;

    @BindView(R.id.txtJatuhTempo)
    TextView txtJatuhTempo;

    @BindView(R.id.txtTotal)
    TextView txtTotal;

    @BindView(R.id.txtTglJatuhTempo)
    TextView txtTglJatuhTempo;

    @BindView(R.id.nama)
    TextView txtNamaBlnBerikut;

    @BindView(R.id.harga)
    TextView txtHargaBlnBerikut;

    @BindView(R.id.pay_next_month)
    CheckBox pay_next_month;

    @BindView(R.id.angsuran_pokok)
    CheckBox angsuran_pokok;

    @BindView(R.id.cari)
    CardView cari;

    Dialog loading;
    AdapterInquiryPembayaran myAdapter;
    String getId="", getNoFaktur="", getJenis="", getNominal="", getJatuhTempo="";
    List<DataItemsDetail> listAllData = new ArrayList<>();
    Boolean selectedPayNextMonth = false, selectedAngPok = false;
    int totalAllBayar = 0, getTotalBayar = 0, getAngsuranPokok = 0, getTotalSelected = 0, getJasaSelected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_inquiry_pembayaran);
        ButterKnife.bind(this);

        back.setOnClickListener(this);
        cari.setOnClickListener(this);

        getId = ""+getIntent().getStringExtra("sendId");
        getNoFaktur = ""+getIntent().getStringExtra("sendNoFaktur");
        getJenis = ""+getIntent().getStringExtra("sendJenis");
        getNominal = ""+getIntent().getStringExtra("sendNominal");
        getJatuhTempo = ""+getIntent().getStringExtra("sendJatuhTempo");

        txtNoFaktur.setText(": "+getNoFaktur);
        txtJenis.setText(": "+getJenis);
        txtNominal.setText(": Rp. "+currencyFormat(getNominal));
        txtJatuhTempo.setText(": "+changeDate(getJatuhTempo));
        pay_next_month.setTypeface(ResourcesCompat.getFont(this, R.font.century_gothic_bold));
        angsuran_pokok.setTypeface(ResourcesCompat.getFont(this, R.font.century_gothic_bold));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                getInquiry();
            }
        });
        swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                    getInquiry();
                }
            }
        );

        pay_next_month.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    // perform logic
                    getTotalBayar = 0;
                    selectedPayNextMonth = true;
                    parent.setVisibility(View.VISIBLE);
                    getTotalBayar = getTotalSelected + getJasaSelected;
                    txtTotal.setText(": Rp. "+currencyFormat(String.valueOf(getTotalBayar)));
                }else{
                    getTotalBayar = 0;
                    selectedPayNextMonth = false;
                    parent.setVisibility(View.GONE);
                    getTotalBayar = getTotalSelected;
                    txtTotal.setText(": Rp. "+currencyFormat(String.valueOf(getTotalBayar)));
                }

            }
        });

        angsuran_pokok.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    // perform logic
                    selectedAngPok = true;
                    LinearLayout4.setVisibility(View.VISIBLE);
                }else{
                    selectedAngPok = false;
                    LinearLayout4.setVisibility(View.GONE);
                    note.setText("");
                }

            }
        });

        Locale locale = new Locale("in", "ID"); // For example Argentina
        int numDecs = 2; // Let's use 2 decimals
        TextWatcher tw = new NumberTextWatcher(note, locale, numDecs);
        note.addTextChangedListener(tw);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new AdapterInquiryPembayaran(this,recyclerView,listAllData);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.back:
                onBackPressed();
                break;

            case R.id.cari:
                checkData();
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

    public void checkData()
    {
        dialogLoading();
        String angpoks = note.getText().toString().replaceAll("\\.","");
        if(angpoks.equals(""))
        {
            getAngsuranPokok = 0;
        }else{
            getAngsuranPokok = Integer.parseInt(angpoks);
        }
        totalAllBayar = getTotalBayar + getAngsuranPokok;

        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        SendBayar sendBayar = new SendBayar(""+getAngsuranPokok,""+selectedPayNextMonth,""+0);
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseNormal2> userCall = service.checkPerpanjangan(tokenBearer,"/pembayaran/"+getId+"/perpanjangan/bayar/check", sendBayar);
        userCall.enqueue(new Callback<ResponseNormal2>() {
            @Override
            public void onResponse(Call<ResponseNormal2> call, retrofit2.Response<ResponseNormal2> response) {
                swipeRefreshLayout.setRefreshing(false);
                loading.dismiss();
                //Log.w("", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful())
                {
                    String myMessage = ""+response.body().getreason();
                    String status = ""+response.body().getstatus();
                    if(status.equals("true"))
                    {
                        Intent intent = new Intent(getApplicationContext(), MetodePembayaran.class);
                        intent.putExtra("sendGadaiId",""+getId);
                        intent.putExtra("sendBiayaJasa",""+getTotalBayar);
                        intent.putExtra("sendAngsuranPokok",""+getAngsuranPokok);
                        intent.putExtra("sendTotalBayar",""+totalAllBayar);
                        intent.putExtra("sendBulanBerikutnya",""+selectedPayNextMonth);
                        startActivityForResult(intent, 8);
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
                swipeRefreshLayout.setRefreshing(false);
                dialogWarning(getResources().getString(R.string.gagalCobaLagi));
            }
        });
    }



    public void getInquiry()
    {
        listAllData.clear();
        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseInquiry> userCall = service.getInquiry(tokenBearer,"pembayaran/"+getId+"/perpanjangan/inquiry");
        userCall.enqueue(new Callback<ResponseInquiry>() {
            @Override
            public void onResponse(Call<ResponseInquiry> call, retrofit2.Response<ResponseInquiry> response) {
                swipeRefreshLayout.setRefreshing(false);
                //Log.w("", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful())
                {
                    String myMessage = ""+response.body().getreason();
                    String status = ""+response.body().getstatus();
                    if(status.equals("true"))
                    {
                        try {
                            List<DataItemsDetail> myList = null;
                            myList = (List<DataItemsDetail>) response.body().getdata().getitems();

                            if(myList.size()==0)
                            {

                            }
                            else
                            {
                                for (int i = 0; i < myList.size(); i++)
                                {
                                    // @M - Add data to model "DataCommisionJob"
                                    DataItemsDetail dataItemsDetail = new DataItemsDetail(
                                            ""+myList.get(i).getname(),
                                            ""+myList.get(i).getnominal(),
                                            ""+myList.get(i).getjenis(),
                                            ""+myList.get(i).getstartDate(),
                                            ""+myList.get(i).getendDate()
                                    );
                                    // @M - Add to List
                                    listAllData.add(dataItemsDetail);
                                }

                                if(response.body().getdata().getjasaBulanBerikutnya() != null){
                                    txtNamaBlnBerikut.setText(""+response.body().getdata().getjasaBulanBerikutnya().getname());
                                    txtHargaBlnBerikut.setText("Rp. "+currencyFormat(response.body().getdata().getjasaBulanBerikutnya().getnominal()));
                                    pay_next_month.setVisibility(View.VISIBLE);
                                    getTotalSelected = Integer.parseInt(response.body().getdata().gettotal());
                                    getJasaSelected = Integer.parseInt(response.body().getdata().getjasaBulanBerikutnya().getnominal());

                                }else{

                                }

                                txtTotal.setText(": Rp. "+currencyFormat(response.body().getdata().gettotal()));
                                txtTglJatuhTempo.setText(": "+changeDate(response.body().getdata().getjatuhTempoSelanjutnya()));
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
            public void onFailure(Call<ResponseInquiry> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                dialogWarning(getResources().getString(R.string.gagalCobaLagi));
            }
        });
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
