package com.pusatgadaiindonesia.app.Interface.Auth;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.pusatgadaiindonesia.app.Adapter.AdapterCity;
import com.pusatgadaiindonesia.app.Adapter.AdapterProvince;
import com.pusatgadaiindonesia.app.Model.Cities.DataCitiesDetail;
import com.pusatgadaiindonesia.app.Model.Cities.ResponseCities;
import com.pusatgadaiindonesia.app.Model.Province.DataProvince;
import com.pusatgadaiindonesia.app.Model.Province.ResponseProvince;
import com.pusatgadaiindonesia.app.Model.Register.ResponseRegister;
import com.pusatgadaiindonesia.app.Model.Register.SendRegister;
import com.pusatgadaiindonesia.app.R;
import com.pusatgadaiindonesia.app.Services.Retrofit.APIService;
import com.pusatgadaiindonesia.app.Services.Retrofit.ApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class Signup extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.layPria)
    LinearLayout layPria;

    @BindView(R.id.layWanita)
    LinearLayout layWanita;

    @BindView(R.id.radioPria)
    ImageView radioPria;

    @BindView(R.id.radioWanita)
    ImageView radioWanita;

    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.calendar)
    LinearLayout calendar;

    @BindView(R.id.textCalendar)
    TextView textCalendar;

    @BindView(R.id.register)
    LinearLayout registrasi;

    @BindView(R.id.selectProvince)
    LinearLayout selectProvince;

    @BindView(R.id.provinceName)
    TextView provinceName;

    @BindView(R.id.selectCity)
    LinearLayout selectCity;

    @BindView(R.id.cityName)
    TextView cityName;

    @BindView(R.id.editName)
    EditText editName;

    @BindView(R.id.editEmail)
    EditText editEmail;

    @BindView(R.id.editPhone)
    EditText editPhone;

    @BindView(R.id.editIdentity)
    EditText editIdentity;

    @BindView(R.id.editAddress)
    EditText editAddress;

    @BindView(R.id.editPassword)
    EditText editPassword;

    @BindView(R.id.editCPassword)
    EditText editCPassword;

    android.app.DatePickerDialog datePickerDialog;

    String selectedProvinceName = "", selectedProvinceId = "";
    String selectedCityName = "", selectedCityId = "";
    String selectedGender = "m";
    String selectedDate = "";

    //Provinsi
    Boolean provinceLoaded = false;
    Boolean provinceShow = false;
    List<DataProvince> listAllDataProvince = new ArrayList<>();
    AdapterProvince myAdapter;
    String provinceMessage = "";

    //City
    List<DataCitiesDetail> listAllDataCity = new ArrayList<>();
    AdapterCity myAdapterCity;

    Dialog loading;

    String sendName = "", sendEmail = "", sendPhone = "", sendIdentityType = "", sendIdentityNumber = "", sendPob = "",
            sendDob = "", sendGender = "", sendAddress = "", sendProvinceId = "", sendCityId = "", sendPassword = "", sendCPassword = "";

    //Calendar
    final Calendar c = Calendar.getInstance();
    int dYear = c.get(Calendar.YEAR);
    int dMonth = c.get(Calendar.MONTH);
    int dDay = c.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_signup);

        ButterKnife.bind(this);

        layPria.setOnClickListener(this);
        layWanita.setOnClickListener(this);
        calendar.setOnClickListener(this);
        back.setOnClickListener(this);
        registrasi.setOnClickListener(this);
        selectProvince.setOnClickListener(this);
        selectCity.setOnClickListener(this);

        loadProvince(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.layPria:
                radioPria.setBackgroundResource(R.drawable.ic_radio_button_checked);
                radioWanita.setBackgroundResource(R.drawable.ic_radio_button_unchecked);
                selectedGender = "m";
                break;

            case R.id.layWanita:
                radioPria.setBackgroundResource(R.drawable.ic_radio_button_unchecked);
                radioWanita.setBackgroundResource(R.drawable.ic_radio_button_checked);
                selectedGender = "f";
                break;

            case R.id.calendar:
                showCalendar();
                break;

            case R.id.back:
                onBackPressed();
                break;

            case R.id.register:
                cekForm();
                break;

            case R.id.selectProvince:
                if(provinceLoaded == false)
                {
                    loadProvince(true);
                }
                else
                {
                    if(provinceShow == true)
                    {
                        showProvince();
                    }
                    else
                    {
                        dialogWarning(""+provinceMessage);
                    }
                }

                break;

            case R.id.selectCity:
                if(selectedProvinceId.equals(""))
                {
                    dialogWarning("Pilih provinsi terlebih dahulu");
                }
                else
                {
                    loadCities(""+selectedProvinceId);
                }
                //showCity();
                break;
        }
    }

    public void showCalendar()
    {
                datePickerDialog = new android.app.DatePickerDialog(Signup.this,
                        new android.app.DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(android.widget.DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                                /*Calendar newDate = Calendar.getInstance();
                                newDate.set(year, monthOfYear, dayOfMonth);*/
                                int month2 = monthOfYear+1;
                                String monthString = ""+month2;
                                String dayString = ""+dayOfMonth;
                                if(month2 > 9)
                                {
                                    monthString = ""+month2;
                                }
                                else
                                {
                                    monthString = "0"+month2;
                                }
                                if(dayOfMonth > 9)
                                {
                                    dayString = ""+dayOfMonth;
                                }
                                else
                                {
                                    dayString = "0"+dayOfMonth;
                                }
                                selectedDate = year+"-"+monthString+"-"+dayString;
                                textCalendar.setText(dayString+" / "+monthString+" / "+year);
                                dDay = dayOfMonth;
                                dMonth = monthOfYear;
                                dYear = year;
                            }
                        }, dYear, dMonth, dDay);
                datePickerDialog.show();
    }

    public void showProvince()
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
        myAdapter = new AdapterProvince(this,recyclerView,listAllDataProvince, dialog, Signup.this, selectedProvinceId);

        /*DataProvince dataProvince = new DataProvince("1", "Jawa Tengah");
        listAllDataProvince.add(dataProvince);
        DataProvince dataProvince2 = new DataProvince("2", "Jawa Barat");
        listAllDataProvince.add(dataProvince2);
        DataProvince dataProvince3 = new DataProvince("3", "Jawa Timur");
        listAllDataProvince.add(dataProvince3);
        DataProvince dataProvince4 = new DataProvince("4", "Sumatera Utara");
        listAllDataProvince.add(dataProvince4);
        DataProvince dataProvince5 = new DataProvince("5", "Jakarta");
        listAllDataProvince.add(dataProvince5);*/

        recyclerView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        myAdapter.setLoaded();

        dialog.show();

    }

    public void setProvince(String id, String name)
    {
        //Toast.makeText(getApplicationContext(),""+name,Toast.LENGTH_SHORT).show();
        selectedProvinceId = ""+id;
        selectedProvinceName = ""+name;
        provinceName.setText(""+name);

        selectedCityId = "";
        selectedCityName = "";
        cityName.setText("pilih kota");
    }


    public void showCity()
    {
        final DialogPlus dialog = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(R.layout.model_dialog_province))
                .setGravity(Gravity.BOTTOM)
                .setInAnimation(R.anim.slide_in_bottom)
                .setOutAnimation(R.anim.slide_out_bottom)
                .create();

        View view = dialog.getHolderView();

        /*AdapterCity myAdapter;
        List<DataProvince> listAllData = new ArrayList<>();*/

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
        myAdapterCity = new AdapterCity(this,recyclerView,listAllDataCity, dialog, Signup.this, selectedCityId);

       /* DataProvince dataProvince = new DataProvince("1", "Kudus");
        listAllData.add(dataProvince);
        DataProvince dataProvince2 = new DataProvince("2", "Pati");
        listAllData.add(dataProvince2);
        DataProvince dataProvince3 = new DataProvince("3", "Semarang");
        listAllData.add(dataProvince3);
        DataProvince dataProvince4 = new DataProvince("4", "Malang");
        listAllData.add(dataProvince4);
        DataProvince dataProvince5 = new DataProvince("5", "Tulungagung");
        listAllData.add(dataProvince5);*/

        recyclerView.setAdapter(myAdapterCity);
        myAdapterCity.notifyDataSetChanged();
        myAdapterCity.setLoaded();

        dialog.show();

    }

    public void setCity(String id, String name)
    {
        //Toast.makeText(getApplicationContext(),""+name,Toast.LENGTH_SHORT).show();
        selectedCityId = ""+id;
        selectedCityName = ""+name;
        cityName.setText(""+name);
    }




    public void loadProvince(final Boolean showLoading)
    {
        if(showLoading == true)
        {
            dialogLoading();
        }

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseProvince> userCall = service.getAllProvince();
        userCall.enqueue(new Callback<ResponseProvince>() {
            @Override
            public void onResponse(Call<ResponseProvince> call, retrofit2.Response<ResponseProvince> response) {
                Log.w("PROVINCE", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful())
                {
                    String status = ""+response.body().getstatus();
                    if(status.equals("success"))
                    {
                        provinceLoaded = true;
                        listAllDataProvince = new ArrayList<>();
                        List<DataProvince> arrayku = response.body().getdata();
                        if(arrayku.size() > 0)
                        {
                            for (int  i=0; i<arrayku.size();  i++)
                            {
                                DataProvince  dataProvince = new DataProvince(
                                        ""+arrayku.get(i).getid(),
                                        ""+arrayku.get(i).getname()
                                );
                                listAllDataProvince.add(dataProvince);
                            }
                            provinceShow = true;
                        }
                        else
                        {
                            provinceLoaded = true;
                            provinceMessage = "Data Proivinsi Kosong";
                        }
                    }
                    else
                    {
                        provinceLoaded = true;
                        provinceMessage = ""+response.body().getmessage();
                    }


                }
                else {
                    provinceLoaded = false;
                    // @M - If response from server is not success, get the message and show it in dialog
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                        String m = ""+jObjError.getString("message");
                        Log.w("PROVINCE", "Error : "+m);
                        provinceMessage = ""+m;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if(showLoading == true)
                    {
                        Toast.makeText(getApplicationContext(), ""+provinceMessage, Toast.LENGTH_SHORT).show();
                    }

                }

                if(showLoading == true)
                {
                    loading.dismiss();
                }
            }
            @Override
            public void onFailure(Call<ResponseProvince> call, Throwable t) {
                Log.w("PROVINCE","Error : "+t.getMessage());
                provinceLoaded = false;
                provinceMessage = ""+t.getMessage();
                if(showLoading == true)
                {
                    loading.dismiss();
                    Toast.makeText(getApplicationContext(), ""+provinceMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void loadCities(String provinceId)
    {
        dialogLoading();

        String baseUrl = ""+ApiClient.BASE_URL;
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseCities> userCall = service.getCities(baseUrl+"master/province/"+provinceId);
        userCall.enqueue(new Callback<ResponseCities>() {
            @Override
            public void onResponse(Call<ResponseCities> call, retrofit2.Response<ResponseCities> response) {
                Log.w("PROVINCE", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful())
                {
                    String status = ""+response.body().getstatus();
                    if(status.equals("success"))
                    {
                        listAllDataCity = new ArrayList<>();
                        List<DataCitiesDetail> arrayku = response.body().getdata().getcities();
                        if(arrayku == null)
                        {
                            Toast.makeText(getApplicationContext(),"Data Kota Kosong",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            if(arrayku.size() > 0)
                            {
                                for (int  i=0; i<arrayku.size();  i++)
                                {
                                    DataCitiesDetail  dataCitiesDetail = new DataCitiesDetail(
                                            ""+arrayku.get(i).getid(),
                                            ""+arrayku.get(i).getname()
                                    );
                                    listAllDataCity.add(dataCitiesDetail);
                                }
                                showCity();

                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Data Kota Kosong",Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), ""+response.body().getmessage(), Toast.LENGTH_SHORT).show();
                    }


                }
                else {
                    String m = "";
                    // @M - If response from server is not success, get the message and show it in dialog
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                        m = ""+jObjError.getString("message");
                        Log.w("PROVINCE", "Error : "+m);
                        provinceMessage = ""+m;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                        Toast.makeText(getApplicationContext(), ""+m, Toast.LENGTH_SHORT).show();

                }
                loading.dismiss();

            }
            @Override
            public void onFailure(Call<ResponseCities> call, Throwable t) {
                Log.w("PROVINCE","Error : "+t.getMessage());
                loading.dismiss();
                Toast.makeText(getApplicationContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();

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
                Intent intent = new Intent(Signup.this, Login.class);
                startActivity(intent);
            }
        });
        dialog.show();
    }

    public void cekForm()
    {
        sendName = ""+editName.getText().toString();
        sendEmail = ""+editEmail.getText().toString();
        sendPhone = ""+editPhone.getText().toString();
        sendIdentityType = "ktp";
        sendIdentityNumber = ""+editIdentity.getText().toString();
        sendDob = ""+selectedDate;
        sendGender = ""+selectedGender;
        sendAddress = ""+editAddress.getText().toString();
        sendProvinceId = ""+selectedProvinceId;
        sendCityId = ""+selectedCityId;
        sendPassword = ""+editPassword.getText().toString();
        sendCPassword = ""+editCPassword.getText().toString();
        sendPob = ""+sendCityId;

        Boolean nameFilled = false, emailFilled = false, phoneFilled = false, identityFilled = false;
        Boolean dobFilled = false, addressFilled = false, provinceIdFilled = false, cityFilled = false;
        Boolean passwordFilled = false, cPasswordFilled = false;

        if(sendName.equals("") || sendName.equals("null"))
        {
            editName.setError("Nama tidak boleh kosong");
            nameFilled = false;
        }
        else
        {
            editName.setError(null);
            nameFilled = true;
        }

        if(sendEmail.equals("") || sendEmail.equals("null"))
        {
            editEmail.setError("Email tidak boleh kosong");
            emailFilled = false;
        }
        else
        {
            editEmail.setError(null);
            emailFilled = true;
        }

        if(sendPhone.equals("") || sendPhone.equals("null"))
        {
            editPhone.setError("Nomor telepon tidak boleh kosong");
            phoneFilled = false;
        }
        else
        {
            editPhone.setError(null);
            phoneFilled = true;

        }

        if(sendIdentityNumber.equals("") || sendIdentityNumber.equals("null"))
        {
            editIdentity.setError("Nomor telepon tidak boleh kosong");
            identityFilled = false;
        }
        else
        {
            editIdentity.setError(null);
            identityFilled = true;

        }

        if(sendDob.equals("") || sendDob.equals("null"))
        {
            dialogWarning("Silakan pilih tanggal lahir");
            dobFilled = false;
        }
        else
        {
            dobFilled = true;
        }

        if(sendAddress.equals("") || sendAddress.equals("null"))
        {
            editAddress.setError("Alamat tidak boleh kosong");
            addressFilled = false;
        }
        else
        {
            editAddress.setError(null);
            addressFilled = true;

        }

        if(sendProvinceId.equals("") || sendProvinceId.equals("null"))
        {
            dialogWarning("Silakan pilih provinsi");
            provinceIdFilled = false;
        }
        else
        {
            provinceIdFilled = true;
        }

        if(sendCityId.equals("") || sendCityId.equals("null"))
        {
            dialogWarning("Silakan pilih kota");
            cityFilled = false;
        }
        else
        {
            cityFilled = true;
        }

        if(sendPassword.equals("") || sendPassword.equals("null"))
        {
            editPassword.setError("Password tidak boleh kosong");
            passwordFilled = false;
        }
        else
        {
            editPassword.setError(null);
            passwordFilled = true;

        }

        if(sendCPassword.equals("") || sendCPassword.equals("null"))
        {
            editCPassword.setError("Tulis ulang password");
            cPasswordFilled = false;
        }
        else
        {
            if(sendPassword.equals(""+sendCPassword))
            {
                editCPassword.setError(null);
                cPasswordFilled = true;
            }
            else
            {
                editCPassword.setError("Password tidak sama");
                cPasswordFilled = false;
            }
        }

        if(nameFilled && emailFilled && phoneFilled && identityFilled && dobFilled && addressFilled && provinceIdFilled && cityFilled && passwordFilled && cPasswordFilled)
        {
            sendData();
        }

    }


    public void sendData()
    {
        dialogLoading();

        SendRegister sendRegister = new SendRegister(""+sendName, ""+sendEmail, ""+sendIdentityType, ""+sendPhone, ""+sendIdentityNumber, ""+sendPob, ""+sendDob,
                                    ""+sendGender, ""+sendAddress, ""+sendProvinceId, ""+sendCityId, ""+sendPassword, ""+sendCPassword);
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseRegister> userCall = service.register(sendRegister);
        userCall.enqueue(new Callback<ResponseRegister>() {
            @Override
            public void onResponse(Call<ResponseRegister> call, retrofit2.Response<ResponseRegister> response) {
                loading.dismiss();
                if(response.isSuccessful()){

                    String myMessage = ""+response.body().getmessage();
                    String status = ""+response.body().getstatus();
                    if(status.equals("success"))
                    {
                        /*Toast.makeText(getApplicationContext(),""+myMessage,Toast.LENGTH_SHORT).show();
                        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
                        data_profile.token = "" + response.body().getdata().gettoken();
                        data_profile.userId = "" + response.body().getdata().getuserId();
                        data_profile.userName = "" + response.body().getdata().getuserName();
                        data_profile.userEmail = "" + response.body().getdata().getuserEmail();
                        data_profile.login = true;
                        data_profile.save();*/
                        dialogSuccess(""+response.body().getmessage());
                        //Toast.makeText(getApplicationContext(),"Registrasi Berhasil. Silakan login",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        dialogWarning(""+myMessage);
                    }



                }
                else {
                    // @M - If response from server is not success, get the message and show it in dialog
                    JSONObject jObjError = null;
                    String message = ""+getResources().getString(R.string.gagalCobaLagi);
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                        message = ""+jObjError.getString("message");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //message = ""+response.body().getmessage();
                    } catch (IOException e) {
                        e.printStackTrace();
                        //message = ""+response.body().getmessage();
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
            public void onFailure(Call<ResponseRegister> call, Throwable t) {
                loading.dismiss();
                dialogWarning(getResources().getString(R.string.gagalCobaLagi));
            }
        });
    }
}