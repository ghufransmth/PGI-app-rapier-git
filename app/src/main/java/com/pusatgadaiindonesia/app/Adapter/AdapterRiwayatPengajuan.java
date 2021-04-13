package com.pusatgadaiindonesia.app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pusatgadaiindonesia.app.Database.Data_Profile;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda.DetailPengajuan;
import com.pusatgadaiindonesia.app.Interface.Homescreen.DaftarGadai.ConfirmPembayaran;
import com.pusatgadaiindonesia.app.Model.Gadai.DataGadaiLunasLelang;
import com.pusatgadaiindonesia.app.Model.Gadai.DataGadaiMobile;
import com.pusatgadaiindonesia.app.R;
import com.pusatgadaiindonesia.app.Services.CustomeClass.OnLoadMoreListener;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdapterRiwayatPengajuan extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    List<DataGadaiMobile> myArray;
    RecyclerView mRecyclerView;
    private boolean isLoading;
    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;
    private OnLoadMoreListener mOnLoadMoreListener;
    Context c;
    public AdapterRiwayatPengajuan(Context c, RecyclerView mRecyclerView, List<DataGadaiMobile> myArray) {
        this.mRecyclerView = mRecyclerView;
        this.myArray = myArray;
        this.c = c;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            //View view = LayoutInflater.from(c).inflate(R.layout.model_list_notifikasi, parent, false);
            View view = LayoutInflater.from(c).inflate(R.layout.model_list_riwayat_pengajuan, parent, false);
            return new AdapterRiwayatPengajuan.UserViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(c).inflate(R.layout.progressbar, parent, false);
            return new AdapterRiwayatPengajuan.LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AdapterRiwayatPengajuan.UserViewHolder) {
            final DataGadaiMobile dataGadaiMobile = myArray.get(position);
            AdapterRiwayatPengajuan.UserViewHolder userViewHolder = (AdapterRiwayatPengajuan.UserViewHolder) holder;

            Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);

            userViewHolder.date.setText(""+changeDate(dataGadaiMobile.getcreatedAt()));
            userViewHolder.antrian.setText(""+dataGadaiMobile.getnoReferensi());
            userViewHolder.nama.setText(""+data_profile.nama);
            userViewHolder.jenis.setText(""+dataGadaiMobile.getjenisBarang());
            userViewHolder.tipe.setText(""+dataGadaiMobile.gettipe());
            userViewHolder.harga.setText("Rp. "+currencyFormat(dataGadaiMobile.getestimasiPinjaman()));
            userViewHolder.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(c, DetailPengajuan.class);
                    intent.putExtra("sendTglTransaksi",""+dataGadaiMobile.getcreatedAt());
                    intent.putExtra("sendNoAntrian",""+dataGadaiMobile.getnoReferensi());
                    intent.putExtra("sendJenis",""+dataGadaiMobile.getjenisBarang());
                    intent.putExtra("sendMerk",""+dataGadaiMobile.getmerk());
                    intent.putExtra("sendTipe",""+dataGadaiMobile.gettipe());
                    intent.putExtra("sendWarna",""+dataGadaiMobile.getwarnaBarang());
                    intent.putExtra("sendTahun",""+dataGadaiMobile.gettahun());
                    intent.putExtra("sendTotal",""+dataGadaiMobile.getestimasiPinjaman());
                    c.startActivity(intent);
                }
            });

        }
    }

    public static String currencyFormat(String amount) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0.00");
        return formatter.format(Double.parseDouble(amount));
    }

    public static String changeDate(String dates){
        Locale us = new Locale("US");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", us);
        String stringDate = "";
        try {
            Date date = format.parse(dates);
            stringDate = new SimpleDateFormat("dd-MMM-yyyy", us).format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return stringDate;
    }

    @Override
    public int getItemCount() {
        return myArray == null ? 0 : myArray.size();
    }

    @Override
    public int getItemViewType(int position) {
        return myArray.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }
    public void setLoaded() {
        isLoading = false;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        TextView date, antrian, nama, jenis, tipe, harga;
        LinearLayout parent;

        public UserViewHolder(View view) {
            super(view);
            c = itemView.getContext();

            date = view.findViewById(R.id.date);
            antrian = view.findViewById(R.id.antrian);
            nama = view.findViewById(R.id.nama);
            jenis = view.findViewById(R.id.jenis);
            tipe = view.findViewById(R.id.tipe);
            harga = view.findViewById(R.id.harga);
            parent = view.findViewById(R.id.parent);

        }
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }

}
