package com.pusatgadaiindonesia.app.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda.Notifikasi;
import com.pusatgadaiindonesia.app.Model.Notification.DataNotificationDetail;
import com.pusatgadaiindonesia.app.R;
import com.pusatgadaiindonesia.app.Services.CustomeClass.OnLoadMoreListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdapterNotifikasi extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    List<DataNotificationDetail> myArray;
    RecyclerView mRecyclerView;
    Notifikasi notifikasi;
    private boolean isLoading;
    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;
    private OnLoadMoreListener mOnLoadMoreListener;
    Context c;
    public AdapterNotifikasi(Context c, RecyclerView mRecyclerView, List<DataNotificationDetail> myArray, Notifikasi notifikasi) {
        this.mRecyclerView = mRecyclerView;
        this.myArray = myArray;
        this.c = c;
        this.notifikasi = notifikasi;

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
            View view = LayoutInflater.from(c).inflate(R.layout.model_list_notifikasi, parent, false);
            return new AdapterNotifikasi.UserViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(c).inflate(R.layout.progressbar, parent, false);
            return new AdapterNotifikasi.LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AdapterNotifikasi.UserViewHolder) {
            final DataNotificationDetail dataNotificationDetail = myArray.get(position);
            AdapterNotifikasi.UserViewHolder userViewHolder = (AdapterNotifikasi.UserViewHolder) holder;

            userViewHolder.title.setText(""+dataNotificationDetail.getgadaiId());
            userViewHolder.desc.setText(""+dataNotificationDetail.getmessage());
            userViewHolder.date.setText(""+changeDate(dataNotificationDetail.getcreatedAt()));

            String cek = ""+dataNotificationDetail.getread();
            if(cek.equals("false"))
            {
                userViewHolder.read.setVisibility(View.VISIBLE);
            }
            else
            {
                userViewHolder.read.setVisibility(View.GONE);
            }

            userViewHolder.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notifikasi.setHasRead(dataNotificationDetail.getid());
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

        TextView title, desc, date;
        ImageView read;
        LinearLayout parent;

        public UserViewHolder(View view) {
            super(view);
            c = itemView.getContext();

            title = view.findViewById(R.id.title);
            desc = view.findViewById(R.id.desc);
            date = view.findViewById(R.id.date);
            parent = view.findViewById(R.id.parent);
            read = view.findViewById(R.id.read);

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
