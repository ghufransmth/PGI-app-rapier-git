package com.pusatgadaiindonesia.app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orhanobut.dialogplus.DialogPlus;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda.GadaiDetail;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda.GadaiElektronik;
import com.pusatgadaiindonesia.app.Model.Barang.DataBarang;
import com.pusatgadaiindonesia.app.Model.Master.DataMaster;
import com.pusatgadaiindonesia.app.R;
import com.pusatgadaiindonesia.app.Services.CustomeClass.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdapterMasterElektronik2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    public List<DataBarang> myArrays;
    ArrayList<DataBarang> arraylist;
    RecyclerView mRecyclerView;
    private boolean isLoading;
    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;
    private OnLoadMoreListener mOnLoadMoreListener;
    Context c;
    DialogPlus dialogPlus;
    GadaiDetail gadaiDetail;
    String idSelect;
    String option;
    public AdapterMasterElektronik2(Context c, RecyclerView mRecyclerView, List<DataBarang> myArray,  GadaiDetail gadaiDetail, String idSelect, String option) {
        this.mRecyclerView = mRecyclerView;
        this.myArrays = myArray;
        this.c = c;
        this.gadaiDetail = gadaiDetail;
        this.idSelect = idSelect;
        this.option = option;
        myArrays = myArray;
        this.arraylist = new ArrayList<DataBarang>();
        this.arraylist.addAll(myArray);

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
            return new AdapterMasterElektronik2.UserViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(c).inflate(R.layout.progressbar, parent, false);
            return new AdapterMasterElektronik2.LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AdapterMasterElektronik2.UserViewHolder) {
            final DataBarang dataBarang = myArrays.get(position);
            AdapterMasterElektronik2.UserViewHolder userViewHolder = (AdapterMasterElektronik2.UserViewHolder) holder;

            userViewHolder.name.setText(""+dataBarang.getname());
            userViewHolder.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gadaiDetail.setData(""+dataBarang.getid(),""+dataBarang.getname());
//                    if(option.equals("1"))
//                    {
//                        gadaiDetail.setJenis(""+DataMaster.getid(),""+DataMaster.getname());
//                    }
//                    else if (option.equals("2"))
//                    {
//                        gadaiElektronik.setMerk(""+DataMaster.getid(),""+DataMaster.getname());
//                    }
//                    else
//                    {
//                        gadaiElektronik.setTipe(""+DataMaster.getid(),""+DataMaster.getname(), ""+DataMaster.getMaxLoanPrice());
//                    }
                }
            });

        }
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        myArrays.clear();
        if (charText.length() == 0) {
            myArrays.addAll(arraylist);
        } else {
            for (DataBarang wp : arraylist) {
                if (wp.getname().toLowerCase(Locale.getDefault()).contains(charText)) {
                    myArrays.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return myArrays == null ? 0 : myArrays.size();
    }

    @Override
    public int getItemViewType(int position) {
        return myArrays.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
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
