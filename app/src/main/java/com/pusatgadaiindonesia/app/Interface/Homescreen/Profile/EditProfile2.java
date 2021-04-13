package com.pusatgadaiindonesia.app.Interface.Homescreen.Profile;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.GsonBuilder;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.pusatgadaiindonesia.app.Adapter.AdapterCityEditProfile;
import com.pusatgadaiindonesia.app.Adapter.AdapterProvinceEditProfile;
import com.pusatgadaiindonesia.app.Database.Data_Profile;
import com.pusatgadaiindonesia.app.Model.Cities.DataCitiesDetail;
import com.pusatgadaiindonesia.app.Model.Cities.ResponseCities;
import com.pusatgadaiindonesia.app.Model.Profile.ResponseProfile;
import com.pusatgadaiindonesia.app.Model.Profile.SendPassword;
import com.pusatgadaiindonesia.app.Model.Province.DataProvince;
import com.pusatgadaiindonesia.app.Model.Province.ResponseProvince;
import com.pusatgadaiindonesia.app.Model.Register.SendRegister;
import com.pusatgadaiindonesia.app.Model.ResponseNormal;
import com.pusatgadaiindonesia.app.Model.ResponseNormal2;
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

public class EditProfile2 extends AppCompatActivity implements View.OnClickListener{


    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.simpan)
    CardView simpan;

    @BindView(R.id.editPassLama)
    EditText editPassLama;

    @BindView(R.id.editPassBaru)
    EditText editPassBaru;

    @BindView(R.id.editConfirmPass)
    EditText editConfirmPass;

    Dialog loading;

    String sendPassLama = "", sendPassBaru = "", sendConfirmPass = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_edit_profile2);

        ButterKnife.bind(this);

        back.setOnClickListener(this);
        simpan.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.back:
                onBackPressed();
                break;

            case R.id.simpan:
                cekForm();
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


    public void cekForm()
    {
        sendPassLama = ""+editPassLama.getText().toString();
        sendPassBaru = ""+editPassBaru.getText().toString();
        sendConfirmPass = ""+editConfirmPass.getText().toString();

        Boolean passLamaFilled = false, passBaruFilled = false, confirmPassFilled = false;

        if(sendPassLama.equals("") || sendPassLama.equals("null"))
        {
            editPassLama.setError("Nama tidak boleh kosong");
            passLamaFilled = false;
        }
        else
        {
            editPassLama.setError(null);
            passLamaFilled = true;
        }

        if(sendPassBaru.equals("") || sendPassBaru.equals("null"))
        {
            editPassBaru.setError("Email tidak boleh kosong");
            passBaruFilled = false;
        }
        else
        {
            editPassBaru.setError(null);
            passBaruFilled = true;
        }

        if(sendConfirmPass.equals("") || sendConfirmPass.equals("null"))
        {
            editConfirmPass.setError("Nomor telepon tidak boleh kosong");
            confirmPassFilled = false;
        }
        else
        {
            editConfirmPass.setError(null);
            confirmPassFilled = true;

        }

        if(passLamaFilled && passBaruFilled && confirmPassFilled)
        {
            sendData();
        }

    }


    public void sendData()
    {
        dialogLoading();

        final Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        SendPassword sendPassword = new SendPassword(""+sendPassBaru, ""+sendPassLama);
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseNormal2> userCall = service.editPassword(tokenBearer, sendPassword);
        userCall.enqueue(new Callback<ResponseNormal2>() {
            @Override
            public void onResponse(Call<ResponseNormal2> call, retrofit2.Response<ResponseNormal2> response) {
                loading.dismiss();
                if(response.isSuccessful()){

                    String myMessage = ""+response.body().getreason();
                    String status = ""+response.body().getstatus();
                    if(status.equals("success"))
                    {
                        dialogSuccess(""+response.body().getreason());
                    }
                    else
                    {
                        dialogWarning(""+myMessage, 2);
                    }



                }
                else {
                    // @M - If response from server is not success, get the message and show it in dialog
                    JSONObject jObjError = null;
                    String message = ""+getResources().getString(R.string.gagalCobaLagi);
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                        message = ""+jObjError.getString("reason");
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
                            dialogWarning(""+myMessage, 2);
                        }
                    };
                    handler.postDelayed(myRunnable,500);
                }
            }
            @Override
            public void onFailure(Call<ResponseNormal2> call, Throwable t) {
                loading.dismiss();
                dialogWarning(getResources().getString(R.string.gagalCobaLagi), 2);
            }
        });
    }

    public void dialogWarning(String message, final int opsi)
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
                if(opsi == 1)
                {
                    dialog.dismiss();
                    finish();
                    onBackPressed();
                }
                else
                {
                    dialog.dismiss();
                }
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
                finish();
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK,intent);
            }
        });
        dialog.show();
    }

}
