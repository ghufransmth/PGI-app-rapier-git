//package com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.content.pm.ActivityInfo;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.SearchView;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
//
//import com.pusatgadaiindonesia.app.Adapter.AdapterMasterElektronik2;
//import com.pusatgadaiindonesia.app.Database.Data_Profile;
//import com.pusatgadaiindonesia.app.Model.Barang.DataBarang;
//import com.pusatgadaiindonesia.app.Model.Barang.ResponseBarang;
//import com.pusatgadaiindonesia.app.Model.Status.DataStatus;
//import com.pusatgadaiindonesia.app.R;
//import com.pusatgadaiindonesia.app.Services.Retrofit.APIService;
//import com.pusatgadaiindonesia.app.Services.Retrofit.ApiClient;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//
//public class RiwayatBayarDetail extends AppCompatActivity implements View.OnClickListener{
//
//    SwipeRefreshLayout swipeRefreshLayout;
//    AdapterMasterElektronik2 myAdapter;
//    List<DataBarang> listAllDataStatus = new ArrayList<>();
//    String selectedId = "", selectedVal = "";
//    RecyclerView recyclerView;
//    SearchView searchView;
//    String keyword="";
//
//    Boolean canGet = true;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        setContentView(R.layout.activity_cabang_terdekat_select);
//
//        searchView = findViewById(R.id.search);
//        swipeRefreshLayout = findViewById(R.id.refresh);
//        recyclerView = findViewById(R.id.rv);
//
//        try {
//            selectedId = ""+getIntent().getStringExtra("id");
//            selectedVal = ""+getIntent().getStringExtra("val");
////            Log.d("",""+selectedId);
////            Log.d("",""+selectedVal);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipeRefreshLayout.setRefreshing(true);
////                loadGadai(keyword);
//            }
//        });
//
//        swipeRefreshLayout.post(new Runnable() {
//                @Override
//                public void run() {
//                    swipeRefreshLayout.setRefreshing(true);
//
//
//                }
//            }
//        );
//
//        listAllDataStatus.add(new DataBarang("1", "Mashu"));
//        listAllDataStatus.add(new DataBarang("2", "Berhasil"));
//        listAllDataStatus.add(new DataBarang("3", "Pending"));
//        listAllDataStatus.add(new DataBarang("4", "Batal"));
//
//
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        myAdapter = new AdapterMasterElektronik2(this,recyclerView,listAllDataStatus,RiwayatBayarDetail.this, selectedId,selectedVal);
//        recyclerView.setAdapter(listAllDataStatus);
//        myAdapter.notifyDataSetChanged();
//        myAdapter.setLoaded();
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId())
//        {
////            case R.id.back:
////                onBackPressed();
////                break;
//        }
//    }
//
//    @Override
//    public void onBackPressed() {
//        finish();
//    }
//
//}
