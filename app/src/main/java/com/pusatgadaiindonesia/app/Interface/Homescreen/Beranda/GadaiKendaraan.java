package com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.pusatgadaiindonesia.app.Adapter.AdapterMasterKendaraan;
import com.pusatgadaiindonesia.app.Adapter.AdapterYear2;
import com.pusatgadaiindonesia.app.Database.Data_Profile;
import com.pusatgadaiindonesia.app.Model.Master.DataMaster;
import com.pusatgadaiindonesia.app.Model.Master.ResponseMaster;
import com.pusatgadaiindonesia.app.Model.Percentage.ResponsePercentage;
import com.pusatgadaiindonesia.app.Model.Percentage.SendPercentage;
import com.pusatgadaiindonesia.app.Model.Province.DataProvince;
import com.pusatgadaiindonesia.app.R;
import com.pusatgadaiindonesia.app.Services.Retrofit.APIService;
import com.pusatgadaiindonesia.app.Services.Retrofit.ApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class GadaiKendaraan extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.lanjut)
    CardView lanjut;

    @BindView(R.id.layMotor)
    LinearLayout layMotor;

    @BindView(R.id.layMobil)
    LinearLayout layMobil;

    @BindView(R.id.radioMotor)
    ImageView radioMotor;

    @BindView(R.id.radioMobil)
    ImageView radioMobil;


    @BindView(R.id.merk)
    LinearLayout merk;

    @BindView(R.id.tipe)
    LinearLayout tipe;

    @BindView(R.id.editTipe)
    EditText editTipe;

    @BindView(R.id.tahun)
    LinearLayout tahun;

    @BindView(R.id.atasNama)
    LinearLayout atasNama;

    @BindView(R.id.grade)
    LinearLayout grade;

    @BindView(R.id.textTahun)
    TextView textTahun;

    @BindView(R.id.textGrade)
    TextView textGrade;

    @BindView(R.id.textAtasNama)
    TextView textAtasNama;

    @BindView(R.id.textMerk)
    TextView textMerk;

    @BindView(R.id.textTipe)
    TextView textTipe;

    @BindView(R.id.editNamaKendaraan)
    EditText editNamaKendaraan;

    @BindView(R.id.editNopol)
    EditText editNopol;

    @BindView(R.id.textPercentage)
    TextView textPercentage;

    String selectedYearId = "";
    String selectedGradeId = "";
    String selectedAtasNamaId = "Pribadi";

    String motorId = "41";
    String mobilId = "40";
    String selectedId = "41";

    Dialog loading;

    Boolean merkLoaded = false;
    List<DataMaster> listAllDataMerk = new ArrayList<>();
    AdapterMasterKendaraan myAdapterMerk;
    String selectedMerkId ="";

    Boolean tipeLoaded = false;
    List<DataMaster> listAllDataTipe = new ArrayList<>();
    AdapterMasterKendaraan myAdapterTipe;
    String selectedTipeId ="";

    String maxLoan = "0";
    String atasNamaNumb = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_gadai_kendaraan);
        ButterKnife.bind(this);

        back.setOnClickListener(this);
        lanjut.setOnClickListener(this);
        layMotor.setOnClickListener(this);
        layMobil.setOnClickListener(this);
        merk.setOnClickListener(this);
        tipe.setOnClickListener(this);
        tahun.setOnClickListener(this);
        grade.setOnClickListener(this);
        atasNama.setOnClickListener(this);

        getId();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.back:
                onBackPressed();
                break;

            case R.id.lanjut:
                Boolean namaFilled = cekNama();
                Boolean nopolFilled = cekNopol();
                Boolean merkFilled = cekMerk();
                Boolean tipeFilled = cekTipe();
                Boolean yearFilled = cekYear();
                Boolean gradeFilled = cekGrade();
                Boolean atasNamaFilled = cekAtasNama();
                Boolean subtipeFilled = cekSubTipe();

                if(namaFilled && nopolFilled && merkFilled && tipeFilled && yearFilled && gradeFilled && atasNamaFilled && subtipeFilled)
                {
                    Intent intent = new Intent(GadaiKendaraan.this, GadaiKendaraan2.class);
                    intent.putExtra("sendNama", ""+editNamaKendaraan.getText().toString());
                    intent.putExtra("sendNopol", ""+editNopol.getText().toString());
                    intent.putExtra("sendKategori", ""+selectedId);
                    intent.putExtra("sendMerk", ""+selectedMerkId);
                    intent.putExtra("sendTipe", ""+selectedTipeId);
                    intent.putExtra("sendYear", ""+textTahun.getText().toString());
                    intent.putExtra("sendGrade", ""+selectedGradeId);
                    intent.putExtra("sendAtasNamaId", ""+selectedAtasNamaId);
                    intent.putExtra("sendAtasNamaNumb", ""+atasNamaNumb);
                    intent.putExtra("sendAtasNamaText", ""+textAtasNama.getText().toString());
                    intent.putExtra("sendMaxLoan",""+maxLoan);
                    intent.putExtra("sendSubTipe", ""+editTipe.getText().toString());
                    intent.putExtra("percentage",""+textPercentage.getText().toString());
                    startActivity(intent);
                }

                break;

            case R.id.layMotor:
                radioMotor.setImageResource(R.drawable.ic_radio_button_checked);
                radioMobil.setImageResource(R.drawable.ic_radio_button_unchecked);
                selectedId = motorId;
                selectedMerkId="";
                merkLoaded = false;
                textMerk.setText("Merk");
                break;

            case R.id.layMobil:
                radioMotor.setImageResource(R.drawable.ic_radio_button_unchecked);
                radioMobil.setImageResource(R.drawable.ic_radio_button_checked);
                selectedId = mobilId;
                selectedMerkId="";
                merkLoaded = false;
                textMerk.setText("Merk");
                break;

            case R.id.merk:
                if(merkLoaded == true)
                {
                    showMerk();
                }
                else
                {
                    loadMerk();
                }
                break;

            case R.id.tipe:
                if(selectedMerkId.equals(""))
                {
                    dialogWarning("Pilih merk barang terlebih dahulu");
                }
                else
                {
                    if(tipeLoaded == true)
                    {
                        showTipe();
                    }
                    else
                    {
                        loadTipe();
                    }
                }
                break;

            case R.id.tahun:
                showYear();
                break;

            case R.id.grade:
                showGrade();
                break;

            case R.id.atasNama:
                showAtasNama();
                break;
        }
    }


    public void showYear()
    {
        final DialogPlus dialog = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(R.layout.model_dialog_province))
                .setGravity(Gravity.BOTTOM)
                .setInAnimation(R.anim.slide_in_bottom)
                .setOutAnimation(R.anim.slide_out_bottom)
                .create();

        View view = dialog.getHolderView();

        AdapterYear2 myAdapter;
        List<DataProvince> listAllData = new ArrayList<>();

        RecyclerView recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new AdapterYear2(this,recyclerView,listAllData, dialog, GadaiKendaraan.this, selectedYearId);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdFormat = new SimpleDateFormat("dd-MMMM-yyyy");
        String getDate = mdFormat.format(calendar.getTime());
        String[] splitter = getDate.split("-");
        int yearNow =  2020;
        try {
            yearNow =  Integer.parseInt(splitter[2]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            yearNow =  2020;
        }

        for (int i = 0; i < 10 ; i++)
        {
            DataProvince dataProvince = new DataProvince(""+i, ""+yearNow);
            listAllData.add(dataProvince);
            yearNow = yearNow - 1;
        }

        recyclerView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        myAdapter.setLoaded();

        dialog.show();

    }

    public void setYear(String id, String name)
    {
        //Toast.makeText(getApplicationContext(),""+name,Toast.LENGTH_SHORT).show();
        selectedYearId = ""+id;
        textTahun.setText(""+name);
    }

    public void showGrade()
    {
        final DialogPlus dialog = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(R.layout.model_dialog_grade))
                .setGravity(Gravity.BOTTOM)
                .setInAnimation(R.anim.slide_in_bottom)
                .setOutAnimation(R.anim.slide_out_bottom)
                .setBackgroundColorResId(Color.parseColor("#00000000"))
                .create();

        View view = dialog.getHolderView();

        final TextView s1 = view.findViewById(R.id.s1);
        final TextView s2 = view.findViewById(R.id.s2);
        final TextView s3 = view.findViewById(R.id.s3);

        if (selectedGradeId.equals("1"))
        {
            s1.setTextColor(getResources().getColor(R.color.blue_selected));
        }
        else if (selectedGradeId.equals("2"))
        {
            s2.setTextColor(getResources().getColor(R.color.blue_selected));
        }
        else if (selectedGradeId.equals("3"))
        {
            s3.setTextColor(getResources().getColor(R.color.blue_selected));
        }

        s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setGrade(s1.getText().toString());
                selectedGradeId="1";
                dialog.dismiss();
                //getPercentage();
            }
        });

        s2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setGrade(s2.getText().toString());
                selectedGradeId="2";
                dialog.dismiss();
                //getPercentage();
            }
        });

        s3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setGrade(s3.getText().toString());
                selectedGradeId="3";
                dialog.dismiss();
                //getPercentage();
            }
        });

        dialog.show();
    }

    public void setGrade(String grade)
    {
        textGrade.setText(""+grade);
    }

    public void showAtasNama()
    {
        final DialogPlus dialog = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(R.layout.model_dialog_atas_nama))
                .setGravity(Gravity.BOTTOM)
                .setInAnimation(R.anim.slide_in_bottom)
                .setOutAnimation(R.anim.slide_out_bottom)
                .setBackgroundColorResId(Color.parseColor("#00000000"))
                .create();

        View view = dialog.getHolderView();

        final TextView s1 = view.findViewById(R.id.s1);
        final TextView s2 = view.findViewById(R.id.s2);

        if (selectedAtasNamaId.equals("Pribadi"))
        {
            s1.setTextColor(getResources().getColor(R.color.blue_selected));
        }
        else if (selectedAtasNamaId.equals("Orang Lain"))
        {
            s2.setTextColor(getResources().getColor(R.color.blue_selected));
        }

        s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAtasNama(s1.getText().toString());
                selectedAtasNamaId="Pribadi";
                atasNamaNumb="1";
                dialog.dismiss();
            }
        });

        s2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAtasNama(s2.getText().toString());
                selectedAtasNamaId="Orang Lain";
                atasNamaNumb="2";
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    public void setAtasNama(String dus)
    {
        textAtasNama.setText(""+dus);
    }




    public void getId()
    {
        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseMaster> userCall = service.getAllCategory(tokenBearer);
        userCall.enqueue(new Callback<ResponseMaster>() {
            @Override
            public void onResponse(Call<ResponseMaster> call, retrofit2.Response<ResponseMaster> response) {
                if(response.isSuccessful())
                {
                    String status = ""+response.body().getstatus();
                    if(status.equals("success"))
                    {
                        List<DataMaster> arrayku = response.body().getdata();
                        if(arrayku.size() > 0)
                        {
                            for (int  i=0; i<arrayku.size();  i++)
                            {
                                String name = ""+arrayku.get(i).getname();
                                if(name.equals("Mobil"))
                                {
                                    mobilId = ""+arrayku.get(i).getid();
                                }
                                if(name.equals("Motor"))
                                {
                                    motorId = ""+arrayku.get(i).getid();
                                }
                            }
                        }
                    }
                }
                else {
                }
            }
            @Override
            public void onFailure(Call<ResponseMaster> call, Throwable t) {
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

    public void loadMerk()
    {
        dialogLoading();

        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        String baseUrl = ""+ApiClient.BASE_URL;

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseMaster> userCall = service.getMerk(tokenBearer, baseUrl+"master/brands?category="+selectedId);
        userCall.enqueue(new Callback<ResponseMaster>() {
            @Override
            public void onResponse(Call<ResponseMaster> call, retrofit2.Response<ResponseMaster> response) {
                //Log.w("Merk", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful())
                {
                    String status = ""+response.body().getstatus();
                    if(status.equals("success"))
                    {
                        listAllDataMerk = new ArrayList<>();
                        List<DataMaster> arrayku = response.body().getdata();
                        if(arrayku.size() > 0)
                        {
                            merkLoaded = true;
                            for (int  i=0; i<arrayku.size();  i++)
                            {
                                DataMaster dataMaster = new DataMaster(
                                        ""+arrayku.get(i).getid(),
                                        ""+arrayku.get(i).getname(),
                                        ""
                                );
                                listAllDataMerk.add(dataMaster);
                            }
                            showMerk();
                        }
                        else
                        {
                            dialogWarning("Data Merk Kosong");
                        }
                    }
                    else
                    {
                        merkLoaded = false;
                        dialogWarning(""+response.body().getmessage());
                    }


                }
                else {
                    merkLoaded = false;
                    // @M - If response from server is not success, get the message and show it in dialog
                    String message = "Gagal. Silakan coba lagi";
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                        message = ""+jObjError.getString("message");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    dialogWarning(""+message);
                }
                loading.dismiss();
            }
            @Override
            public void onFailure(Call<ResponseMaster> call, Throwable t) {
                Log.w("Merk","Error : "+t.getMessage());
                dialogWarning(""+t.getMessage());
            }
        });
    }

    public void showMerk()
    {
        final DialogPlus dialog = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(R.layout.model_dialog_province))
                .setGravity(Gravity.BOTTOM)
                .setInAnimation(R.anim.slide_in_bottom)
                .setOutAnimation(R.anim.slide_out_bottom)
                .create();

        View view = dialog.getHolderView();
        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                Handler handler =  new Handler();
                Runnable myRunnable = new Runnable() {
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                };
                handler.postDelayed(myRunnable,1500);
            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapterMerk = new AdapterMasterKendaraan(this,recyclerView,listAllDataMerk, dialog, GadaiKendaraan.this, selectedMerkId, "2");

        recyclerView.setAdapter(myAdapterMerk);
        myAdapterMerk.notifyDataSetChanged();
        myAdapterMerk.setLoaded();

        dialog.show();
    }

    public void setMerk(String id, String name)
    {
        selectedMerkId = ""+id;
        textMerk.setText(""+name);

        selectedTipeId = "";
        textTipe.setText("Tipe");
        tipeLoaded = false;

        //getPercentage();
    }


    public void loadTipe()
    {
        dialogLoading();

        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        String baseUrl = ""+ApiClient.BASE_URL;

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseMaster> userCall = service.getTipe(tokenBearer, baseUrl+"master/types?brand="+selectedMerkId);
        userCall.enqueue(new Callback<ResponseMaster>() {
            @Override
            public void onResponse(Call<ResponseMaster> call, retrofit2.Response<ResponseMaster> response) {
                //Log.w("Tipe", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful())
                {
                    String status = ""+response.body().getstatus();
                    if(status.equals("success"))
                    {
                        listAllDataTipe = new ArrayList<>();
                        List<DataMaster> arrayku = response.body().getdata();
                        if(arrayku.size() > 0)
                        {
                            tipeLoaded = true;
                            for (int  i=0; i<arrayku.size();  i++)
                            {
                                DataMaster dataMaster = new DataMaster(
                                        ""+arrayku.get(i).getid(),
                                        ""+arrayku.get(i).getname(),
                                        ""+arrayku.get(i).getMaxLoanPrice()
                                );
                                listAllDataTipe.add(dataMaster);
                            }
                            showTipe();
                        }
                        else
                        {
                            dialogWarning("Data Tipe Kosong");
                        }
                    }
                    else
                    {
                        tipeLoaded = false;
                        dialogWarning(""+response.body().getmessage());
                    }


                }
                else {
                    tipeLoaded = false;
                    // @M - If response from server is not success, get the message and show it in dialog
                    String message = "Gagal. Silakan coba lagi";
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                        message = ""+jObjError.getString("message");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    dialogWarning(""+message);
                }
                loading.dismiss();
            }
            @Override
            public void onFailure(Call<ResponseMaster> call, Throwable t) {
                Log.w("Tipe","Error : "+t.getMessage());
                dialogWarning(""+t.getMessage());
            }
        });
    }

    public void showTipe()
    {
        final DialogPlus dialog = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(R.layout.model_dialog_province))
                .setGravity(Gravity.BOTTOM)
                .setInAnimation(R.anim.slide_in_bottom)
                .setOutAnimation(R.anim.slide_out_bottom)
                .create();

        View view = dialog.getHolderView();
        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                Handler handler =  new Handler();
                Runnable myRunnable = new Runnable() {
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                };
                handler.postDelayed(myRunnable,1500);
            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapterTipe = new AdapterMasterKendaraan(this,recyclerView,listAllDataTipe, dialog, GadaiKendaraan.this, selectedTipeId, "3");

        recyclerView.setAdapter(myAdapterTipe);
        myAdapterTipe.notifyDataSetChanged();
        myAdapterTipe.setLoaded();

        dialog.show();
    }

    public void setTipe(String id, String name, String price)
    {
        selectedTipeId = ""+id;
        textTipe.setText(""+name);
        maxLoan = price;
    }

    public Boolean cekNama()
    {
        String nama = ""+editNamaKendaraan.getText().toString();
        if(nama.equals("") || nama.equals("null"))
        {
            editNamaKendaraan.setError("Masukkan Nama Kendaraan");
            return false;
        }
        else
        {
            editNamaKendaraan.setError(null);
            return true;
        }
    }

    public Boolean cekNopol()
    {
        String nopol = ""+editNopol.getText().toString();
        if(nopol.equals("") || nopol.equals("null"))
        {
            editNopol.setError("Masukkan Nomor Polisi Kendaraan");
            return false;
        }
        else
        {
            editNopol.setError(null);
            return true;
        }
    }

    public Boolean cekMerk()
    {
        if(selectedMerkId.equals(""))
        {
            dialogWarning("Silakan pilih merk kendaraan");
            return false;
        }
        else
        {
            return true;
        }
    }

    public Boolean cekTipe()
    {
        if(selectedTipeId.equals(""))
        {
            dialogWarning("Silakan pilih tipe kendaraan");
            return false;
        }
        else
        {
            return true;
        }
    }

    public Boolean cekSubTipe()
    {
        String tipe = ""+editTipe.getText().toString();
        if(tipe.equals("") || tipe.equals("null"))
        {
            editTipe.setError("Silakan masukkan sub tipe kendaraan");
            return false;
        }
        else
        {
            editTipe.setError(null);
            return true;
        }
    }

    public Boolean cekYear()
    {
        if(selectedYearId.equals(""))
        {
            dialogWarning("Silakan pilih tahun kendaraan");
            return false;
        }
        else
        {
            return true;
        }
    }

    public Boolean cekGrade()
    {
        if(selectedGradeId.equals(""))
        {
            dialogWarning("Silakan pilih grade kendaraan");
            return false;
        }
        else
        {
            return true;
        }
    }

    public Boolean cekAtasNama()
    {
        if(selectedAtasNamaId.equals(""))
        {
            dialogWarning("Silakan pilih atas nama kendaraan");
            return false;
        }
        else
        {
            return true;
        }
    }


    public void getPercentage()
    {
        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        String grade = "";
        if(selectedGradeId.equals("1"))
        {
            grade = "A";
        }
        else if(selectedGradeId.equals("2"))
        {
            grade = "B";
        }
        else if(selectedGradeId.equals("3"))
        {
            grade = "C";
        }

        SendPercentage sendPercentage = new SendPercentage(""+selectedMerkId,"GK",""+grade);
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponsePercentage> userCall = service.getPercentage(tokenBearer, sendPercentage);
        userCall.enqueue(new Callback<ResponsePercentage>() {
            @Override
            public void onResponse(Call<ResponsePercentage> call, retrofit2.Response<ResponsePercentage> response) {
                if(response.isSuccessful())
                {
                    textPercentage.setText(""+response.body().getData().getfullset());
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
                }
            }
            @Override
            public void onFailure(Call<ResponsePercentage> call, Throwable t) {
                Log.w("Jenis","Error : "+t.getMessage());
            }
        });
    }
}
