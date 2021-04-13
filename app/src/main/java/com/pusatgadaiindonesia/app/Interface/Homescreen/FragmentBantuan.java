package com.pusatgadaiindonesia.app.Interface.Homescreen;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.pusatgadaiindonesia.app.Database.Data_Profile;
import com.pusatgadaiindonesia.app.Model.Complain.ResponseComplain;
import com.pusatgadaiindonesia.app.Model.Complain.SendComplain;
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

public class FragmentBantuan extends Fragment implements View.OnClickListener {

    View rootview;

    RelativeLayout keluhan, saran;
    LinearLayout keluhanSelected, saranSelected;

    EditText input;
    LinearLayout send;
    
    String pilihan = "kritik";
    Dialog loading;

    public FragmentBantuan() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootview == null)
        {

            rootview = inflater.inflate(R.layout.activity_fragment_bantuan, container, false);

            ButterKnife.bind(this, rootview);
            keluhan = rootview.findViewById(R.id.keluhan);
            saran = rootview.findViewById(R.id.saran);
            input = rootview.findViewById(R.id.input);
            keluhanSelected = rootview.findViewById(R.id.keluhanSelected);
            saranSelected = rootview.findViewById(R.id.saranSelected);
            send = rootview.findViewById(R.id.send);

            keluhan.setOnClickListener(this);
            saran.setOnClickListener(this);
            send.setOnClickListener(this);

        }
        else
        {

        }
        return rootview;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.keluhan:
                keluhanSelected.setVisibility(View.VISIBLE);
                saranSelected.setVisibility(View.GONE);
                pilihan = "kritik";
                break;

            case R.id.saran:
                keluhanSelected.setVisibility(View.GONE);
                saranSelected.setVisibility(View.VISIBLE);
                pilihan = "saran";
                break;

            case R.id.send:
                cekForm();
                break;
        }
    }
    
    public void cekForm()
    {
        String getInput = ""+input.getText().toString();

        if(getInput.equals("") || getInput.equals("null"))
        {
            input.setError("Keluhan / Saran tidak boleh kosong");
        }
        else
        {
            input.setError(null);
            sendData(getInput);
        }

    }


    public void sendData(String getInput)
    {
        dialogLoading();

        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        SendComplain sendComplain = new SendComplain(""+data_profile.nik, ""+data_profile.nama,""+data_profile.noHandphone,
                ""+pilihan,""+getInput);
        APIService service = ApiClient.getClient2().create(APIService.class);
        Call<ResponseComplain> userCall = service.complain(""+data_profile.nik,""+data_profile.nama,""+data_profile.noHandphone,""+pilihan,""+getInput);
        userCall.enqueue(new Callback<ResponseComplain>() {
            @Override
            public void onResponse(Call<ResponseComplain> call, retrofit2.Response<ResponseComplain> response) {
                loading.dismiss();
                //Log.w("", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful()){

                    String myMessage = ""+response.body().getmassage();
                    String status = ""+response.body().getstatus();
                    if(status.equals("1"))
                    {
                        input.setText("");
                        dialogSuccess(""+myMessage);
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
                        message = ""+jObjError.getString("massage");
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
            public void onFailure(Call<ResponseComplain> call, Throwable t) {
                loading.dismiss();
                dialogWarning(getResources().getString(R.string.gagalCobaLagi));
            }
        });
    }

    public void dialogLoading()
    {
        // @M - showing the loading dialog
        loading = new Dialog(getActivity());
        loading.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loading.setContentView(R.layout.dialog_loading);
        loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loading.show();
    }

    public void dialogSuccess(String message)
    {
        // @M - showing the warning dialog in old style dialog
        final Dialog dialog = new Dialog(getActivity());
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

    public void dialogWarning(String message)
    {
        // @M - showing the warning dialog in old style dialog
        final Dialog dialog = new Dialog(getActivity());
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
