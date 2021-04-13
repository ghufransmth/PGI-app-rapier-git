package com.pusatgadaiindonesia.app.Interface.Homescreen;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.pusatgadaiindonesia.app.Adapter.MainSliderAdapter;
import com.pusatgadaiindonesia.app.Config.Config;
import com.pusatgadaiindonesia.app.Database.Data_Profile;
import com.pusatgadaiindonesia.app.Interface.Auth.Login;
import com.pusatgadaiindonesia.app.Model.Banner.DataBannerDetail2;
import com.pusatgadaiindonesia.app.Model.Banner.ResponseBanner;
import com.pusatgadaiindonesia.app.Model.Device.SendDevice;
import com.pusatgadaiindonesia.app.Model.Kendaraan.SendKendaraan;
import com.pusatgadaiindonesia.app.Model.ResponseNormal2;
import com.pusatgadaiindonesia.app.R;
import com.pusatgadaiindonesia.app.Services.Retrofit.APIService;
import com.pusatgadaiindonesia.app.Services.Retrofit.ApiClient;
import com.vistrav.ask.Ask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;


/*HOmscreen bug tampilan
        Edit Profil error (permission ?)'
        Semua gadai gaisa
        gagal load profile
        masih ada bug pada data kendaraan, ketika kendaraan awal memilih motor maka opsi yang
        disediakan pada jenis merk merupakan merk motor namun ketika kendaraan berganti mobil
        maka opsi yang disediakan pada jenis merk tetap merk motor bukan merk mobil dan berlaku sebaliknya
        ketika kolom opsi ditarik kebawah, logo refresh stuck tidak bisa hilang
        gagal load profile, edit prpofile, logout*/

public class Homescreen extends AppCompatActivity implements View.OnClickListener {

    // @M - Tab
    @BindView(R.id.beranda)
    LinearLayout beranda;

    @BindView(R.id.kontrak)
    LinearLayout kontrak;

    @BindView(R.id.profil)
    LinearLayout profil;

    @BindView(R.id.bantuan)
    LinearLayout bantuan;

    @BindView(R.id.imgBeranda)
    ImageView imgBeranda;

    @BindView(R.id.imgKontrak)
    ImageView imgKontrak;

    @BindView(R.id.imgProfil)
    ImageView imgProfil;

    @BindView(R.id.imgBantuan)
    ImageView imgBantuan;

    @BindView(R.id.txtBeranda)
    TextView txtBeranda;

    @BindView(R.id.txtKontrak)
    TextView txtKontrak;

    @BindView(R.id.txtProfil)
    TextView txtProfil;

    @BindView(R.id.txtBantuan)
    TextView txtBantuan;

