package com.pusatgadaiindonesia.app.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.orhanobut.dialogplus.DialogPlus;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda.GadaiKendaraan;
import com.pusatgadaiindonesia.app.Model.Master.DataMaster;
import com.pusatgadaiindonesia.app.R;
import com.pusatgadaiindonesia.app.Services.CustomeClass.OnLoadMoreListener;

import java.util.List;

public class AdapterMasterKendaraan extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    List<DataMaster> myArray;
    RecyclerView mRecyclerView;
    private boolean isLoading;
    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;
    private OnLoadMoreListener mOnLoadMoreListener;
    Context c;
    DialogPlus dialogPlus;
    GadaiKendaraan gadaiKendaraan;
    String idSelect;
    String option;
    public AdapterMasterKendaraan(Context c, RecyclerView mRecyclerView, List<DataMaster> myArray, DialogPlus dialogPlus, GadaiKendaraan gadaiKendaraan, String idSelect, String option) {
        this.mRecyclerView = mRecyclerView;
        this.myArray = myArray;
        this.c = c;
        this.dialogPlus = dialogPlus;
        this.gadaiKendaraan = gadaiKendaraan;
        this.idSelect = idSelect;
        this.option = option;

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
            //View view = LayoutInflater.from(c).inflate(R.layout.model_list_province, parent, false);
            View view = LayoutInflater.from(c).inflate(R.layout.model_list_province, parent, false);
            return new AdapterMasterKendaraan.UserViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(c).inflate(R.layout.progressbar, parent, false);
            return new AdapterMasterKendaraan.LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AdapterMasterKendaraan.UserViewHolder) {
            final DataMaster DataMaster = myArray.get(position);
            AdapterMasterKendaraan.UserViewHolder userViewHolder = (AdapterMasterKendaraan.UserViewHolder) holder;

            userViewHolder.name.setText(""+DataMaster.getname());
            if (idSelect.equals(""+DataMaster.getid()))
            {
                userViewHolder.name.setTextColor(c.getResources().getColor(R.color.blue_selected));
            }
            else
            {
                userViewHolder.name.setTextColor(c.getResources().getColor(R.color.grey_menu_text));
            }
            userViewHolder.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogPlus.dismiss();
                    if (option.equals("2"))
                    {
                        gadaiKendaraan.setMerk(""+DataMaster.getid(),""+DataMaster.getname());
                    }
                    else
                    {
                        gadaiKendaraan.setTipe(""+DataMaster.getid(),""+DataMaster.getname(), ""+DataMaster.getMaxLoanPrice());
                    }
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

        TextView name;
        LinearLayout parent;

        public UserViewHolder(View view) {
            super(view);
            c = itemView.getContext();

            name = view.findViewById(R.id.name);
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
