package com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda;

import android.app.Dialog;
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
import com.pusatgadaiindonesia.app.Adapter.AdapterNews;
import com.pusatgadaiindonesia.app.Database.Data_Profile;
import com.pusatgadaiindonesia.app.Model.News.DataNewsDetail;
import com.pusatgadaiindonesia.app.Model.News.ResponseNews;
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

public class NewsFeed extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.rv)
    RecyclerView recyclerView;

    @BindView(R.id.refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    AdapterNews myAdapter;
    List<DataNewsDetail> listAllData = new ArrayList<>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_news_feed);
        ButterKnife.bind(this);

        back.setOnClickListener(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                getNews();
            }
        });
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        getNews();
                                    }
                                }
        );

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new AdapterNews(this,recyclerView,listAllData);
        
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


    public void getNews()
    {
        listAllData.clear();

        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseNews> userCall = service.getNews(tokenBearer);
        userCall.enqueue(new Callback<ResponseNews>() {
            @Override
            public void onResponse(Call<ResponseNews> call, retrofit2.Response<ResponseNews> response) {
                swipeRefreshLayout.setRefreshing(false);
                Log.w("", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful())
                {
                    String myMessage = ""+response.body().getmessage();
                    String status = ""+response.body().getstatus();
                    if(status.equals("success"))
                    {
                        try {
                            List<DataNewsDetail> myList = null;
                            myList = (List<DataNewsDetail>) response.body().getdata().getdata();

                            if(myList.size()==0)
                            {

                            }
                            else
                            {
                                for (int i = 0; i < myList.size(); i++)
                                {
                                    // @M - Add data to model "DataCommisionJob"
                                    DataNewsDetail DataNewsDetail = new DataNewsDetail(
                                            ""+myList.get(i).getid(),
                                            ""+myList.get(i).gettitle(),
                                            ""+myList.get(i).getdescription(),
                                            ""+myList.get(i).getcreatedAt(),
                                            ""+myList.get(i).getStatus()
                                    );
                                    // @M - Add to List
                                    listAllData.add(DataNewsDetail);
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
            public void onFailure(Call<ResponseNews> call, Throwable t) {
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
