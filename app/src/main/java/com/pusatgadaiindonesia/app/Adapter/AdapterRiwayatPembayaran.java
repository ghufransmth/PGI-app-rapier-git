package com.pusatgadaiindonesia.app.Adapter;

import android.content.Context;
import android.content.Intent;
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

import com.pusatgadaiindonesia.app.Database.Data_Profile;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda.DetailPembayaran;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda.DetailPengajuan;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda.Riwayat;
import com.pusatgadaiindonesia.app.Model.Gadai.DataGadaiMobile;
import com.pusatgadaiindonesia.app.Model.Pembayaran.DataPembayaran;
import com.pusatgadaiindonesia.app.Model.Pembayaran.DataPembayaranDetail;
import com.pusatgadaiindonesia.app.Model.Pembayaran.DataPembayaranDetail2;
import com.pusatgadaiindonesia.app.R;
import com.pusatgadaiindonesia.app.Services.CustomeClass.OnLoadMoreListener;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdapterRiwayatPembayaran extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    List<DataPembayaranDetail2> myArray;
    RecyclerView mRecyclerView;
    private boolean isLoading;
    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;
    private OnLoadMoreListener mOnLoadMoreListener;
    Riwayat riwayat;
    Context c;
    public AdapterRiwayatPembayaran(Context c, RecyclerView mRecyclerView, List<DataPembayaranDetail2> myArray, Riwayat riwayat) {
        this.mRecyclerView = mRecyclerView;
        this.myArray = myArray;
        this.c = c;
        this.riwayat = riwayat;

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
            View view = LayoutInflater.from(c).inflate(R.layout.model_list_riwayat_pembayaran, parent, false);
            return new AdapterRiwayatPembayaran.UserViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(c).inflate(R.layout.progressbar, parent, false);
            return new AdapterRiwayatPembayaran.LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AdapterRiwayatPembayaran.UserViewHolder) {
            final DataPembayaranDetail2 dataPembayaran = myArray.get(position);
            AdapterRiwayatPembayaran.UserViewHolder userViewHolder = (AdapterRiwayatPembayaran.UserViewHolder) holder;

            Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);

            userViewHolder.date.setText(""+changeDate(dataPembayaran.getcreatedAt()));
            userViewHolder.no_faktur.setText(""+dataPembayaran.getno_faktur());
            userViewHolder.jenis.setText(""+dataPembayaran.getgadai());
            userViewHolder.bank.setText(""+dataPembayaran.getbank());
            userViewHolder.harga.setText("Rp. "+currencyFormat(dataPembayaran.getjumlahHarusBayar()));

            if(dataPembayaran.getstatus().equals("1"))
            {
                userViewHolder.batalkan.setVisibility(View.GONE);
                userViewHolder.pending.setVisibility(View.GONE);
                userViewHolder.berhasil.setVisibility(View.VISIBLE);
                userViewHolder.batal.setVisibility(View.GONE);
            }
            else if(dataPembayaran.getstatus().equals("2")){
                userViewHolder.batalkan.setVisibility(View.GONE);
                userViewHolder.pending.setVisibility(View.GONE);
                userViewHolder.berhasil.setVisibility(View.GONE);
                userViewHolder.batal.setVisibility(View.VISIBLE);
            }else{
                userViewHolder.batalkan.setVisibility(View.VISIBLE);
                userViewHolder.pending.setVisibility(View.VISIBLE);
                userViewHolder.berhasil.setVisibility(View.GONE);
                userViewHolder.batal.setVisibility(View.GONE);
            }

            if(dataPembayaran.getva_number().equals("") || dataPembayaran.getva_number().equals("null")){
                userViewHolder.txt_va.setVisibility(View.GONE);
                userViewHolder.va.setVisibility(View.GONE);
                userViewHolder.txt_bill_key.setVisibility(View.VISIBLE);
                userViewHolder.bill_key.setVisibility(View.VISIBLE);
                userViewHolder.txt_bill_code.setVisibility(View.VISIBLE);
                userViewHolder.bill_code.setVisibility(View.VISIBLE);
                userViewHolder.bill_key.setText(""+dataPembayaran.getbill_key());
                userViewHolder.bill_code.setText(""+dataPembayaran.getbiller_code());
            }else{
                userViewHolder.txt_va.setVisibility(View.VISIBLE);
                userViewHolder.va.setVisibility(View.VISIBLE);
                userViewHolder.txt_bill_key.setVisibility(View.GONE);
                userViewHolder.bill_key.setVisibility(View.GONE);
                userViewHolder.txt_bill_code.setVisibility(View.GONE);
                userViewHolder.bill_code.setVisibility(View.GONE);
                userViewHolder.va.setText(""+dataPembayaran.getva_number());
            }

            userViewHolder.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(c, DetailPembayaran.class);
                    intent.putExtra("sendPembayaranId",""+dataPembayaran.getpembayaranId());
                    intent.putExtra("sendStatus",""+dataPembayaran.getstatus());
                    intent.putExtra("sendTglTransaksi",""+dataPembayaran.getcreatedAt());
                    intent.putExtra("sendNoFaktur",""+dataPembayaran.getno_faktur());
                    intent.putExtra("sendNamaBarang",""+dataPembayaran.getgadai());
                    intent.putExtra("sendVa",""+dataPembayaran.getva_number());
                    intent.putExtra("sendMetodeBayar",""+dataPembayaran.getmetode());
                    intent.putExtra("sendTotal",""+dataPembayaran.getjumlahHarusBayar());
                    c.startActivity(intent);
                }
            });

            userViewHolder.batalkan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    riwayat.setData(dataPembayaran.getpembayaranId());
                }
            });

        }
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

    public static String currencyFormat(String amount) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0.00");
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

        TextView date, no_faktur, jenis, va, payment_code, bill_key, bill_code, bank, harga;
        TextView txt_va, txt_bill_key, txt_bill_code, txt_payment_code;
        CardView berhasil, pending, batal;
        ImageView batalkan;
        LinearLayout parent;

        public UserViewHolder(View view) {
            super(view);
            c = itemView.getContext();

            date = view.findViewById(R.id.date);
            no_faktur = view.findViewById(R.id.no_faktur);
            jenis = view.findViewById(R.id.jenis);
            txt_va = view.findViewById(R.id.txt_va);
            va = view.findViewById(R.id.va);
            txt_bill_key = view.findViewById(R.id.txt_bill_key);
            bill_key = view.findViewById(R.id.bill_key);
            txt_bill_code = view.findViewById(R.id.txt_bill_code);
            bill_code = view.findViewById(R.id.bill_code);
            txt_payment_code = view.findViewById(R.id.txt_payment_code);
            payment_code = view.findViewById(R.id.payment_code);
            bank = view.findViewById(R.id.bank);
            harga = view.findViewById(R.id.harga);
            batalkan = view.findViewById(R.id.batalkan);
            berhasil = view.findViewById(R.id.berhasil);
            pending = view.findViewById(R.id.pending);
            batal = view.findViewById(R.id.batal);
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
