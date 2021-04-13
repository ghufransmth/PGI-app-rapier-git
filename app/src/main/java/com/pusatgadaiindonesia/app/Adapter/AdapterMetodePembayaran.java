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

import com.bumptech.glide.Glide;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda.GadaiElektronik;
import com.pusatgadaiindonesia.app.Interface.Homescreen.DaftarGadai.ConfirmPembayaran;
import com.pusatgadaiindonesia.app.Interface.Homescreen.DaftarGadai.InquiryPembayaran;
import com.pusatgadaiindonesia.app.Interface.Homescreen.DaftarGadai.MetodePembayaran;
import com.pusatgadaiindonesia.app.Model.Gadai.DataGadaiBerjalan;
import com.pusatgadaiindonesia.app.Model.MetodeBayar.DataMetodeBayar;
import com.pusatgadaiindonesia.app.R;
import com.pusatgadaiindonesia.app.Services.CustomeClass.OnLoadMoreListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdapterMetodePembayaran extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    List<DataMetodeBayar> myArray;
    RecyclerView mRecyclerView;
    private boolean isLoading;
    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;
    private OnLoadMoreListener mOnLoadMoreListener;
    MetodePembayaran metodePembayaran;
    Context c;
    public AdapterMetodePembayaran(Context c, RecyclerView mRecyclerView, List<DataMetodeBayar> myArray, MetodePembayaran metodePembayaran) {
        this.mRecyclerView = mRecyclerView;
        this.myArray = myArray;
        this.c = c;
        this.metodePembayaran = metodePembayaran;

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
            View view = LayoutInflater.from(c).inflate(R.layout.model_list_metode_pembayaran, parent, false);
            return new AdapterMetodePembayaran.UserViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(c).inflate(R.layout.progressbar, parent, false);
            return new AdapterMetodePembayaran.LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AdapterMetodePembayaran.UserViewHolder) {
            final DataMetodeBayar dataMetodeBayar = myArray.get(position);
            AdapterMetodePembayaran.UserViewHolder userViewHolder = (AdapterMetodePembayaran.UserViewHolder) holder;

            userViewHolder.title.setText(""+dataMetodeBayar.getname());
            Glide
            .with(c)
            .load("https://dev.earth.pgindonesia.com/"+dataMetodeBayar.getlogo())
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(userViewHolder.image);

            userViewHolder.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    metodePembayaran.setConfirmBayar(""+dataMetodeBayar.getid());
                }
            });

        }
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

        TextView title;
        ImageView image;
        LinearLayout parent;
        CardView lanjut;

        public UserViewHolder(View view) {
            super(view);
            c = itemView.getContext();

            title = view.findViewById(R.id.title);
            image = view.findViewById(R.id.image);
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