    // @M - End of Tab

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.tabs)
    TabLayout tabLayout;


    private int[] tabSelected = {
            R.drawable.menu1_selected,
            R.drawable.menu2_selected,
            R.drawable.menu3_selected,
            R.drawable.menu4_selected
    };

    private int[] tabUnselected = {
            R.drawable.menu1_unselected,
            R.drawable.menu2_unselected,
            R.drawable.menu3_unselected,
            R.drawable.menu4_unselected
    };

    int press = 0;

    View v1,v2,v3,v4;
    ImageView img1,img2,img3,img4;
    TextView text1,text2,text3,text4;
    TabLayout.Tab tab;
    SharedPreferences pref;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_homescreen);

        ButterKnife.bind(this);

        setupViewPager(viewPager);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorHeight(0);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //tab.setIcon(tabIcons2[tab.getPosition()]);
                /*Fragment frg = getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.viewpager+":"+viewPager.getCurrentItem());
                final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.detach(frg);
                ft.attach(frg);
                ft.commit();*/
                if(tab.getPosition()==0)
                {
                    imgBeranda.setImageResource(tabSelected[0]);
                    txtBeranda.setTextColor(getResources().getColor(R.color.blue_selected));
                    imgKontrak.setImageResource(tabUnselected[1]);
                    txtKontrak.setTextColor(getResources().getColor(R.color.grey_unselected));
                    imgProfil.setImageResource(tabUnselected[2]);
                    txtProfil.setTextColor(getResources().getColor(R.color.grey_unselected));
                    imgBantuan.setImageResource(tabUnselected[3]);
                    txtBantuan.setTextColor(getResources().getColor(R.color.grey_unselected));
                }
                else if(tab.getPosition()==1)
                {
                    imgBeranda.setImageResource(tabUnselected[0]);
                    txtBeranda.setTextColor(getResources().getColor(R.color.grey_unselected));
                    imgKontrak.setImageResource(tabSelected[1]);
                    txtKontrak.setTextColor(getResources().getColor(R.color.blue_selected));
                    imgProfil.setImageResource(tabUnselected[2]);
                    txtProfil.setTextColor(getResources().getColor(R.color.grey_unselected));
                    imgBantuan.setImageResource(tabUnselected[3]);
                    txtBantuan.setTextColor(getResources().getColor(R.color.grey_unselected));

                }
                else if(tab.getPosition()==2)
                {
                    imgBeranda.setImageResource(tabUnselected[0]);
                    txtBeranda.setTextColor(getResources().getColor(R.color.grey_unselected));
                    imgKontrak.setImageResource(tabUnselected[1]);
                    txtKontrak.setTextColor(getResources().getColor(R.color.grey_unselected));
                    imgProfil.setImageResource(tabSelected[2]);
                    txtProfil.setTextColor(getResources().getColor(R.color.blue_selected));
                    imgBantuan.setImageResource(tabUnselected[3]);
                    txtBantuan.setTextColor(getResources().getColor(R.color.grey_unselected));

                }
                else if(tab.getPosition()==3)
                {
                    imgBeranda.setImageResource(tabUnselected[0]);
                    txtBeranda.setTextColor(getResources().getColor(R.color.grey_unselected));
                    imgKontrak.setImageResource(tabUnselected[1]);
                    txtKontrak.setTextColor(getResources().getColor(R.color.grey_unselected));
                    imgProfil.setImageResource(tabUnselected[2]);
                    txtProfil.setTextColor(getResources().getColor(R.color.grey_unselected));
                    imgBantuan.setImageResource(tabSelected[3]);
                    txtBantuan.setTextColor(getResources().getColor(R.color.blue_selected));


                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                if(tab.getPosition()==0)
                {
                    imgBeranda.setImageResource(tabUnselected[0]);
                    txtBeranda.setTextColor(getResources().getColor(R.color.grey_unselected));
                }
                else if(tab.getPosition()==1)
                {
                    imgKontrak.setImageResource(tabUnselected[1]);
                    txtKontrak.setTextColor(getResources().getColor(R.color.grey_unselected));
                }
                else if(tab.getPosition()==2)
                {
                    imgProfil.setImageResource(tabUnselected[2]);
                    txtProfil.setTextColor(getResources().getColor(R.color.grey_unselected));
                }
                else if(tab.getPosition()==3)
                {
                    imgBantuan.setImageResource(tabUnselected[3]);
                    txtBantuan.setTextColor(getResources().getColor(R.color.grey_unselected));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Fragment frg = getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.viewpager+":"+viewPager.getCurrentItem());
                final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.detach(frg);
                ft.attach(frg);
                ft.commit();
            }
        });

        Data_Profile data = Data_Profile.findById(Data_Profile.class,1L);
        if((int)data.count(Data_Profile.class, "", null) == 0) {
            Data_Profile datax = new Data_Profile("", "", "", "", "","","","","","");
            datax.save();
            Intent intent = new Intent(this, Login.class);
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
            Log.d("millis",""+millis);
            Log.d("accessTokenExpAt",""+accessTokenExpAt);
            Log.d("refreshTokenExpAt",""+refreshTokenExpAt);
            Log.d("difference",""+difference);
            // @M - Check expired token
            if(millis > accessTokenExpAt){
                // @M - Refresh token
                if (difference > 2610000000L){
                    finish();
                    Intent intent = new Intent(this, Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else{
                    finish();
                    Intent intent = new Intent(this, Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }else{

            }
        }

        //FIREBASE MESSAGING
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( Homescreen.this,  new OnSuccessListener<InstanceIdResult>() {
                        @Override
                        public void onSuccess(InstanceIdResult instanceIdResult) {
                            String updatedToken = instanceIdResult.getToken();
                            Log.e("Updated Token",updatedToken);
                            if (!TextUtils.isEmpty(updatedToken))
                                register_device(updatedToken);
                            else
                                Toast.makeText(getApplicationContext(), "Firebase Reg Id is not received yet!", Toast.LENGTH_SHORT).show();

                        }
                    });

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                }
            }
        };

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( Homescreen.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String updatedToken = instanceIdResult.getToken();
                Log.e("Updated Token",updatedToken);
                if (!TextUtils.isEmpty(updatedToken))
                    register_device(updatedToken);
                else
                    Toast.makeText(getApplicationContext(), "Firebase Reg Id is not received yet!", Toast.LENGTH_SHORT).show();

            }
        });
        //END FIREBASE MESSAGING

        beranda.setOnClickListener(this);
        kontrak.setOnClickListener(this);
        profil.setOnClickListener(this);
        bantuan.setOnClickListener(this);
        //permission();
        checkPermission();
        setupTabIcons();
    }

    public void register_device(String deviceId)
    {
        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String accessToken = "Bearer " +data_profile.accessToken;

        SendDevice sendDevice = new SendDevice(""+deviceId);

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseNormal2> userCall = service.registerDevice(accessToken, sendDevice);
        userCall.enqueue(new Callback<ResponseNormal2>() {
            @Override
            public void onResponse(Call<ResponseNormal2> call, retrofit2.Response<ResponseNormal2> response) {
                //Log.w("", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful())
                {
                    String myMessage = ""+response.body().getreason();
                    String status = ""+response.body().getstatus();
                    if(status.equals("true"))
                    {

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),""+myMessage,Toast.LENGTH_SHORT).show();
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
                //dialogWarning(getResources().getString(R.string.gagalCobaLagi));
            }
        });


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentBeranda2(), "");
        adapter.addFragment(new FragmentDaftarGadai(), "");
        adapter.addFragment(new FragmentProfile(), "");
        adapter.addFragment(new FragmentBantuan(), "");
        viewPager.setAdapter(adapter);

        try {
            String cek = getIntent().getStringExtra("frgToLoad");
            Log.d("frgToLoad :", ""+cek);
            if(cek != null){
                if(cek.equals("1")){
                    tab = tabLayout.getTabAt(1);
                    tab.select();
                }else if(cek.equals("2")){
                    tab = tabLayout.getTabAt(1);
                    tab.select();
                }else{

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void setupTabIcons() {

        v1 = getLayoutInflater().inflate(R.layout.custome_tab, null);
        img1 = (ImageView) v1.findViewById(R.id.icontab);
        img1.setImageResource(tabSelected[0]);
        text1 = (TextView) v1.findViewById(R.id.texttab);
        text1.setText(""+getResources().getString(R.string.beranda));
        text1.setTextColor(getResources().getColor(R.color.blue_selected));
        tabLayout.getTabAt(0).setCustomView(v1);

        v2 = getLayoutInflater().inflate(R.layout.custome_tab, null);
        img2 = (ImageView) v2.findViewById(R.id.icontab);
        img2.setImageResource(tabUnselected[1]);
        text2 = (TextView) v2.findViewById(R.id.texttab);
        text2.setText(""+getResources().getString(R.string.kontrak));
        text2.setTextColor(getResources().getColor(R.color.grey_unselected));
        tabLayout.getTabAt(1).setCustomView(v2);

        v3 = getLayoutInflater().inflate(R.layout.custome_tab, null);
        img3 = (ImageView) v3.findViewById(R.id.icontab);
        img3.setImageResource(tabUnselected[2]);
        text3 = (TextView) v3.findViewById(R.id.texttab);
        text3.setText(""+getResources().getString(R.string.profil));
        text3.setTextColor(getResources().getColor(R.color.grey_unselected));
        tabLayout.getTabAt(2).setCustomView(v3);

        v4 = getLayoutInflater().inflate(R.layout.custome_tab, null);
        img4 = (ImageView) v4.findViewById(R.id.icontab);
        img4.setImageResource(tabUnselected[3]);
        text4 = (TextView) v4.findViewById(R.id.texttab);
        text4.setText(""+getResources().getString(R.string.bantuan));
        text4.setTextColor(getResources().getColor(R.color.grey_unselected));
        tabLayout.getTabAt(3).setCustomView(v4);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.beranda:
                tab = tabLayout.getTabAt(0);
                tab.select();
                break;

            case R.id.kontrak:
                tab = tabLayout.getTabAt(1);
                tab.select();
                break;

            case R.id.profil:
                tab = tabLayout.getTabAt(2);
                tab.select();
                break;

            case R.id.bantuan:
                tab = tabLayout.getTabAt(3);
                tab.select();
                break;
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            //return mFragmentTitleList.get(position);
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        if(press==0)
        {
            Fragment frg = getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.viewpager+":"+viewPager.getCurrentItem());
            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.detach(frg);
            ft.attach(frg);
            ft.commit();
            Toast.makeText(getApplicationContext(), "Press again to exit", Toast.LENGTH_SHORT).show();
            press = 1;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    press = 0;
                }
            },3000);
        }
        else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            press = 0;
        }

    }

    public void permission()
    {
        Ask.on(this)
                .id(1) // in case you are invoking multiple time Ask from same activity or fragment
                .forPermissions(Manifest.permission.ACCESS_COARSE_LOCATION
                        , Manifest.permission.ACCESS_FINE_LOCATION
                        , Manifest.permission.ACCESS_NETWORK_STATE
                        , Manifest.permission.READ_EXTERNAL_STORAGE
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE
                        , Manifest.permission.READ_PHONE_STATE)
                /*.withRationales("Location permission need for map to work properly",
                        "In order to save file you will need to grant storage permission") *///optional
                .go();
    }



    public void checkPermission()
    {
        int permission = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
        int permission2 = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        int permission3 = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_NETWORK_STATE);
        int permission4 = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE);
        int permission5 = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE);
        int permission6 = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
        int permission7 = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int permission8 = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        Log.e("Permission", permission+","+permission2+","+permission3+","+permission4+","+permission5+","+permission6+","+permission7+","+permission8);
        if (permission != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(Homescreen.this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    341);
        }
        else if (permission2 != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(Homescreen.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    342);
        }
        else if (permission3 != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(Homescreen.this,
                    new String[]{Manifest.permission.ACCESS_NETWORK_STATE},
                    343);
        }
        else if (permission4 != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(Homescreen.this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    344);
        }
        else if (permission5 != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(Homescreen.this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    345);
        }
        else if (permission7 != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(Homescreen.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    347);
        }
        else if (permission8 != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(Homescreen.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    348);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            checkPermission();
        }
        else
        {
            /*location 341, 342
            phone 343, 344, 345
            storage 347, 348*/
            if(requestCode == 341 || requestCode == 342)
            {
                dialogPermission();
            }
            else if(requestCode == 343 || requestCode == 344 || requestCode == 345)
            {
                dialogPermissionPhone();
            }
            else
            {
                dialogPermissionStorage();
            }

        }
    }


    public void dialogPermission()
    {
        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_permission);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        Button ok = (Button) dialog.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                checkPermission();
            }
        });

        dialog.show();
    }

    public void dialogPermissionPhone()
    {
        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_permission_call);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        Button ok = (Button) dialog.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                checkPermission();
            }
        });

        dialog.show();
    }

    public void dialogPermissionStorage()
    {
        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_permission_storage);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        Button ok = (Button) dialog.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                checkPermission();
            }
        });

        dialog.show();
    }
}
