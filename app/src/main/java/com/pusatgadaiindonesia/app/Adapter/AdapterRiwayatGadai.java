package com.pusatgadaiindonesia.app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pusatgadaiindonesia.app.Model.Gadai.DataGadaiLunasLelang;
import com.pusatgadaiindonesia.app.Model.Notification.DataNotificationDetail;
import com.pusatgadaiindonesia.app.R;
import com.pusatgadaiindonesia.app.Services.CustomeClass.OnLoadMoreListener;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdapterRiwayatGadai extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    List<DataGadaiLunasLelang> myArray;
    RecyclerView mRecyclerView;
    private boolean isLoading;
    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;
    private OnLoadMoreListener mOnLoadMoreListener;
    Context c;
    public AdapterRiwayatGadai(Context c, RecyclerView mRecyclerView, List<DataGadaiLunasLelang> myArray) {
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
            View view = LayoutInflater.from(c).inflate(R.layout.model_list_riwayat_gadai, parent, false);
            return new AdapterRiwayatGadai.UserViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(c).inflate(R.layout.progressbar, parent, false);
            return new AdapterRiwayatGadai.LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AdapterRiwayatGadai.UserViewHolder) {
            final DataGadaiLunasLelang dataGadaiLunasLelang = myArray.get(position);
            AdapterRiwayatGadai.UserViewHolder userViewHolder = (AdapterRiwayatGadai.UserViewHolder) holder;

            userViewHolder.no_faktur.setText(""+dataGadaiLunasLelang.getnoFaktur());
            userViewHolder.jenis.setText(""+dataGadaiLunasLelang.getjenisBarang());
            userViewHolder.tipe.setText(""+dataGadaiLunasLelang.gettipe());
            userViewHolder.harga.setText("Rp. "+currencyFormat(dataGadaiLunasLelang.getnilaiPinjamanAwal()));

            userViewHolder.tahun.setText(""+changeDateFormat("yyyy-MMM-dd","yyyy",""+dataGadaiLunasLelang.gettanggalJatuhTempo()));
            userViewHolder.tgl.setText(""+changeDateFormat("yyyy-MMM-dd","dd",""+dataGadaiLunasLelang.gettanggalJatuhTempo()));
            userViewHolder.bulan.setText(""+changeDateFormat("yyyy-MMM-dd","MMM",""+dataGadaiLunasLelang.gettanggalJatuhTempo()));
            userViewHolder.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });

        }
    }

    public static String currencyFormat(String amount) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0");
        return formatter.format(Double.parseDouble(amount));
    }

    private String changeDateFormat(String currentFormat,String requiredFormat,String dateString){
        String result="";
        if (dateString.isEmpty()){
            return result;
        }
        SimpleDateFormat formatterOld = new SimpleDateFormat(currentFormat, Locale.getDefault());
        SimpleDateFormat formatterNew = new SimpleDateFormat(requiredFormat, Locale.getDefault());
        Date date=null;
        try {
            date = formatterOld.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null) {
            result = formatterNew.format(date);
        }
        return result;
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
