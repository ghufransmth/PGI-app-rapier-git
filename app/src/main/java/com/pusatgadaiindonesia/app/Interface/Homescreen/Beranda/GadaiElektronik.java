package com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.pusatgadaiindonesia.app.Adapter.AdapterMasterElektronik;
import com.pusatgadaiindonesia.app.Adapter.AdapterYear4;
import com.pusatgadaiindonesia.app.Database.Data_Profile;
import com.pusatgadaiindonesia.app.Model.Master.DataMaster;
import com.pusatgadaiindonesia.app.Model.Master.ResponseMaster;
import com.pusatgadaiindonesia.app.Model.Percentage.ResponsePercentage;
import com.pusatgadaiindonesia.app.Model.Percentage.SendPercentage;
import com.pusatgadaiindonesia.app.Model.Province.DataProvince;
import com.pusatgadaiindonesia.app.R;
import com.pusatgadaiindonesia.app.Services.Retrofit.APIService;
import com.pusatgadaiindonesia.app.Services.Retrofit.ApiClient;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

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

public class GadaiElektronik extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.lanjut)
    CardView lanjut;

    @BindView(R.id.grade)
    LinearLayout grade;

    @BindView(R.id.textGrade)
    TextView textGrade;

    @BindView(R.id.dus)
    LinearLayout dus;

    @BindView(R.id.textDus)
    TextView textDus;

    @BindView(R.id.selectImage)
    ImageView selectImage;

    @BindView(R.id.jenis)
    LinearLayout jenis;

    @BindView(R.id.textjenis)
    TextView textJenis;

    @BindView(R.id.merk)
    LinearLayout merk;

    @BindView(R.id.textMerk)
    TextView textMerk;

    @BindView(R.id.tipe)
    LinearLayout tipe;

    @BindView(R.id.textTipe)
    TextView textTipe;

    @BindView(R.id.nama)
    EditText editNama;

    @BindView(R.id.imei)
    EditText editImei;

    @BindView(R.id.note)
    EditText editNote;

    @BindView(R.id.tahun)
    LinearLayout tahun;

    @BindView(R.id.textTahun)
    TextView textTahun;

    @BindView(R.id.checkBaterai)
    CheckBox checkBaterai;

    @BindView(R.id.checkCharger)
    CheckBox checkCharger;

    @BindView(R.id.checkBuku)
    CheckBox checkBuku;

    @BindView(R.id.checkGaransi)
    CheckBox checkGaransi;

    @BindView(R.id.checkDus)
    CheckBox checkDus;

    @BindView(R.id.checkTusuk)
    CheckBox checkTusuk;

    @BindView(R.id.checkHeadset)
    CheckBox checkHeadset;

    @BindView(R.id.checkUsb)
    CheckBox checkUsb;

    @BindView(R.id.checkMemory)
    CheckBox checkMemory;

    @BindView(R.id.checkSoftcase)
    CheckBox checkSoftcase;

    @BindView(R.id.checkSim)
    CheckBox checkSim;

    @BindView(R.id.checkKwitansi)
    CheckBox checkKwitansi;

    @BindView(R.id.textPercentage)
    TextView textPercentage;

    String selectedGradeId = "";
    String selectedDusId = "2";

    Boolean jenisLoaded = false;
    Boolean merkLoaded = false;
    Boolean tipeLoaded = false;
    Boolean imageLoaded = false;

    String selectedYearId = "";

    Dialog loading;

    List<DataMaster> listAllDataJenis = new ArrayList<>();
    AdapterMasterElektronik myAdapterJenis;

    List<DataMaster> listAllDataMerk = new ArrayList<>();
    AdapterMasterElektronik myAdapterMerk;

    List<DataMaster> listAllDataTipe = new ArrayList<>();
    AdapterMasterElektronik myAdapterTipe;
    
    String selectedJenisId = "", selectedJenisName = "";
    String selectedMerkId = "", selectedMerkName = "";
    String selectedTipeId = "", selectedTipeName = "";

    Uri imageUri;
    String nama = "", imei = "", note = "";

    Boolean requiredYear = false;
    String sendYear = "";
    String maxLoan = "0";
    String fullset = "";
    String batangan = "";
    Boolean isFullset = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_gadai_elektronik);
        ButterKnife.bind(this);

        back.setOnClickListener(this);
        lanjut.setOnClickListener(this);
        grade.setOnClickListener(this);
        dus.setOnClickListener(this);
        selectImage.setOnClickListener(this);
        jenis.setOnClickListener(this);
        merk.setOnClickListener(this);
        tipe.setOnClickListener(this);
        tahun.setOnClickListener(this);
        
        checkBuku.setOnCheckedChangeListener(this);
        checkDus.setOnCheckedChangeListener(this);
        checkCharger.setOnCheckedChangeListener(this);
        checkBaterai.setOnCheckedChangeListener(this);
        checkGaransi.setOnCheckedChangeListener(this);
        checkHeadset.setOnCheckedChangeListener(this);
        checkKwitansi.setOnCheckedChangeListener(this);
        checkMemory.setOnCheckedChangeListener(this);
        checkSim.setOnCheckedChangeListener(this);
        checkSoftcase.setOnCheckedChangeListener(this);
        checkUsb.setOnCheckedChangeListener(this);
        checkTusuk.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.back:
                onBackPressed();
                break;

            case R.id.lanjut:
                Boolean imageFilled = cekImage();
                Boolean namaFilled = cekNamaBarang();
                Boolean jenisFilled = cekJenis();
                Boolean merkFilled = cekMerk();
                Boolean tipeFilled = cekTipe();
                Boolean imeiFilled = cekImei();
                Boolean gradeFilled = cekGrade();
                Boolean noteFilled = cekNote();
                Boolean yearFilled = cekYear();
                if(namaFilled && imeiFilled && noteFilled && jenisFilled && merkFilled && tipeFilled && gradeFilled && imageFilled && yearFilled)
                {
                    String kelengkapan = "";
                    if(checkBaterai.isChecked())
                    {
                        kelengkapan = kelengkapan+"1";
                    }
                    if(checkCharger.isChecked())
                    {
                        kelengkapan = kelengkapan+",2";
                    }
                    if(checkBuku.isChecked())
                    {
                        kelengkapan = kelengkapan+",3";
                    }
                    if(checkGaransi.isChecked())
                    {
                        kelengkapan = kelengkapan+",4";
                    }
                    if(checkDus.isChecked())
                    {
                        kelengkapan = kelengkapan+",5";
                    }
                    if(checkTusuk.isChecked())
                    {
                        kelengkapan = kelengkapan+",6";
                    }
                    if(checkHeadset.isChecked())
                    {
                        kelengkapan = kelengkapan+",7";
                    }
                    if(checkUsb.isChecked())
                    {
                        kelengkapan = kelengkapan+",8";
                    }
                    if(checkMemory.isChecked())
                    {
                        kelengkapan = kelengkapan+",9";
                    }
                    if(checkSoftcase.isChecked())
                    {
                        kelengkapan = kelengkapan+",10";
                    }
                    if(checkSim.isChecked())
                    {
                        kelengkapan = kelengkapan+",11";
                    }
                    if(checkKwitansi.isChecked())
                    {
                        kelengkapan = kelengkapan+",12";
                    }


                    if(!kelengkapan.equals(""))
                    {
                        String check = ""+kelengkapan.charAt(0);
                        if(check.equals(","))
                        {
                            kelengkapan = kelengkapan.substring(1);
                        }
                    }

                    if(kelengkapan.equals(""))
                    {
                        dialogWarning("Silakan pilih kelengkapan barang terlebih dahulu");
                    }
                    else
                    {
                        Intent intent = new Intent(GadaiElektronik.this, GadaiElektronik2.class);
                        intent.putExtra("sendNama",""+nama);
                        intent.putExtra("sendImei",""+imei);
                        intent.putExtra("sendNote",""+note);
                        intent.putExtra("sendJenis",""+selectedJenisId);
                        intent.putExtra("sendMerk",""+selectedMerkId);
                        intent.putExtra("sendTipe",""+selectedTipeId);
                        intent.putExtra("sendGrade",""+selectedGradeId);
                        intent.putExtra("sendDus",""+selectedDusId);
                        intent.putExtra("sendImage",""+imageUri.toString());
                        intent.putExtra("sendYear",""+sendYear);
                        intent.putExtra("sendKelengkapan",""+kelengkapan);
                        intent.putExtra("sendMaxLoan",""+maxLoan);
                        //intent.putExtra("percentage",""+textPercentage.getText().toString());
                        startActivity(intent);
                    }


                }
                break;

            case R.id.grade:
                showGrade();
                break;

            case R.id.dus:
                showDus();
                break;

            case R.id.selectImage:
                cropImage();
                break;

            case R.id.jenis:
                if(jenisLoaded == true)
                {
                    showJenis();
                }
                else
                {
                    loadJenis();
                }
                break;

            case R.id.merk:
                if(selectedJenisId.equals(""))
                {
                    dialogWarning("Pilih jenis barang terlebih dahulu");
                }
                else
                {
                    if(merkLoaded == true)
                    {
                        showMerk();
                    }
                    else
                    {
                        loadMerk();
                    }
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
        }
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
                //getPercentage();
                dialog.dismiss();
            }
        });

        s2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setGrade(s2.getText().toString());
                selectedGradeId="2";
                //getPercentage();
                dialog.dismiss();
            }
        });

        s3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setGrade(s3.getText().toString());
                selectedGradeId="3";
                //getPercentage();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void setGrade(String grade)
    {
        textGrade.setText(""+grade);
    }


    public void showDus()
    {
        final DialogPlus dialog = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(R.layout.model_dialog_dus))
                .setGravity(Gravity.BOTTOM)
                .setInAnimation(R.anim.slide_in_bottom)
                .setOutAnimation(R.anim.slide_out_bottom)
                .setBackgroundColorResId(Color.parseColor("#00000000"))
                .create();

        View view = dialog.getHolderView();

        final TextView s1 = view.findViewById(R.id.s1);
        final TextView s2 = view.findViewById(R.id.s2);

        if (selectedDusId.equals("1"))
        {
            s1.setTextColor(getResources().getColor(R.color.blue_selected));
        }
        else if (selectedDusId.equals("2"))
        {
            s2.setTextColor(getResources().getColor(R.color.blue_selected));
        }

        s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDus(s1.getText().toString());
                selectedDusId="1";
                dialog.dismiss();
            }
        });

        s2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDus(s2.getText().toString());
                selectedDusId="2";
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    public void setDus(String dus)
    {
        textDus.setText(""+dus);
    }

    public void cropImage()
    {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setFixAspectRatio(true)
                .start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri uriImage = result.getUri();
                imageUri = uriImage;
                selectImage.setImageURI(uriImage);
                imageLoaded = true;
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                imageLoaded = false;
            }
        }
    }


    public void showJenis()
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
        myAdapterJenis = new AdapterMasterElektronik(this,recyclerView,listAllDataJenis, dialog, GadaiElektronik.this, selectedJenisId, "1");

        recyclerView.setAdapter(myAdapterJenis);
        myAdapterJenis.notifyDataSetChanged();
        myAdapterJenis.setLoaded();

        dialog.show();
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
        myAdapterMerk = new AdapterMasterElektronik(this,recyclerView,listAllDataMerk, dialog, GadaiElektronik.this, selectedMerkId, "2");

        recyclerView.setAdapter(myAdapterMerk);
        myAdapterMerk.notifyDataSetChanged();
        myAdapterMerk.setLoaded();

        dialog.show();
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
        myAdapterTipe = new AdapterMasterElektronik(this,recyclerView,listAllDataTipe, dialog, GadaiElektronik.this, selectedTipeId, "3");

        recyclerView.setAdapter(myAdapterTipe);
        myAdapterTipe.notifyDataSetChanged();
        myAdapterTipe.setLoaded();

        dialog.show();
    }

    public void setJenis(String id, String name)
    {
        selectedJenisId = ""+id;
        selectedJenisName = ""+name;
        textJenis.setText(""+name);

        selectedMerkId = "";
        selectedMerkName = "";
        textMerk.setText("Merk Barang");
        merkLoaded = false;

        selectedTipeId = "";
        selectedTipeName = "";
        textTipe.setText("Tipe");
        tipeLoaded = false;

        requiredYear = false;
        sendYear = "";
        textTahun.setText("Tahun");
        tahun.setVisibility(View.GONE);

        String[] splitter = name.split(" ");
        if(splitter.length > 0)
        {
            for(int i=0; i<splitter.length; i++)
            {
                String cek = splitter[i].toLowerCase();
                if(cek.equals("pc") || cek.equals("laptop"))
                {
                    requiredYear = true;
                    tahun.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void setMerk(String id, String name)
    {
        selectedMerkId = ""+id;
        selectedMerkName = ""+name;
        textMerk.setText(""+name);

        selectedTipeId = "";
        selectedTipeName = "";
        textTipe.setText("Tipe");
        tipeLoaded = false;

        //getPercentage();
    }

    public void setTipe(String id, String name, String price)
    {
        selectedTipeId = ""+id;
        selectedTipeName = ""+name;
        textTipe.setText(""+name);
        maxLoan = price;
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

    public void loadJenis()
    {
        dialogLoading();

        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseMaster> userCall = service.getAllCategoryElektronik(tokenBearer);
        userCall.enqueue(new Callback<ResponseMaster>() {
            @Override
            public void onResponse(Call<ResponseMaster> call, retrofit2.Response<ResponseMaster> response) {
                Log.w("Jenis", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful())
                {
                    String status = ""+response.body().getstatus();
                    if(status.equals("success"))
                    {
                        listAllDataJenis = new ArrayList<>();
                        List<DataMaster> arrayku = response.body().getdata();
                        if(arrayku.size() > 0)
                        {
                            jenisLoaded = true;
                            for (int  i=0; i<arrayku.size();  i++)
                            {
                                DataMaster dataMaster = new DataMaster(
                                        ""+arrayku.get(i).getid(),
                                        ""+arrayku.get(i).getname(),
                                        ""
                                );
                                listAllDataJenis.add(dataMaster);
                            }
                            showJenis();
                        }
                        else
                        {
                            dialogWarning("Data Proivinsi Kosong");
                        }
                    }
                    else
                    {
                        jenisLoaded = false;
                        dialogWarning(""+response.body().getmessage());
                    }


                }
                else {
                    jenisLoaded = false;
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
                Log.w("Jenis","Error : "+t.getMessage());
                dialogWarning(""+t.getMessage());
            }
        });
    }


    public void loadMerk()
    {
        dialogLoading();

        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        String baseUrl = ""+ApiClient.BASE_URL;

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseMaster> userCall = service.getMerk(tokenBearer, baseUrl+"master/brands?category="+selectedJenisId);
        userCall.enqueue(new Callback<ResponseMaster>() {
            @Override
            public void onResponse(Call<ResponseMaster> call, retrofit2.Response<ResponseMaster> response) {
                Log.w("Merk", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
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
                Log.w("Tipe", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
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


    public Boolean cekNamaBarang()
    {
        nama = ""+editNama.getText().toString();
        if(nama.equals("") || nama.equals("null"))
        {
            editNama.setError("Masukkan nama barang");
            return false;
        }
        else
        {
            editNama.setError(null);
            return true;
        }
    }

    public Boolean cekImei()
    {
        imei = ""+editImei.getText().toString();
        if(imei.equals("") || imei.equals("null"))
        {
            editImei.setError("Masukkan No Imei / ESN  barang");
            return false;
        }
        else
        {
            int jml = imei.length();
            if(jml < 15)
            {
                editImei.setError("No Imei minimal 15 angka");
                return false;
            }
            else
            {
                editImei.setError(null);
                return true;
            }

        }
    }

    public Boolean cekNote()
    {
        note = ""+editNote.getText().toString();
        if(note.equals("") || note.equals("null"))
        {
            editNote.setError("Masukkan detail kondisi barang");
            return false;
        }
        else
        {
            editNote.setError(null);
            return true;
        }
    }

    public Boolean cekYear()
    {
        if(requiredYear == true)
        {
            if(sendYear.equals("") || sendYear.equals("null"))
            {
                dialogWarning("Silakan masukkan tahun barang");
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return true;
        }

    }

    public Boolean cekJenis()
    {
        if(selectedJenisId.equals("") || selectedJenisId.equals("null"))
        {
            dialogWarning("Silakan pilih jenis barang");
            return false;
        }
        else
        {
            return true;
        }
    }

    public Boolean cekMerk()
    {
        if(selectedMerkId.equals("") || selectedMerkId.equals("null"))
        {
            dialogWarning("Silakan pilih merk barang");
            return false;
        }
        else
        {
            return true;
        }
    }

    public Boolean cekTipe()
    {
        if(selectedTipeId.equals("") || selectedTipeId.equals("null"))
        {
            dialogWarning("Silakan pilih tipe barang");
            return false;
        }
        else
        {
            return true;
        }
    }

    public Boolean cekGrade()
    {
        if(selectedGradeId.equals("") || selectedGradeId.equals("null"))
        {
            dialogWarning("Silakan pilih grade barang");
            return false;
        }
        else
        {
            return true;
        }
    }

    public Boolean cekImage()
    {
        if(imageLoaded == false)
        {
            dialogWarning("Silakan pilih foto barang");
            return false;
        }
        else
        {
            return true;
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

        AdapterYear4 myAdapter;
        List<DataProvince> listAllData = new ArrayList<>();

        RecyclerView recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new AdapterYear4(this,recyclerView,listAllData, dialog, GadaiElektronik.this, selectedYearId);

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
        sendYear = ""+name;
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

        SendPercentage sendPercentage = new SendPercentage(""+selectedMerkId,"GE",""+grade);
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponsePercentage> userCall = service.getPercentage(tokenBearer, sendPercentage);
        userCall.enqueue(new Callback<ResponsePercentage>() {
            @Override
            public void onResponse(Call<ResponsePercentage> call, retrofit2.Response<ResponsePercentage> response) {
                if(response.isSuccessful())
                {
                    fullset = ""+response.body().getData().getfullset();
                    batangan = ""+response.body().getData().getbatangan();
                    countCheck();
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        countCheck();
    }
    
    public void countCheck()
    {
        int totalCheck = 0;
        if(checkBaterai.isChecked())
        {
            totalCheck = totalCheck+1;
        }
        if(checkCharger.isChecked())
        {
            totalCheck = totalCheck+1;
        }
        if(checkBuku.isChecked())
        {
            totalCheck = totalCheck+1;
        }
        if(checkGaransi.isChecked())
        {
            totalCheck = totalCheck+1;
        }
        if(checkDus.isChecked())
        {
            totalCheck = totalCheck+1;
        }
        if(checkTusuk.isChecked())
        {
            totalCheck = totalCheck+1;
        }
        if(checkHeadset.isChecked())
        {
            totalCheck = totalCheck+1;
        }
        if(checkUsb.isChecked())
        {
            totalCheck = totalCheck+1;
        }
        if(checkMemory.isChecked())
        {
            totalCheck = totalCheck+1;
        }
        if(checkSoftcase.isChecked())
        {
            totalCheck = totalCheck+1;
        }
        if(checkSim.isChecked())
        {
            totalCheck = totalCheck+1;
        }
        if(checkKwitansi.isChecked())
        {
            totalCheck = totalCheck+1;
        }

        if(totalCheck > 0)
        {
            isFullset = true;
            textPercentage.setText(""+fullset);
        }
        else
        {
            isFullset = false;
            textPercentage.setText(""+batangan);
        }
    }
}
