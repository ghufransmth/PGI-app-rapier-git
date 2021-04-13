package com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
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
import com.pusatgadaiindonesia.app.Database.Data_Profile;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Homescreen;
import com.pusatgadaiindonesia.app.Model.BayarListrik.SendListrik;
import com.pusatgadaiindonesia.app.Model.ResponseNormal;
import com.pusatgadaiindonesia.app.R;
import com.pusatgadaiindonesia.app.Services.Retrofit.APIService;
import com.pusatgadaiindonesia.app.Services.Retrofit.ApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class BeliPulsaListrik extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.tipe)
    LinearLayout layTipe;

    @BindView(R.id.idMeter)
    EditText editIdMeter;

    @BindView(R.id.nominal)
    LinearLayout layNominal;

    @BindView(R.id.lanjut)
    CardView bayar;

    @BindView(R.id.textListrik)
    TextView textListrik;

    @BindView(R.id.textNominal)
    TextView textNominal;

    String sendTipe = "prabayar";
    String sendNominal = "20000";

    String selectedListrikId = "1";
    String selectedNominalId = "1";
    
    Dialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_beli_pulsa_listrik);
        ButterKnife.bind(this);

        back.setOnClickListener(this);
        layTipe.setOnClickListener(this);
        layNominal.setOnClickListener(this);
        bayar.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.back:
                onBackPressed();
                break;

            case R.id.tipe:
                showListrik();
                break;

            case R.id.nominal:
                showListrikNominal();
                break;

            case R.id.lanjut:
                String idMeter = ""+editIdMeter.getText().toString();
                if(idMeter.equals("") || idMeter.equals(null))
                {
                    editIdMeter.setError("Mohon masukkan no. meter / id pelanggan Anda");
                }
                else
                {
                    sendData(idMeter);
                }
                break;
        }
    }

    public void showListrik()
    {
        final DialogPlus dialog = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(R.layout.model_dialog_listrik))
                .setGravity(Gravity.BOTTOM)
                .setInAnimation(R.anim.slide_in_bottom)
                .setOutAnimation(R.anim.slide_out_bottom)
                .setBackgroundColorResId(Color.parseColor("#00000000"))
                .create();

        View view = dialog.getHolderView();

        final TextView s1 = view.findViewById(R.id.s1);
        final TextView s2 = view.findViewById(R.id.s2);

        if (selectedListrikId.equals("1"))
        {
            s1.setTextColor(getResources().getColor(R.color.blue_selected));
        }
        else if (selectedListrikId.equals("2"))
        {
            s2.setTextColor(getResources().getColor(R.color.blue_selected));
        }

        s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textListrik.setText(s1.getText().toString());
                selectedListrikId="1";
                sendTipe = "prabayar";
                dialog.dismiss();
            }
        });

        s2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textListrik.setText(s2.getText().toString());
                selectedListrikId="2";
                sendTipe = "pascabayar";
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void showListrikNominal()
    {
        final DialogPlus dialog = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(R.layout.model_dialog_listrik_nominal))
                .setGravity(Gravity.BOTTOM)
                .setInAnimation(R.anim.slide_in_bottom)
                .setOutAnimation(R.anim.slide_out_bottom)
                .setBackgroundColorResId(Color.parseColor("#00000000"))
                .create();

        View view = dialog.getHolderView();

        final LinearLayout s1 = view.findViewById(R.id.s1);
        final LinearLayout s2 = view.findViewById(R.id.s2);
        final LinearLayout s3 = view.findViewById(R.id.s3);
        final LinearLayout s4 = view.findViewById(R.id.s4);
        final LinearLayout s5 = view.findViewById(R.id.s5);
        final LinearLayout s6 = view.findViewById(R.id.s6);
        final LinearLayout s7 = view.findViewById(R.id.s7);

        final TextView s1a = view.findViewById(R.id.s1a);
        final TextView s2a = view.findViewById(R.id.s2a);
        final TextView s3a = view.findViewById(R.id.s3a);
        final TextView s4a = view.findViewById(R.id.s4a);
        final TextView s5a = view.findViewById(R.id.s5a);
        final TextView s6a = view.findViewById(R.id.s6a);
        final TextView s7a = view.findViewById(R.id.s7a);

        final TextView s1b = view.findViewById(R.id.s1b);
        final TextView s2b = view.findViewById(R.id.s2b);
        final TextView s3b = view.findViewById(R.id.s3b);
        final TextView s4b = view.findViewById(R.id.s4b);
        final TextView s5b = view.findViewById(R.id.s5b);
        final TextView s6b = view.findViewById(R.id.s6b);
        final TextView s7b = view.findViewById(R.id.s7b);

        if (selectedNominalId.equals("1"))
        {
            s1a.setTextColor(getResources().getColor(R.color.blue_selected));
            s1b.setTextColor(getResources().getColor(R.color.blue_selected));
        }
        else if (selectedNominalId.equals("2"))
        {
            s2a.setTextColor(getResources().getColor(R.color.blue_selected));
            s2b.setTextColor(getResources().getColor(R.color.blue_selected));
        }
        else if (selectedNominalId.equals("3"))
        {
            s3a.setTextColor(getResources().getColor(R.color.blue_selected));
            s3b.setTextColor(getResources().getColor(R.color.blue_selected));
        }
        else if (selectedNominalId.equals("4"))
        {
            s4a.setTextColor(getResources().getColor(R.color.blue_selected));
            s4b.setTextColor(getResources().getColor(R.color.blue_selected));
        }
        else if (selectedNominalId.equals("5"))
        {
            s5a.setTextColor(getResources().getColor(R.color.blue_selected));
            s5b.setTextColor(getResources().getColor(R.color.blue_selected));
        }
        else if (selectedNominalId.equals("6"))
        {
            s6a.setTextColor(getResources().getColor(R.color.blue_selected));
            s6b.setTextColor(getResources().getColor(R.color.blue_selected));
        }
        else if (selectedNominalId.equals("7"))
        {
            s7a.setTextColor(getResources().getColor(R.color.blue_selected));
            s7b.setTextColor(getResources().getColor(R.color.blue_selected));
        }

        s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textNominal.setText(s1b.getText().toString());
                selectedNominalId="1";
                sendNominal="20000";
                dialog.dismiss();
            }
        });
        s1a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textNominal.setText(s1b.getText().toString());
                selectedNominalId="1";
                sendNominal="20000";
                dialog.dismiss();
            }
        });
        s1b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textNominal.setText(s1b.getText().toString());
                selectedNominalId="1";
                sendNominal="20000";
                dialog.dismiss();
            }
        });

        s2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textNominal.setText(s2b.getText().toString());
                selectedNominalId="2";
                sendNominal="50000";
                dialog.dismiss();
            }
        });
        s2a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textNominal.setText(s2b.getText().toString());
                selectedNominalId="2";
                sendNominal="50000";
                dialog.dismiss();
            }
        });
        s2b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textNominal.setText(s2b.getText().toString());
                selectedNominalId="2";
                sendNominal="50000";
                dialog.dismiss();
            }
        });

        s3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textNominal.setText(s3b.getText().toString());
                selectedNominalId="3";
                sendNominal="100000";
                dialog.dismiss();
            }
        });
        s3a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textNominal.setText(s3b.getText().toString());
                selectedNominalId="3";
                sendNominal="100000";
                dialog.dismiss();
            }
        });
        s3b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textNominal.setText(s3b.getText().toString());
                selectedNominalId="3";
                sendNominal="100000";
                dialog.dismiss();
            }
        });

        s4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textNominal.setText(s4b.getText().toString());
                selectedNominalId="4";
                sendNominal="200000";
                dialog.dismiss();
            }
        });
        s4a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textNominal.setText(s4b.getText().toString());
                selectedNominalId="4";
                sendNominal="200000";
                dialog.dismiss();
            }
        });
        s4b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textNominal.setText(s4b.getText().toString());
                selectedNominalId="4";
                sendNominal="200000";
                dialog.dismiss();
            }
        });

        s5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textNominal.setText(s5b.getText().toString());
                selectedNominalId="5";
                sendNominal="500000";
                dialog.dismiss();
            }
        });
        s5a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textNominal.setText(s5b.getText().toString());
                selectedNominalId="5";
                sendNominal="500000";
                dialog.dismiss();
            }
        });
        s5b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textNominal.setText(s5b.getText().toString());
                selectedNominalId="5";
                sendNominal="500000";
                dialog.dismiss();
            }
        });

        s6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textNominal.setText(s6b.getText().toString());
                selectedNominalId="6";
                sendNominal="1000000";
                dialog.dismiss();
            }
        });
        s6a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textNominal.setText(s6b.getText().toString());
                selectedNominalId="6";
                sendNominal="1000000";
                dialog.dismiss();
            }
        });
        s6b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textNominal.setText(s6b.getText().toString());
                selectedNominalId="6";
                sendNominal="1000000";
                dialog.dismiss();
            }
        });

        s7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textNominal.setText(s7b.getText().toString());
                selectedNominalId="7";
                sendNominal="500000";
                dialog.dismiss();
            }
        });
        s7a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textNominal.setText(s7b.getText().toString());
                selectedNominalId="7";
                sendNominal="500000";
                dialog.dismiss();
            }
        });
        s7b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textNominal.setText(s7b.getText().toString());
                selectedNominalId="7";
                sendNominal="500000";
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
                finish();
                Intent intent = new Intent(BeliPulsaListrik.this, Homescreen.class);
                startActivity(intent);
            }
        });
        dialog.show();
    }
    
    public void sendData(String idMeter)
    {
        dialogLoading();

        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        SendListrik sendListrik = new SendListrik(""+sendTipe, ""+idMeter, "description", ""+sendNominal);
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseNormal> userCall = service.bayarListrik(tokenBearer, sendListrik);
        userCall.enqueue(new Callback<ResponseNormal>() {
            @Override
            public void onResponse(Call<ResponseNormal> call, retrofit2.Response<ResponseNormal> response) {
                loading.dismiss();
                //Log.w("Bayar", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful()){
                    //Toast.makeText(getApplicationContext(),"Sukses "+response.body().getstatus(),Toast.LENGTH_SHORT).show();

                    String myMessage = ""+response.body().getmessage();
                    String status = ""+response.body().getstatus();
                    if(status.equals("success"))
                    {
                        dialogSuccess(""+myMessage);
                    }
                    else
                    {
                        dialogWarning(""+myMessage);
                    }
                }
                else {
                    //Toast.makeText(getApplicationContext(),"Bukan 200 ",Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<ResponseNormal> call, Throwable t) {
                loading.dismiss();
                dialogWarning(getResources().getString(R.string.gagalCobaLagi));
                //Toast.makeText(getApplicationContext(),"Failure  "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
