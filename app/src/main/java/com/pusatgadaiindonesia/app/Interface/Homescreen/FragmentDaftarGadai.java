package com.pusatgadaiindonesia.app.Interface.Homescreen;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.pusatgadaiindonesia.app.Adapter.AdapterDataAktif;
import com.pusatgadaiindonesia.app.Adapter.AdapterNotifikasi;
import com.pusatgadaiindonesia.app.Adapter.AdapterRiwayatGadai;
import com.pusatgadaiindonesia.app.Database.Data_Profile;
import com.pusatgadaiindonesia.app.Model.Gadai.DataGadai;
import com.pusatgadaiindonesia.app.Model.Gadai.DataGadaiBerjalan;
import com.pusatgadaiindonesia.app.Model.Gadai.DataGadaiLunasLelang;
import com.pusatgadaiindonesia.app.Model.Gadai.ResponseGadai;
import com.pusatgadaiindonesia.app.Model.Gadai.ResponseGadai2;
import com.pusatgadaiindonesia.app.Model.Notification.DataNotificationDetail;
import com.pusatgadaiindonesia.app.Model.Notification.ResponseNotification;
import com.pusatgadaiindonesia.app.R;
import com.pusatgadaiindonesia.app.Services.Retrofit.APIService;
import com.pusatgadaiindonesia.app.Services.Retrofit.ApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class FragmentDaftarGadai extends Fragment implements View.OnClickListener {

    View rootview;

    LinearLayout layAktif, layRiwayat;
    RelativeLayout dataAktif, riwayatGadai;
    LinearLayout aktifSelected, riwayatSelected;
    TextView jmlTunggakan2;
    ImageView bayar;
    CheckBox checkBox;
    SwipeRefreshLayout swipeRefreshLayout, swipeRefreshLayout2;
    RecyclerView recyclerView, recyclerView2;
    AdapterDataAktif myAdapter;
    AdapterRiwayatGadai myAdapter2;
    List<DataGadaiBerjalan> listAllData = new ArrayList<>();
    List<DataGadaiLunasLelang> listAllData2 = new ArrayList<>();
    String selectedNoFaktur = "", selectedJenis = "", selectedNominal = "", selectedJatuhTempo = "";
    int hitung = 0;

    public FragmentDaftarGadai() {
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

            rootview = inflater.inflate(R.layout.activity_fragment_aktif_gadai, container, false);

            //SetUp layout To Specific
            /*card = rootview.findViewById(R.id.card);

            DisplayMetrics dm = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(dm);

            int width  = dm.widthPixels;
            int height = dm.heightPixels;

            int widthCard = width + dpToPx(75);
            int heightCard = height + dpToPx(17);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(widthCard, heightCard);
            params.setMargins(dpToPx(-75), 0, 0, dpToPx(-75));
            card.setLayoutParams(params);*/

            layAktif = rootview.findViewById(R.id.layAktif);
            layRiwayat = rootview.findViewById(R.id.layRiwayat);
            jmlTunggakan2 = rootview.findViewById(R.id.jmlTunggakan);
            dataAktif = rootview.findViewById(R.id.dataAktif);
            riwayatGadai = rootview.findViewById(R.id.riwayatGadai);
            aktifSelected = rootview.findViewById(R.id.aktifSelected);
            riwayatSelected = rootview.findViewById(R.id.riwayatSelected);
            swipeRefreshLayout = rootview.findViewById(R.id.refresh);
            swipeRefreshLayout2 = rootview.findViewById(R.id.refresh2);
            recyclerView = rootview.findViewById(R.id.rv);
            recyclerView2 = rootview.findViewById(R.id.rv2);
            checkBox = rootview.findViewById(R.id.checkbox);

            dataAktif.setOnClickListener(this);
            riwayatGadai.setOnClickListener(this);
            layAktif.setOnClickListener(this);
            layRiwayat.setOnClickListener(this);

            try {
                String cek = getActivity().getIntent().getStringExtra("frgToLoad");
//                Log.d("frgToLoad :", ""+cek);
                if(cek != null){
                    if(cek.equals("1")){
                        layAktif.setVisibility(View.VISIBLE);
                        layRiwayat.setVisibility(View.GONE);
                        aktifSelected.setVisibility(View.VISIBLE);
                        riwayatSelected.setVisibility(View.GONE);
                    }else if(cek.equals("2")){
                        layAktif.setVisibility(View.GONE);
                        layRiwayat.setVisibility(View.VISIBLE);
                        aktifSelected.setVisibility(View.GONE);
                        riwayatSelected.setVisibility(View.VISIBLE);
                    }else{

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeRefreshLayout.setRefreshing(true);
                    hitung = 0;
                    getDataAktif();
                }
            });
            swipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(true);
                        getDataAktif();
                    }
                }
            );
            swipeRefreshLayout2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeRefreshLayout2.setRefreshing(true);
                    getRiwayatGadai();
                }
            });
            swipeRefreshLayout2.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout2.setRefreshing(true);
                        getRiwayatGadai();
                    }
                }
            );

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            myAdapter = new AdapterDataAktif(getContext(),recyclerView,listAllData);

            recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
            myAdapter2 = new AdapterRiwayatGadai(getContext(),recyclerView,listAllData2);

