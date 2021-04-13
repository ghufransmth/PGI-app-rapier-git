package com.pusatgadaiindonesia.app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.util.Strings;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda.GadaiElektronik;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda.GadaiElektronik2;
import com.pusatgadaiindonesia.app.Interface.Homescreen.DaftarGadai.InquiryPembayaran;
import com.pusatgadaiindonesia.app.Model.Gadai.DataGadaiBerjalan;
import com.pusatgadaiindonesia.app.Model.Notification.DataNotificationDetail;
import com.pusatgadaiindonesia.app.R;
import com.pusatgadaiindonesia.app.Services.CustomeClass.OnLoadMoreListener;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdapterDataAktif extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    List<DataGadaiBerjalan> myArray;
    RecyclerView mRecyclerView;
    private boolean isLoading;
    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;
    private OnLoadMoreListener mOnLoadMoreListener;
    Context c;
    public AdapterDataAktif(Context c, RecyclerView mRecyclerView, List<DataGadaiBerjalan> myArray) {
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
            View view = LayoutInflater.from(c).inflate(R.layout.model_list_data_aktif, parent, false);
            return new AdapterDataAktif.UserViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(c).inflate(R.layout.progressbar, parent, false);
            return new AdapterDataAktif.LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AdapterDataAktif.UserViewHolder) {
            final DataGadaiBerjalan dataGadaiBerjalan = myArray.get(position);
            AdapterDataAktif.UserViewHolder userViewHolder = (AdapterDataAktif.UserViewHolder) holder;

            userViewHolder.no_faktur.setText(""+dataGadaiBerjalan.getnoFaktur());
            userViewHolder.jenis.setText(""+dataGadaiBerjalan.getjenisBarang());
            userViewHolder.tipe.setText(""+dataGadaiBerjalan.gettipe());
            userViewHolder.harga.setText("Rp. "+currencyFormat(dataGadaiBerjalan.getnilaiPinjamanEfektif()));

            if (dataGadaiBerjalan.gettanggalGadai().equals("")){

            }else{
                Locale us = new Locale("US");
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", us);
                try {
                    Date date = format.parse(dataGadaiBerjalan.gettanggalGadai());
                    String stringDate = new SimpleDateFormat("dd", us).format(date);
                    String stringMonth = new SimpleDateFormat("MMM", us).format(date);
                    String stringYear = new SimpleDateFormat("yyyy", us).format(date);

                    userViewHolder.tahun.setText(""+stringYear);
                    userViewHolder.tgl.setText(""+stringDate);
                    userViewHolder.bulan.setText(""+stringMonth);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }



            userViewHolder.lanjut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(c, InquiryPembayaran.class);
                    intent.putExtra("sendId",""+dataGadaiBerjalan.getid());
                    intent.putExtra("sendNoFaktur",""+dataGadaiBerjalan.getnoFaktur());
                    intent.putExtra("sendJenis",""+dataGadaiBerjalan.getjenisBarang());
                    intent.putExtra("sendNominal",""+dataGadaiBerjalan.getnilaiPinjamanEfektif());
                    intent.putExtra("sendJatuhTempo",""+dataGadaiBerjalan.gettanggalJatuhTempo());
                    c.startActivity(intent);
                }
            });

        }
    }

    public static String currencyFormat(String amount) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0");
        return formatter.format(Double.parseDouble(amount));
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

        TextView tahun, tgl, bulan, no_faktur, jenis, tipe, harga;
        LinearLayout parent;
        CardView lanjut;


        public UserViewHolder(View view) {
            super(view);
            c = itemView.getContext();

            tahun = view.findViewById(R.id.tahun);
            tgl = view.findViewById(R.id.tgl);
            bulan = view.findViewById(R.id.bulan);
            no_faktur = view.findViewById(R.id.no_faktur);
            jenis = view.findViewById(R.id.jenis);
            tipe = view.findViewById(R.id.tipe);
            harga = view.findViewById(R.id.harga);
            lanjut = view.findViewById(R.id.lanjut);
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
