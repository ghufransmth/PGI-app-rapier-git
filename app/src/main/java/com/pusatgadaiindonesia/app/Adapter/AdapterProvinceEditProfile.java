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
import com.pusatgadaiindonesia.app.Interface.Homescreen.Profile.EditProfile;
import com.pusatgadaiindonesia.app.Model.Province.DataProvince;
import com.pusatgadaiindonesia.app.R;
import com.pusatgadaiindonesia.app.Services.CustomeClass.OnLoadMoreListener;

import java.util.List;

public class AdapterProvinceEditProfile extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    List<DataProvince> myArray;
    RecyclerView mRecyclerView;
    private boolean isLoading;
    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;
    private OnLoadMoreListener mOnLoadMoreListener;
    Context c;
    DialogPlus dialogPlus;
    EditProfile editProfile;
    String idSelect;
    public AdapterProvinceEditProfile(Context c, RecyclerView mRecyclerView, List<DataProvince> myArray, DialogPlus dialogPlus, EditProfile editProfile, String idSelect) {
        this.mRecyclerView = mRecyclerView;
        this.myArray = myArray;
        this.c = c;
        this.dialogPlus = dialogPlus;
        this.editProfile = editProfile;
        this.idSelect = idSelect;

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
            return new AdapterProvinceEditProfile.UserViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(c).inflate(R.layout.progressbar, parent, false);
            return new AdapterProvinceEditProfile.LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AdapterProvinceEditProfile.UserViewHolder) {
            final DataProvince DataProvince = myArray.get(position);
            AdapterProvinceEditProfile.UserViewHolder userViewHolder = (AdapterProvinceEditProfile.UserViewHolder) holder;

            userViewHolder.name.setText(""+DataProvince.getname());
            if (idSelect.equals(""+DataProvince.getid()))
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
                    editProfile.setProvince(""+DataProvince.getid(),""+DataProvince.getname());
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
