package com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.pusatgadaiindonesia.app.Adapter.AdapterListCabang;
import com.pusatgadaiindonesia.app.Adapter.AdapterMasterElektronik;
import com.pusatgadaiindonesia.app.Adapter.AdapterMasterElektronik2;
import com.pusatgadaiindonesia.app.Database.Data_Profile;
import com.pusatgadaiindonesia.app.Model.Barang.DataBarang;
import com.pusatgadaiindonesia.app.Model.Barang.ResponseBarang;
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

public class GadaiDetail extends AppCompatActivity implements View.OnClickListener{

    SwipeRefreshLayout swipeRefreshLayout;
    AdapterMasterElektronik2 myAdapter;
    List<DataBarang> listAllDataBarang = new ArrayList<>();
    String selectedId = "", selectedVal = "0";
    RecyclerView recyclerView;
    SearchView searchView;
    String keyword="";

    Boolean canGet = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_gadai_select);

        searchView = findViewById(R.id.search);
//        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
//        TextView searchText = (TextView) searchView.findViewById(id);
//        Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.century_gothic);
//        searchText.setTypeface(typeface);

        swipeRefreshLayout = findViewById(R.id.refresh);
        recyclerView = findViewById(R.id.rv);


        try {
            selectedId = ""+getIntent().getStringExtra("id");
            selectedVal = ""+getIntent().getStringExtra("val");
//            Log.d("",""+selectedId);
//            Log.d("",""+selectedVal);
        } catch (Exception e) {
            e.printStackTrace();
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
//                loadGadai(keyword);
            }
        });

        swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                    if(selectedVal.equals("1")){
                        loadGadai("/barang/jenis/elektronik","");
                    }else if(selectedVal.equals("2")){
                        loadGadai("/barang/merk/",selectedId);
                    }else if(selectedVal.equals("3")){
                        loadGadai("/barang/tipe/",selectedId);
                    }else if(selectedVal.equals("4")){
                        loadGadai("/barang/warna/","");
                    }else if(selectedVal.equals("5")){
                        loadGadai("/barang/tahun/",selectedId);
                    }else if(selectedVal.equals("6")){
                        loadGadai("/barang/kendaraan","");
                    }else if(selectedVal.equals("7")){
                        listAllDataBarang.clear();
                        swipeRefreshLayout.setRefreshing(false);
                        listAllDataBarang.add(new DataBarang("1", "Semua"));
                        listAllDataBarang.add(new DataBarang("2", "Berhasil"));
                        listAllDataBarang.add(new DataBarang("3", "Pending"));
                        listAllDataBarang.add(new DataBarang("4", "Batal"));
                        recyclerView.setAdapter(myAdapter);
                        myAdapter.notifyDataSetChanged();
                        myAdapter.setLoaded();
                    }else{

                    }

                }
            }
        );


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new AdapterMasterElektronik2(this,recyclerView,listAllDataBarang,GadaiDetail.this, selectedId,selectedVal);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                //Log.d("text: ",""+query);
                myAdapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                //Log.d("text: ",""+query);
                myAdapter.filter(query);
                return false;
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
//            case R.id.back:
//                onBackPressed();
//                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }


    public void loadGadai(String url, String id)
    {
        listAllDataBarang.clear();
        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseBarang> userCall = service.getAllBarang(tokenBearer, ""+url+id);
        userCall.enqueue(new Callback<ResponseBarang>() {
            @Override
            public void onResponse(Call<ResponseBarang> call, retrofit2.Response<ResponseBarang> response) {
                if(response.isSuccessful())
                {
                    String status = ""+response.body().getstatus();
                    if(status.equals("true"))
                    {
                        List<DataBarang> arrayku = response.body().getdata();
                        if(arrayku.size() > 0)
                        {
                            try {
                                Log.e("array",""+arrayku.size());
                                for (int  i=0; i<arrayku.size();  i++)
                                {
                                    DataBarang dataBarang = new DataBarang(
                                            ""+arrayku.get(i).getid(),
                                            ""+arrayku.get(i).getname()
                                    );
                                    listAllDataBarang.add(dataBarang);
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
                            Toast.makeText(getApplicationContext(), "Data Kosong", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), ""+response.body().getreason(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<ResponseBarang> call, Throwable t) {
                Log.w("Merk","Error : "+t.getMessage());
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getApplicationContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                canGet = true;
            }
        });
    }

    public void setData(String id, String nama)
    {
        Intent intent = new Intent();
        intent.putExtra("id", ""+id);
        intent.putExtra("nama", ""+nama);
        intent.putExtra("val", ""+selectedVal);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    public void searchData(String keyword)
    {
        Toast.makeText(getApplicationContext(),""+keyword,Toast.LENGTH_SHORT).show();
    }

}
