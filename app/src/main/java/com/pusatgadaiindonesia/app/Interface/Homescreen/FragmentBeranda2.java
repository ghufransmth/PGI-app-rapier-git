package com.pusatgadaiindonesia.app.Interface.Homescreen;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.GsonBuilder;
import com.pusatgadaiindonesia.app.Adapter.MainSliderAdapter;
import com.pusatgadaiindonesia.app.Database.Data_Profile;
import com.pusatgadaiindonesia.app.Interface.Auth.Login;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda.BeliPulsaListrik;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda.CabangTerdekat;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda.GadaiBPKB;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda.GadaiElektronik;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda.GadaiKategori;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda.GadaiKendaraan;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda.GadaiPembayaran;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda.NewsFeed;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda.Notifikasi;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda.Riwayat;
import com.pusatgadaiindonesia.app.MainActivity;
import com.pusatgadaiindonesia.app.Model.Banner.DataBannerDetail;
import com.pusatgadaiindonesia.app.Model.Banner.DataBannerDetail2;
import com.pusatgadaiindonesia.app.Model.Banner.ResponseBanner;
import com.pusatgadaiindonesia.app.Model.Profile.ResponseProfile;
import com.pusatgadaiindonesia.app.Model.Profile.ResponseProfile2;
import com.pusatgadaiindonesia.app.Model.ResponseNormal2;
import com.pusatgadaiindonesia.app.R;
import com.pusatgadaiindonesia.app.Services.ImageHandling.PicassoImageLoadingService;
import com.pusatgadaiindonesia.app.Services.Retrofit.APIService;
import com.pusatgadaiindonesia.app.Services.Retrofit.ApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import ss.com.bannerslider.Slider;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static android.content.Context.WINDOW_SERVICE;

public class FragmentBeranda2 extends Fragment implements View.OnClickListener{

    View rootview;

    LinearLayout headLayout, showBalance;
    CardView cardKontrak;
    RelativeLayout notifikasi, riwayat;
    SwipeRefreshLayout swipeRefreshLayout;

    /*BannerSlider bannerSlider;
    List<Banner> banners = new ArrayList<>();*/
    String[] myUrl;
    Slider slider;


    @BindView(R.id.nama)
    TextView nama;

    // @M  - Menu
    CardView gadai, pembayaran, membership, simulasi, cabang, promo;
    LinearLayout headLayoutX;
    TextView textRiwayatLive, textNotifLive;

    public FragmentBeranda2() {
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

            rootview = inflater.inflate(R.layout.activity_fragment_beranda2, container, false);

            ButterKnife.bind(this, rootview);

            Slider.init(new PicassoImageLoadingService(getActivity()));

            headLayout = rootview.findViewById(R.id.headLayout);
            gadai = rootview.findViewById(R.id.gadai);
            pembayaran = rootview.findViewById(R.id.pembayaran);
            membership = rootview.findViewById(R.id.membership);
            simulasi = rootview.findViewById(R.id.simulasi);
            cabang = rootview.findViewById(R.id.cabang);
            promo = rootview.findViewById(R.id.promo);
            swipeRefreshLayout = rootview.findViewById(R.id.refresh);
            textRiwayatLive = rootview.findViewById(R.id.textRiwayatLive);
            textNotifLive = rootview.findViewById(R.id.textNotifLive);
            headLayoutX = rootview.findViewById(R.id.headLayoutX);
            notifikasi = rootview.findViewById(R.id.notifikasi);
            riwayat = rootview.findViewById(R.id.riwayat);

            Data_Profile data = Data_Profile.findById(Data_Profile.class,1L);
            if((int)data.count(Data_Profile.class, "", null) == 0) {
                Data_Profile datax = new Data_Profile("", "", "", "", "","","","","","");
                datax.save();
                Intent intent = new Intent(getActivity(), Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }else{
                long accessTokenExpAt = Long.valueOf(data.accessTokenExpAt);
                long refreshTokenExpAt = Long.valueOf(data.refreshTokenExpAt);
                //creating Calendar instance
                Calendar calendar = Calendar.getInstance();
                //Returns current time in millis
                long millis = calendar.getTimeInMillis();
                //Returns current time + one month in millis
                Calendar calendar2 = Calendar.getInstance();
                calendar2.add(Calendar.MONTH, 1);
                long millisOneMonth = calendar2.getTimeInMillis();
                long difference = millis - accessTokenExpAt;
                // @M - Check expired token
                if(millis > accessTokenExpAt){

                }else{
                    swipeRefreshLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                swipeRefreshLayout.setRefreshing(true);
                                getBanner();
                                getCountNotif();
                                getCountRiwayat();
                                getDataProfile();
                            }
                        }
                    );
                }
            }

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
//                    cardKontrak.setLayoutParams(params);
                }
            });


            slider = rootview.findViewById(R.id.banner_slider2);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeRefreshLayout.setRefreshing(true);
                    getBanner();
                    getCountNotif();
                    getCountRiwayat();
                }
            });


            DisplayMetrics dm = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(dm);

            int width  = dm.widthPixels;
            int height = width / 16 * 7;

