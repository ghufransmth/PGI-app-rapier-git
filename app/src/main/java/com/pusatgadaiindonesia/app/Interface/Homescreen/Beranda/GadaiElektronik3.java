package com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.pusatgadaiindonesia.app.Adapter.AdapterNotifikasi;
import com.pusatgadaiindonesia.app.Database.Data_Profile;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Homescreen;
import com.pusatgadaiindonesia.app.Model.BayarListrik.SendListrik;
import com.pusatgadaiindonesia.app.Model.Gadai.SendGadaiProcess;
import com.pusatgadaiindonesia.app.Model.Notification.DataNotificationDetail;
import com.pusatgadaiindonesia.app.Model.Notification.ResponseNotification;
import com.pusatgadaiindonesia.app.Model.ResponseNormal2;
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

public class GadaiElektronik3 extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.jenis)
    LinearLayout jenis;

    @BindView(R.id.merk)
    LinearLayout merk;

    @BindView(R.id.tipe)
    LinearLayout tipe;

    @BindView(R.id.warna)
    LinearLayout warna;

    @BindView(R.id.tahun)
    LinearLayout tahun;

    @BindView(R.id.textjenis)
    TextView textjenis;

    @BindView(R.id.textMerk)
    TextView textMerk;

    @BindView(R.id.textTipe)
    TextView textTipe;

    @BindView(R.id.textWarna)
    TextView textWarna;

    @BindView(R.id.textTahun)
    TextView textTahun;

    @BindView(R.id.note)
    TextView note;

    @BindView(R.id.lanjut)
    CardView lanjut;

    AdapterNotifikasi myAdapter;
    List<DataNotificationDetail> listAllData = new ArrayList<>();
    String getJenis = "", getMerk = "", getTipe = "", getWarna = "", getVal = "", getTahun = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_gadai_elektronik3);
        ButterKnife.bind(this);

        back.setOnClickListener(this);
        jenis.setOnClickListener(this);
        merk.setOnClickListener(this);
        tipe.setOnClickListener(this);
        warna.setOnClickListener(this);
        tahun.setOnClickListener(this);
        lanjut.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.back:
                onBackPressed();
                break;

            case R.id.jenis:
                Intent intent = new Intent(GadaiElektronik3.this, GadaiDetail.class);
                intent.putExtra("id", "0");
                intent.putExtra("val", "1");
                startActivityForResult(intent, 8);
                break;

            case R.id.merk:
                if(getJenis.equals(""))
                {
                    dialogWarning("Silakan pilih jenis terlebih dahulu");
                }
                else
                {
                    Intent intent2 = new Intent(GadaiElektronik3.this, GadaiDetail.class);
                    intent2.putExtra("id", getJenis);
                    intent2.putExtra("val", "2");
                    startActivityForResult(intent2, 8);
                }

                break;

            case R.id.tipe:
                if(getMerk.equals(""))
                {
                    dialogWarning("Silakan pilih merk terlebih dahulu");
                }
                else
                {
                    Intent intent3 = new Intent(GadaiElektronik3.this, GadaiDetail.class);
                    intent3.putExtra("id", getMerk);
                    intent3.putExtra("val", "3");
                    startActivityForResult(intent3, 8);
                }

                break;

            case R.id.warna:
                if(getTipe.equals(""))
                {
                    dialogWarning("Silakan pilih tipe terlebih dahulu");
                }
                else
                {
                    Intent intent4 = new Intent(GadaiElektronik3.this, GadaiDetail.class);
                    intent4.putExtra("id", "0");
                    intent4.putExtra("val", "4");
                    startActivityForResult(intent4, 8);
                }

                break;

            case R.id.tahun:
                if(getTipe.equals(""))
                {
                    dialogWarning("Silakan pilih tipe terlebih dahulu");
                }
                else
                {
                    Intent intent5 = new Intent(GadaiElektronik3.this, GadaiDetail.class);
                    intent5.putExtra("id", getTipe);
                    intent5.putExtra("val", "5");
                    startActivityForResult(intent5, 8);
                }

                break;

            case R.id.lanjut:
                sendData();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 8 && resultCode == RESULT_OK && data != null)
        {
            if(data.getStringExtra("val").equals("1")){
                textjenis.setText(""+data.getStringExtra("nama"));
                textMerk.setText("Merk Barang");
                textTipe.setText("Tipe");
                textWarna.setText("Warna");
                textTahun.setText("Tahun");

                getJenis = ""+data.getStringExtra("id");
                getMerk = "";
                getTipe = "";
                getWarna = "";
                getTahun = "";

                tahun.setVisibility(View.GONE);
            }else if(data.getStringExtra("val").equals("2")){
                textMerk.setText(""+data.getStringExtra("nama"));
                textTipe.setText("Tipe");
                textWarna.setText("Warna");
                textTahun.setText("Tahun");

                getMerk = ""+data.getStringExtra("id");
                getTipe = "";
                getWarna = "";
                getTahun = "";
            }else if(data.getStringExtra("val").equals("3")){
                textTipe.setText(""+data.getStringExtra("nama"));
                textWarna.setText("Warna");
                textTahun.setText("Tahun");

                getTipe = ""+data.getStringExtra("id");
                gadaiHitung();
//            if(data.getStringExtra("id").equals("") || data.getStringExtra("id").isEmpty() || data.getStringExtra("id").equals("0")){
//                tahun.setVisibility(View.GONE);
//                gadaiHitung();
//            }else{
//                tahun.setVisibility(View.VISIBLE);
//
//                getWarna = "";
//                getTahun = "";
//            }
            }else if(data.getStringExtra("val").equals("4")){
                textWarna.setText(""+data.getStringExtra("nama"));
                getWarna = ""+data.getStringExtra("id");
            }else if(data.getStringExtra("val").equals("5")){
                textTahun.setText(""+data.getStringExtra("nama"));
                getTahun = ""+data.getStringExtra("id");
                gadaiHitung();
            }
        }else{

        }
    }


    public void sendData()
    {
        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        SendGadaiProcess sendGadaiProcess = new SendGadaiProcess(""+getTahun, ""+getTipe,  ""+getWarna);
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseNormal2> userCall = service.gadaiProcess(tokenBearer,sendGadaiProcess);
        userCall.enqueue(new Callback<ResponseNormal2>() {
            @Override
            public void onResponse(Call<ResponseNormal2> call, retrofit2.Response<ResponseNormal2> response) {

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
            public void onFailure(Call<ResponseNormal2> call, Throwable t) {

                dialogWarning(getResources().getString(R.string.gagalCobaLagi));
            }
        });
    }

    public void gadaiHitung()
    {
        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        SendGadaiProcess sendGadaiProcess = new SendGadaiProcess(""+getTahun, ""+getTipe,  ""+getWarna);
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseNormal2> userCall = service.gadaiHitung(tokenBearer,sendGadaiProcess);
        userCall.enqueue(new Callback<ResponseNormal2>() {
            @Override
            public void onResponse(Call<ResponseNormal2> call, retrofit2.Response<ResponseNormal2> response) {

                //Log.w("", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful())
                {
                    String myMessage = ""+response.body().getreason();
                    String status = ""+response.body().getstatus();
                    if(status.equals("true"))
                    {
                        note.setText("Rp. "+currencyFormat(response.body().getdata()));
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
            public void onFailure(Call<ResponseNormal2> call, Throwable t) {

                dialogWarning(getResources().getString(R.string.gagalCobaLagi));
            }
        });
    }

    public static String currencyFormat(String amount) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0.00");
        return formatter.format(Double.parseDouble(amount));
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
                Intent intent = new Intent(GadaiElektronik3.this, Homescreen.class);
                startActivity(intent);
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
