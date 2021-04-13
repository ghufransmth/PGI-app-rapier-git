package com.pusatgadaiindonesia.app;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.pusatgadaiindonesia.app.Database.Data_Profile;
import com.pusatgadaiindonesia.app.Interface.Auth.Login;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Homescreen;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Splashscreen;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        Data_Profile data = Data_Profile.findById(Data_Profile.class,1L);
        if((int)data.count(Data_Profile.class, "", null) == 0) {
            Data_Profile datax = new Data_Profile("", "", "", "", "","","","","","");
            datax.save();
            Intent intent = new Intent(MainActivity.this, Login.class);
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
                    Intent intent = new Intent(MainActivity.this, Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else{
                    Log.d("refreshTokenExpAt",""+refreshTokenExpAt);
                    data.accessToken = ""+data.refreshToken;
                    data.save();
                    Intent intent = new Intent(MainActivity.this, Homescreen.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }else{
                Intent intent = new Intent(MainActivity.this, Homescreen.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }


    }
}