//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
//            layoutParams.setMargins(0,0,0, dpToPx(25));
//            slider.setLayoutParams(layoutParams);

            int x = dpToPx(84);
            int y = dm.widthPixels - x;
            int cardWidth = y / 2;
            //int cardWidth = (width - dpToPx(60)) / 2;
            int kendHeight =  cardWidth / 2;
            int elekHeight =  cardWidth + dpToPx(30);

            int cardWidthX = (dm.widthPixels - dpToPx(108)) / 3;
            int cardHeightX = cardWidthX / 3 * 23 / 10;

            int xcv =  dm.widthPixels - dpToPx(60);
            LinearLayout.LayoutParams layoutParamsX = new LinearLayout.LayoutParams(xcv, LinearLayout.LayoutParams.WRAP_CONTENT);
            headLayoutX.setLayoutParams(layoutParamsX);

            notifikasi.setOnClickListener(this);
            gadai.setOnClickListener(this);
            pembayaran.setOnClickListener(this);
            membership.setOnClickListener(this);
            simulasi.setOnClickListener(this);
            cabang.setOnClickListener(this);
            promo.setOnClickListener(this);
            riwayat.setOnClickListener(this);


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
//            case R.id.showBalance:
//                dialogBalance();
//                break;

            case R.id.notifikasi:
                Intent intent = new Intent(getActivity(), Notifikasi.class);
                startActivityForResult(intent, 111);
                break;

            case R.id.riwayat:
                Intent intentRw = new Intent(getActivity(), Riwayat.class);
                startActivityForResult(intentRw, 111);
                break;

            case R.id.gadai:
                Intent intentEl = new Intent(getActivity(), GadaiKategori.class);
                startActivity(intentEl);
                break;

            case R.id.pembayaran:
                Intent intentKen = new Intent(getActivity(), GadaiPembayaran.class);
                startActivity(intentKen);
                break;

            case R.id.membership:
                Toast.makeText(getActivity(),"Fitur masih dalam pengembangan",Toast.LENGTH_SHORT).show();
//                Intent intentBp = new Intent(getActivity(), GadaiBPKB.class);
//                startActivity(intentBp);
                break;

            case R.id.simulasi:
                Toast.makeText(getActivity(),"Fitur masih dalam pengembangan",Toast.LENGTH_SHORT).show();
//                Intent intentLis = new Intent(getActivity(), BeliPulsaListrik.class);
//                startActivity(intentLis);
                break;

            case R.id.cabang:
                Intent intentCab = new Intent(getActivity(), CabangTerdekat.class);
                startActivity(intentCab);
                break;

            case R.id.promo:
                Toast.makeText(getActivity(),"Fitur masih dalam pengembangan",Toast.LENGTH_SHORT).show();
