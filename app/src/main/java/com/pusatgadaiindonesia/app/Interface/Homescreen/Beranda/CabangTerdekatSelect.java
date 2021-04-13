package com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.util.Log;
import android.widget.Toast;

import com.pusatgadaiindonesia.app.Adapter.AdapterListCabang;
import com.pusatgadaiindonesia.app.Database.Data_Profile;
import com.pusatgadaiindonesia.app.Model.Location.DataLocation;
import com.pusatgadaiindonesia.app.Model.Location.ResponseLocation;
import com.pusatgadaiindonesia.app.R;
import com.pusatgadaiindonesia.app.Services.Retrofit.APIService;
import com.pusatgadaiindonesia.app.Services.Retrofit.ApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class CabangTerdekatSelect extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshLayout;
    AdapterListCabang myAdapter;
    List<DataLocation> listAllDataLocation = new ArrayList<>();
    String selectedCabangId = "";
    RecyclerView recyclerView;
    SearchView searchView;
    String keyword="";

    Boolean canGet = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_cabang_terdekat_select);

        searchView = findViewById(R.id.search);
        swipeRefreshLayout = findViewById(R.id.refresh);
        recyclerView = findViewById(R.id.rv);

        try {
            selectedCabangId = ""+getIntent().getStringExtra("id");
        } catch (Exception e) {
            e.printStackTrace();
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                loadLocation(keyword);
            }
        });

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        loadLocation("");
                                    }
                                }
        );

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new AdapterListCabang(this,recyclerView,listAllDataLocation, CabangTerdekatSelect.this, selectedCabangId);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                keyword = ""+searchView.getQuery();
                if(canGet == true)
                {
                    canGet = false;
                    loadLocation(""+searchView.getQuery());
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                keyword = ""+searchView.getQuery();
                if(canGet == true)
                {
                    canGet = false;
                    loadLocation(""+searchView.getQuery());
                }
                return false;
            }
        });
    }


    public void loadLocation(String keyword)
    {
        listAllDataLocation.clear();
        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        String baseUrl = ""+ApiClient.BASE_URL;

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseLocation> userCall = service.getAllLocation(tokenBearer, baseUrl+"location/branch?search="+keyword);
        userCall.enqueue(new Callback<ResponseLocation>() {
            @Override
            public void onResponse(Call<ResponseLocation> call, retrofit2.Response<ResponseLocation> response) {
                if(response.isSuccessful())
                {
                    String status = ""+response.body().getstatus();
                    if(status.equals("success"))
                    {
                        List<DataLocation> arrayku = response.body().getdata().getdata();
                        if(arrayku.size() > 0)
                        {
                            try {
                                Log.e("array",""+arrayku.size());
                                for (int  i=0; i<arrayku.size();  i++)
                                {
                                    DataLocation dataLocation = new DataLocation(
                                            ""+arrayku.get(i).getid(),
                                            ""+arrayku.get(i).getname(),
                                            ""+arrayku.get(i).getLatitude(),
                                            ""+arrayku.get(i).getLongitude(),
                                            ""+arrayku.get(i).getAddress(),
                                            ""+arrayku.get(i).getProvince(),
                                            ""+arrayku.get(i).getCity()
                                    );
                                    listAllDataLocation.add(dataLocation);
                                }
                                recyclerView.setAdapter(myAdapter);
                                myAdapter.notifyDataSetChanged();
                                myAdapter.setLoaded();
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("array",""+e.getMessage());
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Data Lokasi Kosong", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), ""+response.body().getmessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
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

                    Toast.makeText(getApplicationContext(), ""+message, Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayout.setRefreshing(false);
                canGet = true;
            }
            @Override
            public void onFailure(Call<ResponseLocation> call, Throwable t) {
                Log.w("Merk","Error : "+t.getMessage());
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getApplicationContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                canGet = true;
            }
        });
    }

    public void setData(String id, String nama, String alamat)
    {
        Intent intent = new Intent();
        intent.putExtra("id", ""+id);
        intent.putExtra("nama", ""+nama);
        intent.putExtra("alamat", ""+alamat);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    public void searchData(String keyword)
    {
        Toast.makeText(getApplicationContext(),""+keyword,Toast.LENGTH_SHORT).show();
    }
}
