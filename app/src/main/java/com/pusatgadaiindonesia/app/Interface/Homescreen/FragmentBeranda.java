package com.pusatgadaiindonesia.app.Interface.Homescreen;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.pusatgadaiindonesia.app.Adapter.MainSliderAdapter;
import com.pusatgadaiindonesia.app.Database.Data_Profile;
import com.pusatgadaiindonesia.app.Interface.Auth.Login;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda.BeliPulsaListrik;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda.CabangTerdekat;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda.GadaiBPKB;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda.GadaiElektronik;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda.GadaiKendaraan;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda.NewsFeed;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda.Notifikasi;
import com.pusatgadaiindonesia.app.Model.Banner.DataBannerDetail;
import com.pusatgadaiindonesia.app.Model.Banner.DataBannerDetail2;
import com.pusatgadaiindonesia.app.Model.Banner.ResponseBanner;
import com.pusatgadaiindonesia.app.Model.Profile.ResponseProfile;
import com.pusatgadaiindonesia.app.R;
import com.pusatgadaiindonesia.app.Services.ImageHandling.PicassoImageLoadingService;
import com.pusatgadaiindonesia.app.Services.Retrofit.APIService;
import com.pusatgadaiindonesia.app.Services.Retrofit.ApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import ss.com.bannerslider.Slider;
/*import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;*/

import static android.content.Context.WINDOW_SERVICE;

public class FragmentBeranda extends Fragment implements View.OnClickListener {

    View rootview;

    LinearLayout headLayout, showBalance;
    CardView cardKontrak;
    RelativeLayout notifikasi;

    /*BannerSlider bannerSlider;
    List<Banner> banners = new ArrayList<>();*/
    String[] myUrl;
    Slider slider;


    @BindView(R.id.nama)
    TextView nama;

    // @M  - Menu
    CardView elektronik, kendaraan, bpkb, listrik, cabang, news;
    LinearLayout headLayoutX;