//                Intent intentNe = new Intent(getActivity(), NewsFeed.class);
//                startActivity(intentNe);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 111) {
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeRefreshLayout.setRefreshing(true);
                    getCountNotif();
                    getCountRiwayat();
                }
            });
        }else{
            Log.d(TAG, "onActivityResult: fail");
        }
    }


    public void getBanner()
    {
        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String accessToken = "Bearer " +data_profile.accessToken;

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseBanner> userCall = service.getBanner2(accessToken,"/banner/");
        userCall.enqueue(new Callback<ResponseBanner>() {
            @Override
            public void onResponse(Call<ResponseBanner> call, retrofit2.Response<ResponseBanner> response) {
                swipeRefreshLayout.setRefreshing(false);
                //Log.w("", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful())
                {
                    String myMessage = ""+response.body().getreason();
                    String status = ""+response.body().getstatus();
                    if(status.equals("true"))
                    {
                        try {
                            List<DataBannerDetail2> myList = null;
                            myList = (List<DataBannerDetail2>) response.body().getdata();

                            if(myList.size()==0)
                            {
                                myUrl = new String[myList.size()];
                                myUrl[0] = "https://dev.earth.pgindonesia.com/BANNER/promo-4-1614338577707.jpg";
                                slider.setAdapter(new MainSliderAdapter(myUrl));
                                slider.setSelectedSlide(0);
                            }
                            else
                            {
                                myUrl = new String[myList.size()];

                                for (int i = 0; i < myList.size(); i++)
                                {
                                    //myUrl[i] = "https://assets.materialup.com/uploads/20ded50d-cc85-4e72-9ce3-452671cf7a6d/preview.jpg";
                                    myUrl[i] = "https://dev.earth.pgindonesia.com"+myList.get(i).getfilePath();
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
                        myUrl = new String[1];
                        myUrl[0] = "https://dev.earth.pgindonesia.com/BANNER/promo-4-1614338577707.jpg";
                        slider.setAdapter(new MainSliderAdapter(myUrl));
                        slider.setSelectedSlide(0);
                        //Toast.makeText(getActivity(),""+myMessage,Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    myUrl = new String[1];
                    // @M - If response from server is not success, get the message and show it in dialog
                    myUrl[0] = "https://dev.earth.pgindonesia.com/BANNER/promo-4-1614338577707.jpg";
                    slider.setAdapter(new MainSliderAdapter(myUrl));
                    slider.setSelectedSlide(0);
                }
            }

            @Override
            public void onFailure(Call<ResponseBanner> call, Throwable t) {
                //dialogWarning(getResources().getString(R.string.gagalCobaLagi));
            }
        });


    }

    public void hitPost(){
        swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                    getBanner();
                    getCountNotif();
                    getCountRiwayat();
                    getDataProfile();
                }
            }
        );
    }

    public void checkToken(){
        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String accessToken = "Bearer " +data_profile.accessToken;

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseNormal2> userCall = service.getCountNotif(accessToken,"/user/notifikasi/count");
        userCall.enqueue(new Callback<ResponseNormal2>() {
            @Override
            public void onResponse(Call<ResponseNormal2> call, retrofit2.Response<ResponseNormal2> response) {
                //swipeRefreshLayout.setRefreshing(false);
                //Log.w("", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful())
                {
                    String myMessage = ""+response.body().getreason();
                    String status = ""+response.body().getstatus();
                    if(status.equals("true"))
                    {
                        hitPost();
                    }
                    else
                    {
                        Intent intent = new Intent(getActivity(), Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
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
            public void onFailure(Call<ResponseNormal2> call, Throwable t) {
                //swipeRefreshLayout.setRefreshing(false);
                //dialogWarning(getResources().getString(R.string.gagalCobaLagi));
            }
        });
    }

    public void getCountNotif()
    {
        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String accessToken = "Bearer " +data_profile.accessToken;

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseNormal2> userCall = service.getCountNotif(accessToken,"/user/notifikasi/count");
        userCall.enqueue(new Callback<ResponseNormal2>() {
            @Override
            public void onResponse(Call<ResponseNormal2> call, retrofit2.Response<ResponseNormal2> response) {
                swipeRefreshLayout.setRefreshing(false);
                //Log.w("", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful())
                {
                    String myMessage = ""+response.body().getreason();
                    String status = ""+response.body().getstatus();
                    if(status.equals("true"))
                    {
                        textNotifLive.setText(""+response.body().getdata());
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
            public void onFailure(Call<ResponseNormal2> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                //dialogWarning(getResources().getString(R.string.gagalCobaLagi));
            }
        });


    }

    public void getCountRiwayat()
    {
        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String accessToken = "Bearer " +data_profile.accessToken;

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseNormal2> userCall = service.getCountNotif(accessToken,"/pembayaran/count/pending");
        userCall.enqueue(new Callback<ResponseNormal2>() {
            @Override
            public void onResponse(Call<ResponseNormal2> call, retrofit2.Response<ResponseNormal2> response) {
                swipeRefreshLayout.setRefreshing(false);
                //Log.w("", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful())
                {
                    String myMessage = ""+response.body().getreason();
                    String status = ""+response.body().getstatus();
                    if(status.equals("true"))
                    {
                        textRiwayatLive.setText(""+response.body().getdata());
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
            public void onFailure(Call<ResponseNormal2> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                //dialogWarning(getResources().getString(R.string.gagalCobaLagi));
            }
        });


    }


    public void getDataProfile()
    {
        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer " +data_profile.accessToken;

//        nama.setText(""+data_profile.userName);

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseProfile2> userCall = service.getProfile2(tokenBearer,"/user/detail");
        userCall.enqueue(new Callback<ResponseProfile2>() {
            @Override
            public void onResponse(Call<ResponseProfile2> call, retrofit2.Response<ResponseProfile2> response) {
                Log.w("LOGIN", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful()){

                    String myMessage = ""+response.body().getreason();
                    String status = ""+response.body().getstatus();
                    if(status.equals("true"))
                    {
                        /*Picasso.with(getActivity())
                                .load("" + response.body().getdata().getPicture())
                                .noPlaceholder()
                                .resize(300, 300)
                                .centerCrop()
                                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                                .into(image);*/
                        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
                        data_profile.nik = "" + response.body().getdata().getnik();
                        data_profile.nama = "" + response.body().getdata().getnama();
                        data_profile.jenisIdentitas = "" + response.body().getdata().getjenisIdentitas();
                        data_profile.noHandphone = "" + response.body().getdata().getnoHandphone();
                        data_profile.alamat = "" + response.body().getdata().getalamat();
                        data_profile.save();
                        nama.setText(""+response.body().getdata().getnama());
                    }
                    else
                    {
                        Toast.makeText(getActivity(),""+response.body().getreason(),Toast.LENGTH_LONG).show();
                    }
                }
                else {
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
                    //Toast.makeText(getActivity(),""+message,Toast.LENGTH_SHORT).show();
                    //dialogWarningLogout(""+message);
                }
            }
            @Override
            public void onFailure(Call<ResponseProfile2> call, Throwable t) {
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
//                Data_Profile data_profileX = Data_Profile.findById(Data_Profile.class,1L);
//                data_profileX.accessToken = "";
//                data_profileX.login = false;
//                data_profileX.save();
//                getActivity().finish();
//                Intent intent2 = new Intent(getActivity(), Login.class);
//                startActivity(intent2);
            }
        });
        dialog.show();
    }

}
