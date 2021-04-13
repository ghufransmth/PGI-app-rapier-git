package com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda;

import android.app.Activity;
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
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.pusatgadaiindonesia.app.Adapter.AdapterNotifikasi;
import com.pusatgadaiindonesia.app.Database.Data_Profile;
import com.pusatgadaiindonesia.app.Model.Notification.DataNotificationDetail;
import com.pusatgadaiindonesia.app.Model.Notification.ResponseNotification;
import com.pusatgadaiindonesia.app.Model.ResponseNormal2;
import com.pusatgadaiindonesia.app.R;
import com.pusatgadaiindonesia.app.Services.Retrofit.APIService;
import com.pusatgadaiindonesia.app.Services.Retrofit.ApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class Notifikasi extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.rv)
    RecyclerView recyclerView;

    @BindView(R.id.refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    AdapterNotifikasi myAdapter;
    List<DataNotificationDetail> listAllData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_notifikasi);
        ButterKnife.bind(this);

        back.setOnClickListener(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                getNotif();
            }
        });
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        getNotif();
                                    }
                                }
        );

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new AdapterNotifikasi(this,recyclerView,listAllData,this);

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
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    public void setHasRead(String id){
        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseNormal2> userCall = service.setReadNotification(tokenBearer,"/user/notifikasi/"+id+"/read");
        userCall.enqueue(new Callback<ResponseNormal2>() {
            @Override
            public void onResponse(Call<ResponseNormal2> call, retrofit2.Response<ResponseNormal2> response) {
                swipeRefreshLayout.setRefreshing(false);
                //Log.w("", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful())
                {
                    String myMessage = ""+response.body().getreason();
                    String status = ""+response.body().getstatus();
                    if(status.equals("true"))
                    {

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
                swipeRefreshLayout.setRefreshing(false);
                dialogWarning(getResources().getString(R.string.gagalCobaLagi));
            }
        });
    }

    public void getNotif()
    {
        listAllData.clear();
        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseNotification> userCall = service.getNotification(tokenBearer,"user/notifikasi?page=0&size=10");
        userCall.enqueue(new Callback<ResponseNotification>() {
            @Override
            public void onResponse(Call<ResponseNotification> call, retrofit2.Response<ResponseNotification> response) {
                swipeRefreshLayout.setRefreshing(false);
                //Log.w("", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful())
                {
                    String myMessage = ""+response.body().getreason();
                    String status = ""+response.body().getstatus();
                    if(status.equals("true"))
                    {
                        try {
                            List<DataNotificationDetail> myList = null;
                            myList = (List<DataNotificationDetail>) response.body().getdata().getdata();

                            if(myList.size()==0)
                            {

                            }
                            else
                            {
                                for (int i = 0; i < myList.size(); i++)
                                {
                                    // @M - Add data to model "DataCommisionJob"
                                    DataNotificationDetail dataNotificationDetail = new DataNotificationDetail(
                                            ""+myList.get(i).getid(),
                                            ""+myList.get(i).getgadaiId(),
                                            ""+myList.get(i).getmessage(),
                                            ""+myList.get(i).getcreatedAt(),
                                            ""+myList.get(i).getread()
                                    );
                                    // @M - Add to List
                                    listAllData.add(dataNotificationDetail);
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
            public void onFailure(Call<ResponseNotification> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                dialogWarning(getResources().getString(R.string.gagalCobaLagi));
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
}
