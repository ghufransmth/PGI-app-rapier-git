package com.pusatgadaiindonesia.app.Interface.Homescreen;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.pusatgadaiindonesia.app.Database.Data_Profile;
import com.pusatgadaiindonesia.app.Interface.Auth.Login;
import com.pusatgadaiindonesia.app.R;

import java.util.Calendar;

public class Splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_splashscreen);

        // @M - redirect to homesceen after 2 seconds loading
        Handler handler =  new Handler();
        Runnable myRunnable = new Runnable() {
            public void run() {
                // do something
                Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
                long accessTokenExpAt = Long.valueOf(data_profile.accessTokenExpAt);
                long refreshTokenExpAt = Long.valueOf(data_profile.refreshTokenExpAt);
                if(data_profile.accessToken != null || data_profile.accessToken != "")
                {
                    //creating Calendar instance
                    Calendar calendar = Calendar.getInstance();
                    //Returns current time in millis
                    long millis = calendar.getTimeInMillis();
                    //Returns current time + one month in millis
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.add(Calendar.MONTH, 1);
                    long millisOneMonth = calendar2.getTimeInMillis();
                    long difference = refreshTokenExpAt - accessTokenExpAt;
                    Log.d("millis",""+millis);
                    Log.d("accessTokenExpAt",""+accessTokenExpAt);
                    Log.d("refreshTokenExpAt",""+refreshTokenExpAt);
                    Log.d("difference",""+difference);
                    // @M - Check expired token
                    if(millis > accessTokenExpAt){
                        // @M - Refresh token
                        if (difference > 2610000000L){
                            Intent intent = new Intent(Splashscreen.this, Login.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }else{
                            Log.d("refreshTokenExpAt",""+refreshTokenExpAt);
                            data_profile.accessToken = ""+data_profile.refreshToken;
                            data_profile.save();
                            Intent intent = new Intent(Splashscreen.this, Homescreen.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }else{
                        Intent intent = new Intent(Splashscreen.this, Homescreen.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }else{
                    Intent intent = new Intent(Splashscreen.this, Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }


            }
        };
    }
}
