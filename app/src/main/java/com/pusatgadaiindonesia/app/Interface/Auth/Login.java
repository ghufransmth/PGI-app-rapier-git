package com.pusatgadaiindonesia.app.Interface.Auth;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.pusatgadaiindonesia.app.Database.Data_Profile;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Homescreen;
import com.pusatgadaiindonesia.app.MainActivity;
import com.pusatgadaiindonesia.app.Model.Login.ResponseLogin;
import com.pusatgadaiindonesia.app.Model.Login.SendLogin;
import com.pusatgadaiindonesia.app.R;
import com.pusatgadaiindonesia.app.Services.Retrofit.APIService;
import com.pusatgadaiindonesia.app.Services.Retrofit.ApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class Login extends AppCompatActivity implements View.OnClickListener {

    // @M - initializing view with butterknife
    @BindView(R.id.masuk)
    LinearLayout masuk;

//    @BindView(R.id.registrasi)
//    TextView registrasi;


    @BindView(R.id.email)
    EditText editEmail;

    @BindView(R.id.password)
    EditText editPassword;

//    @BindView(R.id.forgot)
//    TextView forgot;


    Dialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_login);

        // @M - Binding
        ButterKnife.bind(this);
        editEmail.setFocusableInTouchMode(true);
        editEmail.requestFocus();
//        Data_Profile data = Data_Profile.findById(Data_Profile.class,1L);
//        if((int)data.count(Data_Profile.class, "", null) > 0) {
//            Intent intent = new Intent(Login.this, Homescreen.class);
//            startActivity(intent);
//        }

        masuk.setOnClickListener(this);
//        registrasi.setOnClickListener(this);
//        forgot.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.masuk:
                cekForm();
                /*Intent intent = new Intent(Login.this, Homescreen.class);
                startActivity(intent);*/
                break;

//            case R.id.registrasi:
//                Intent intent2 = new Intent(Login.this, Signup.class);
//                startActivity(intent2);
//                break;
//
//            case R.id.forgot:
//                Intent intentx = new Intent(Login.this, ForgotPassword.class);
//                startActivity(intentx);
//                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void cekForm()
    {
        Boolean emailEmpty = true;
        Boolean passwordEmpty = true;

        String getEmail = ""+editEmail.getText().toString();
        String getPassword = ""+editPassword.getText().toString();

        if(getEmail.equals("") || getEmail.equals("null"))
        {
            emailEmpty = true;
            editEmail.setError(""+getResources().getString(R.string.usernameEmailTidakBolehKosong));
        }
        else
        {
            emailEmpty = false;
            editEmail.setError(null);
        }

        if(getPassword.equals("") || getPassword.equals("null"))
        {
            passwordEmpty = true;
            editPassword.setError(""+getResources().getString(R.string.passwordTidakBolehKosong));
        }
        else
        {
            passwordEmpty = false;
            editPassword.setError(null);
        }

        if(emailEmpty == false && passwordEmpty == false)
        {
            sendData(getEmail, getPassword);
        }

    }

    public void sendData(String getEmail, String getPasssword)
    {
        dialogLoading();

        SendLogin sendLogin = new SendLogin(""+getEmail, ""+getPasssword,"client_credentials");
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseLogin> userCall = service.login(""+getEmail,""+getPasssword,"client_credentials");
        userCall.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, retrofit2.Response<ResponseLogin> response) {
                loading.dismiss();
                Log.w("LOGIN", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful()){

                    String myMessage = ""+response.body().getreason();
                    String status = ""+response.body().getstatus();
                    if(status.equals("true"))
                    {
                        Toast.makeText(getApplicationContext(),""+myMessage,Toast.LENGTH_SHORT).show();

                        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
                        data_profile.accessToken = "" + response.body().getdata().getaccessToken();
                        data_profile.refreshToken = "" + response.body().getdata().getrefreshToken();
                        data_profile.accessTokenExpAt = "" + response.body().getdata().getAccessTokenExpAt();
                        data_profile.refreshTokenExpAt = "" + response.body().getdata().getrefreshTokenExpAt();
                        data_profile.mustChangePassword = "" + response.body().getdata().getmustChangePassword();
//                        data_profile.token = "" + response.body().getdata().gettoken();
//                        data_profile.userId = "" + response.body().getdata().getuserId();
//                        data_profile.userName = "" + response.body().getdata().getuserName();
//                        data_profile.userEmail = "" + response.body().getdata().getuserEmail();
//                        data_profile.login = true;
                        data_profile.save();

                        finish();
                        Intent intent = new Intent(Login.this, Homescreen.class);
                        startActivity(intent);
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
                            dialogWarning(""+myMessage);
                        }
                    };
                    handler.postDelayed(myRunnable,500);
                }
            }
            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                loading.dismiss();
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
