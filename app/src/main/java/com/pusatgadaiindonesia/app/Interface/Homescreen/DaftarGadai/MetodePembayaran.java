package com.pusatgadaiindonesia.app.Interface.Homescreen.DaftarGadai;

import android.app.Dialog;
import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.pusatgadaiindonesia.app.Adapter.AdapterInquiryPembayaran;
import com.pusatgadaiindonesia.app.Adapter.AdapterMetodePembayaran;
import com.pusatgadaiindonesia.app.Database.Data_Profile;
import com.pusatgadaiindonesia.app.Model.Inquiry.DataItemsDetail;
import com.pusatgadaiindonesia.app.Model.Inquiry.ResponseInquiry;
import com.pusatgadaiindonesia.app.Model.MetodeBayar.DataMetodeBayar;
import com.pusatgadaiindonesia.app.Model.MetodeBayar.DataPerpanjangan;
import com.pusatgadaiindonesia.app.Model.MetodeBayar.ResponseMetodeBayar;
import com.pusatgadaiindonesia.app.Model.MetodeBayar.ResponsePerpanjangan;
import com.pusatgadaiindonesia.app.Model.MetodeBayar.ResponsePerpanjanganConvenience;
import com.pusatgadaiindonesia.app.Model.MetodeBayar.SendBayar;
import com.pusatgadaiindonesia.app.Model.Percentage.SendPercentage;
import com.pusatgadaiindonesia.app.R;
import com.pusatgadaiindonesia.app.Services.Retrofit.APIService;
import com.pusatgadaiindonesia.app.Services.Retrofit.ApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class MetodePembayaran extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.rv)
    RecyclerView recyclerView;

    @BindView(R.id.refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.txtbiayajasa)
    TextView txtbiayajasa;

    @BindView(R.id.txtangsuranpokok)
    TextView txtangsuranpokok;

    @BindView(R.id.txttotalbayar)
    TextView txttotalbayar;

    AdapterMetodePembayaran myAdapter;
    String getBiayaJasa="", getAngsuranPokok="", getTotalBayar="", getBulanBerikutnya="", getGadaiId="";
    String selectedBank="", selectedBillKey="", selectedBillerCode="", selectedPembayaranId="", selectedVaNumber="";
    String selectedName="", selectedPaymentCode="", selectedProductCode="";
    List<DataMetodeBayar> listAllData = new ArrayList<>();

    Dialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_metode_pembayaran);
        ButterKnife.bind(this);

        back.setOnClickListener(this);

        getGadaiId = ""+getIntent().getStringExtra("sendGadaiId");
        getBiayaJasa = ""+getIntent().getStringExtra("sendBiayaJasa");
        getAngsuranPokok = ""+getIntent().getStringExtra("sendAngsuranPokok");
        getTotalBayar = ""+getIntent().getStringExtra("sendTotalBayar");
        getBulanBerikutnya = ""+getIntent().getStringExtra("sendBulanBerikutnya");

        txtbiayajasa.setText("Rp. "+currencyFormat(getBiayaJasa));
        txtangsuranpokok.setText("Rp. "+currencyFormat(getAngsuranPokok));
        txttotalbayar.setText("Rp. "+currencyFormat(getTotalBayar));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                getMetodeBayar();
            }
        });
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        getMetodeBayar();
                                    }
                                }
        );

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new AdapterMetodePembayaran(this,recyclerView,listAllData,this);

    }

    public static String currencyFormat(String amount) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0");
        return formatter.format(Double.parseDouble(amount));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 8 && resultCode == RESULT_OK) {
            finish();
        }
    }

    public void setConfirmBayar(String id){
        if (id.equals("6") || id.equals("7")){
            perpanjanganBayarConvenienceStore(id);
        }else{
            perpanjanganBayarBank(id);
        }

    }

    public void perpanjanganBayarBank(String id){
        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        SendBayar sendBayar = new SendBayar(""+getAngsuranPokok,""+getBulanBerikutnya,""+id);
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponsePerpanjangan> userCall = service.bayarBank(tokenBearer,sendBayar,"pembayaran/"+getGadaiId+"/perpanjangan/bayar/bank");
        userCall.enqueue(new Callback<ResponsePerpanjangan>() {
            @Override
            public void onResponse(Call<ResponsePerpanjangan> call, retrofit2.Response<ResponsePerpanjangan> response) {
                swipeRefreshLayout.setRefreshing(false);
                //Log.w("", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful())
                {
                    String myMessage = ""+response.body().getreason();
                    String status = ""+response.body().getstatus();
                    if(status.equals("true"))
                    {
                        selectedBank = ""+response.body().getdata().getbank();
                        selectedBillKey = ""+response.body().getdata().getbill_key();
                        selectedBillerCode = ""+response.body().getdata().getbiller_code();
                        selectedPembayaranId = ""+response.body().getdata().getpembayaranId();
                        selectedVaNumber = ""+response.body().getdata().getva_number();
                        Intent intent = new Intent(getApplicationContext(), ConfirmPembayaran.class);
                        intent.putExtra("sendBank",""+selectedBank);
                        intent.putExtra("sendBillKey",""+selectedBillKey);
                        intent.putExtra("sendBillerCode",""+selectedBillerCode);
                        intent.putExtra("sendPembayaranId",""+selectedPembayaranId);
                        intent.putExtra("sendVaNumber",""+selectedVaNumber);
                        intent.putExtra("sendTotalBayar",""+getTotalBayar);
                        intent.putExtra("sendAngsuranPokok",""+getAngsuranPokok);
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
            public void onFailure(Call<ResponsePerpanjangan> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                dialogWarning(getResources().getString(R.string.gagalCobaLagi));
            }
        });
    }

    public void perpanjanganBayarConvenienceStore(String id){
        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        SendBayar sendBayar = new SendBayar(""+getAngsuranPokok,""+getBulanBerikutnya,""+id);
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponsePerpanjanganConvenience> userCall = service.bayarConvenienceStore(tokenBearer,sendBayar,"pembayaran/"+getGadaiId+"/perpanjangan/bayar/convenience_store");
        userCall.enqueue(new Callback<ResponsePerpanjanganConvenience>() {
            @Override
            public void onResponse(Call<ResponsePerpanjanganConvenience> call, retrofit2.Response<ResponsePerpanjanganConvenience> response) {
                swipeRefreshLayout.setRefreshing(false);
                //Log.w("", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful())
                {
                    String myMessage = ""+response.body().getreason();
                    String status = ""+response.body().getstatus();
                    if(status.equals("true"))
                    {
                        selectedName = ""+response.body().getdata().getname();
                        selectedPaymentCode = ""+response.body().getdata().getpayment_code();
                        selectedPembayaranId = ""+response.body().getdata().getpembayaranId();
                        selectedProductCode = ""+response.body().getdata().getproduct_code();
                        Intent intent = new Intent(getApplicationContext(), ConfirmPembayaranConvenience.class);
                        intent.putExtra("sendName",""+selectedName);
                        intent.putExtra("sendPaymentCode",""+selectedPaymentCode);
                        intent.putExtra("sendPembayaranId",""+selectedPembayaranId);
                        intent.putExtra("sendProductCode",""+selectedProductCode);
                        intent.putExtra("sendTotalBayar",""+getAngsuranPokok);
                        intent.putExtra("sendAngsuranPokok",""+getTotalBayar);
                        startActivity(intent);
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
            public void onFailure(Call<ResponsePerpanjanganConvenience> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                dialogWarning(getResources().getString(R.string.gagalCobaLagi));
            }
        });
    }


    public void getMetodeBayar()
    {
        listAllData.clear();
        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseMetodeBayar> userCall = service.getMetodePembayaran(tokenBearer,"pembayaran/metode");
        userCall.enqueue(new Callback<ResponseMetodeBayar>() {
            @Override
            public void onResponse(Call<ResponseMetodeBayar> call, retrofit2.Response<ResponseMetodeBayar> response) {
                swipeRefreshLayout.setRefreshing(false);
                //Log.w("", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful())
                {
                    String myMessage = ""+response.body().getreason();
                    String status = ""+response.body().getstatus();
                    if(status.equals("true"))
                    {
                        try {
                            List<DataMetodeBayar> myList = null;
                            myList = (List<DataMetodeBayar>) response.body().getdata();

                            if(myList.size()==0)
                            {

                            }
                            else
                            {
                                for (int i = 0; i < myList.size(); i++)
                                {
                                    // @M - Add data to model "DataCommisionJob"
                                    DataMetodeBayar dataMetodeBayar = new DataMetodeBayar(
                                            ""+myList.get(i).getid(),
                                            ""+myList.get(i).getlogo(),
                                            ""+myList.get(i).getname(),
                                            ""+myList.get(i).gettype()
                                    );
                                    // @M - Add to List
                                    listAllData.add(dataMetodeBayar);
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
            public void onFailure(Call<ResponseMetodeBayar> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                dialogWarning(getResources().getString(R.string.gagalCobaLagi));
            }
        });
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

}