//            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if(isChecked)
//                    {
//                        bayar.setBackgroundResource(R.drawable.button_bayar);
//                    }
//                    else
//                    {
//                        bayar.setBackgroundResource(R.drawable.button_bayar_grey);
//                    }
//                }
//            });
        }
        else
        {

        }
        return rootview;
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.dataAktif:
                layAktif.setVisibility(View.VISIBLE);
                layRiwayat.setVisibility(View.GONE);
                aktifSelected.setVisibility(View.VISIBLE);
                riwayatSelected.setVisibility(View.GONE);
                break;

            case R.id.riwayatGadai:
                layAktif.setVisibility(View.GONE);
                layRiwayat.setVisibility(View.VISIBLE);
                aktifSelected.setVisibility(View.GONE);
                riwayatSelected.setVisibility(View.VISIBLE);
                break;

            case R.id.layAktif:

                break;

            case R.id.layRiwayat:
                dialogLunas();
                break;

//            case R.id.bayar:
//                if(checkBox.isChecked())
//                {
//                    dialogCicilan();
//                }
//                else
//                {
//                    Toast.makeText(getActivity(),"Pilih barang terlebih dahulu",Toast.LENGTH_SHORT).show();
//                }
//
//                break;
        }
    }

    public void getDataAktif()
    {
        listAllData.clear();
        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseGadai> userCall = service.getDataAktif(tokenBearer,"gadai/berjalan?page=0&size=10");
        userCall.enqueue(new Callback<ResponseGadai>() {
            @Override
            public void onResponse(Call<ResponseGadai> call, retrofit2.Response<ResponseGadai> response) {
                swipeRefreshLayout.setRefreshing(false);
                //Log.w("", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful())
                {
                    String myMessage = ""+response.body().getreason();
                    String status = ""+response.body().getstatus();
                    if(status.equals("true"))
                    {
                        try {
                            List<DataGadaiBerjalan> myList = null;
                            myList = (List<DataGadaiBerjalan>) response.body().getdata().getdata();

                            if(myList.size()==0)
                            {

                            }
                            else
                            {
                                hitung = 0;
                                for (int i = 0; i < myList.size(); i++)
                                {
                                    // @M - Add data to model "DataCommisionJob"
                                    DataGadaiBerjalan dataGadaiBerjalan = new DataGadaiBerjalan(
                                            ""+myList.get(i).getid(),
                                            ""+myList.get(i).getnoFaktur(),
                                            ""+myList.get(i).gettipe(),
                                            ""+myList.get(i).getmerk(),
                                            ""+myList.get(i).getjenisBarang(),
                                            ""+myList.get(i).getjenisPinjaman(),
                                            ""+myList.get(i).getstatus(),
                                            ""+myList.get(i).getnilaiPinjamanAwal(),
                                            ""+myList.get(i).getnilaiPinjamanEfektif(),
                                            ""+myList.get(i).gettanggalGadai(),
                                            ""+myList.get(i).getimeiSn(),
                                            ""+myList.get(i).gettanggalJatuhTempo(),
                                            ""+myList.get(i).getwarna(),
                                            ""+myList.get(i).gettahun()
                                    );
                                    hitung += Integer.parseInt(myList.get(i).getnilaiPinjamanEfektif());
                                    // @M - Add to List
                                    listAllData.add(dataGadaiBerjalan);
                                }
                                jmlTunggakan2.setText("Rp. "+currencyFormat(""+hitung));
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
                        message = ""+jObjError.getString("reason");
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
            public void onFailure(Call<ResponseGadai> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                dialogWarning(getResources().getString(R.string.gagalCobaLagi));
            }
        });
    }

    public static String currencyFormat(String amount) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0");
        return formatter.format(Double.parseDouble(amount));
    }

    public void getRiwayatGadai()
    {
        listAllData2.clear();
        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseGadai2> userCall = service.getRiwayatGadai(tokenBearer,"gadai/lunas-lelang?page=0&size=10");
        userCall.enqueue(new Callback<ResponseGadai2>() {
            @Override
            public void onResponse(Call<ResponseGadai2> call, retrofit2.Response<ResponseGadai2> response) {
                swipeRefreshLayout2.setRefreshing(false);
                //Log.w("", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful())
                {
                    String myMessage = ""+response.body().getreason();
                    String status = ""+response.body().getstatus();
                    if(status.equals("true"))
                    {
                        try {
                            List<DataGadaiLunasLelang> myList = null;
                            myList = (List<DataGadaiLunasLelang>) response.body().getdata().getdata();

                            if(myList.size()==0)
                            {

                            }
                            else
                            {
                                for (int i = 0; i < myList.size(); i++)
                                {
                                    // @M - Add data to model "DataCommisionJob"
                                    DataGadaiLunasLelang dataGadaiLunasLelang = new DataGadaiLunasLelang(
                                            ""+myList.get(i).getid(),
                                            ""+myList.get(i).getnoFaktur(),
                                            ""+myList.get(i).gettipe(),
                                            ""+myList.get(i).getmerk(),
                                            ""+myList.get(i).getjenisBarang(),
                                            ""+myList.get(i).getjenisPinjaman(),
                                            ""+myList.get(i).getstatus(),
                                            ""+myList.get(i).getnilaiPinjamanAwal(),
                                            ""+myList.get(i).getnilaiPinjamanEfektif(),
                                            ""+myList.get(i).gettanggalGadai(),
                                            ""+myList.get(i).getimeiSn(),
                                            ""+myList.get(i).gettanggalJatuhTempo(),
                                            ""+myList.get(i).getwarna(),
                                            ""+myList.get(i).gettahun()
                                    );
                                    // @M - Add to List
                                    listAllData2.add(dataGadaiLunasLelang);
                                }

                                recyclerView2.setAdapter(myAdapter2);
                                myAdapter2.notifyDataSetChanged();
                                myAdapter2.setLoaded();
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
                        message = ""+jObjError.getString("reason");
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
            public void onFailure(Call<ResponseGadai2> call, Throwable t) {
                swipeRefreshLayout2.setRefreshing(false);
                dialogWarning(getResources().getString(R.string.gagalCobaLagi));
            }
        });
    }


    private void dialogCicilan()
    {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.model_kontrak_undone);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView close = dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private void dialogLunas()
    {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.model_kontrak_done);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView close = dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
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