    public FragmentBeranda() {
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

            rootview = inflater.inflate(R.layout.activity_fragment_beranda, container, false);

            ButterKnife.bind(this, rootview);

            Slider.init(new PicassoImageLoadingService(getActivity()));

            headLayout = rootview.findViewById(R.id.headLayout);
            cardKontrak = rootview.findViewById(R.id.cardKontrak);

            elektronik = rootview.findViewById(R.id.elektronik);
            kendaraan = rootview.findViewById(R.id.kendaraan);
            bpkb = rootview.findViewById(R.id.bpkb);
            listrik = rootview.findViewById(R.id.listrik);
            cabang = rootview.findViewById(R.id.cabang);
            news = rootview.findViewById(R.id.news);
            headLayoutX = rootview.findViewById(R.id.headLayoutX);

            ViewTreeObserver vto = headLayout.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        headLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        headLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                    int width  = headLayout.getMeasuredWidth();
                    int height = headLayout.getMeasuredHeight();

                    //Toast.makeText(getActivity(),""+height,Toast.LENGTH_SHORT).show();

                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT,
                            dpToPx(65)
                    );
                    int mar = height - dpToPx(107);
                    params.setMargins(dpToPx(30), mar, dpToPx(60), dpToPx(5));
                    cardKontrak.setLayoutParams(params);
                }
            });


            slider = rootview.findViewById(R.id.banner_slider2);
            getBanner();

            DisplayMetrics dm = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(dm);

            int width  = dm.widthPixels;
            int height = width / 16 * 7;

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
            layoutParams.setMargins(0,0,0, dpToPx(25));
            slider.setLayoutParams(layoutParams);

            int x = dpToPx(84);
            int y = dm.widthPixels - x;
            int cardWidth = y / 2;
            //int cardWidth = (width - dpToPx(60)) / 2;
            int kendHeight =  cardWidth / 2;
            int elekHeight =  cardWidth + dpToPx(30);

            LinearLayout.LayoutParams layoutParamsElek = new LinearLayout.LayoutParams(cardWidth, elekHeight);
            layoutParamsElek.setMargins(dpToPx(30), dpToPx(30), dpToPx(12), dpToPx(12));
            elektronik.setLayoutParams(layoutParamsElek);

            LinearLayout.LayoutParams layoutParamsKend = new LinearLayout.LayoutParams(cardWidth, kendHeight);
            layoutParamsKend.setMargins(dpToPx(12), dpToPx(30), dpToPx(30), dpToPx(12));
            kendaraan.setLayoutParams(layoutParamsKend);

            LinearLayout.LayoutParams layoutParamsBp = new LinearLayout.LayoutParams(cardWidth, kendHeight);
            layoutParamsBp.setMargins(dpToPx(12), dpToPx(12), dpToPx(30), dpToPx(12));
            bpkb.setLayoutParams(layoutParamsBp);

            int cardWidthX = (dm.widthPixels - dpToPx(108)) / 3;
            int cardHeightX = cardWidthX / 3 * 23 / 10;

            LinearLayout.LayoutParams layoutParamsLis = new LinearLayout.LayoutParams(cardWidthX, cardHeightX);
            layoutParamsLis.setMargins(dpToPx(30), dpToPx(12), dpToPx(12), dpToPx(30));
            listrik.setLayoutParams(layoutParamsLis);

            LinearLayout.LayoutParams layoutParamsCab = new LinearLayout.LayoutParams(cardWidthX, cardHeightX);
            layoutParamsCab.setMargins(dpToPx(12), dpToPx(12), dpToPx(12), dpToPx(12));
            cabang.setLayoutParams(layoutParamsCab);

            LinearLayout.LayoutParams layoutParamsNews = new LinearLayout.LayoutParams(cardWidthX, cardHeightX);
            layoutParamsNews.setMargins(dpToPx(12), dpToPx(12), dpToPx(12), dpToPx(12));
            news.setLayoutParams(layoutParamsNews);

            int xcv =  dm.widthPixels - dpToPx(60);
            LinearLayout.LayoutParams layoutParamsX = new LinearLayout.LayoutParams(xcv, LinearLayout.LayoutParams.WRAP_CONTENT);
            headLayoutX.setLayoutParams(layoutParamsX);


            showBalance = rootview.findViewById(R.id.showBalance);
            notifikasi = rootview.findViewById(R.id.notifikasi);

            showBalance.setOnClickListener(this);
            notifikasi.setOnClickListener(this);
            elektronik.setOnClickListener(this);
            kendaraan.setOnClickListener(this);
            bpkb.setOnClickListener(this);
            listrik.setOnClickListener(this);
            cabang.setOnClickListener(this);
            news.setOnClickListener(this);

            getDataProfile();
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


    private void dialogBalance()
    {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.model_dialog_balance);
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

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.showBalance:
                dialogBalance();
                break;

            case R.id.notifikasi:
                Intent intent = new Intent(getActivity(), Notifikasi.class);
                startActivity(intent);
                break;

            case R.id.elektronik:
                Intent intentEl = new Intent(getActivity(), GadaiElektronik.class);
                startActivity(intentEl);
                break;

            case R.id.kendaraan:
                Intent intentKen = new Intent(getActivity(), GadaiKendaraan.class);
                startActivity(intentKen);
                break;

            case R.id.bpkb:
                Intent intentBp = new Intent(getActivity(), GadaiBPKB.class);
                startActivity(intentBp);
                break;

            case R.id.listrik:
                Intent intentLis = new Intent(getActivity(), BeliPulsaListrik.class);
                startActivity(intentLis);
                break;

            case R.id.cabang:
                Intent intentCab = new Intent(getActivity(), CabangTerdekat.class);
                startActivity(intentCab);
                break;

            case R.id.news:
                Intent intentNe = new Intent(getActivity(), NewsFeed.class);
                startActivity(intentNe);
                break;
        }
    }


    public void getBanner()
    {
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseBanner> userCall = service.getBanner();
        userCall.enqueue(new Callback<ResponseBanner>() {
            @Override
            public void onResponse(Call<ResponseBanner> call, retrofit2.Response<ResponseBanner> response) {
                //Log.w("", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful())
                {
                    String myMessage = ""+response.body().getreason();
                    String status = ""+response.body().getstatus();
                    if(status.equals("success"))
                    {
                        try {
                            List<DataBannerDetail2> myList = null;
                            myList = (List<DataBannerDetail2>) response.body().getdata();

                            if(myList.size()==0)
                            {

                            }
                            else
                            {
                                myUrl = new String[myList.size()];

                                for (int i = 0; i < myList.size(); i++)
                                {
                                    myUrl[i] = "https://assets.materialup.com/uploads/20ded50d-cc85-4e72-9ce3-452671cf7a6d/preview.jpg";
                                    //myUrl[i] = ""+myList.get(i).getimgUrl();
                                    //banners.add(new RemoteBanner(""+myList.get(i).getimgUrl()));
                                    Log.v("banner", myList.get(i).getfilePath());
                                }
                                slider.setAdapter(new MainSliderAdapter(myUrl));
                                slider.setSelectedSlide(0);
                                //bannerSlider.setBanners(banners);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            //Toast.makeText(getApplicationContext(),"catch",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getActivity(),""+myMessage,Toast.LENGTH_SHORT).show();
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
                            //Toast.makeText(getActivity(),""+myMessage,Toast.LENGTH_SHORT).show();
                        }
                    };
                    handler.postDelayed(myRunnable,500);
                }
            }

            @Override
            public void onFailure(Call<ResponseBanner> call, Throwable t) {
                //dialogWarning(getResources().getString(R.string.gagalCobaLagi));
            }
        });


    }


    public void getDataProfile()
    {
        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        nama.setText(""+data_profile.userName);

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseProfile> userCall = service.getProfile(tokenBearer);
        userCall.enqueue(new Callback<ResponseProfile>() {
            @Override
            public void onResponse(Call<ResponseProfile> call, retrofit2.Response<ResponseProfile> response) {
                Log.w("LOGIN", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful()){

                    String myMessage = ""+response.body().getmessage();
                    String status = ""+response.body().getstatus();
                    if(status.equals("success"))
                    {
                        /*Picasso.with(getActivity())
                                .load("" + response.body().getdata().getPicture())
                                .noPlaceholder()
                                .resize(300, 300)
                                .centerCrop()
                                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                                .into(image);*/

                        nama.setText(""+response.body().getdata().getname());
                    }
                    else
                    {
                        Toast.makeText(getActivity(),""+response.body().getmessage(),Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    // @M - If response from server is not success, get the message and show it in dialog
                    JSONObject jObjError = null;
                    String message = ""+getResources().getString(R.string.gagalCobaLagi);
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                        message = ""+jObjError.getString("message");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //Toast.makeText(getActivity(),""+message,Toast.LENGTH_SHORT).show();
                    //dialogWarningLogout(""+message);
                }
            }
            @Override
            public void onFailure(Call<ResponseProfile> call, Throwable t) {
            }
        });
    }

    public void dialogWarningLogout(String message)
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
                Data_Profile data_profileX = Data_Profile.findById(Data_Profile.class,1L);
                data_profileX.accessToken = "";
                data_profileX.login = false;
                data_profileX.save();
                getActivity().finish();
                Intent intent2 = new Intent(getActivity(), Login.class);
                startActivity(intent2);
            }
        });
        dialog.show();
    }
}