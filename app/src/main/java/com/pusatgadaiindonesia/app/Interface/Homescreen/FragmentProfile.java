package com.pusatgadaiindonesia.app.Interface.Homescreen;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.pusatgadaiindonesia.app.Database.Data_Profile;
import com.pusatgadaiindonesia.app.Interface.Auth.Login;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Profile.EditProfile;
import com.pusatgadaiindonesia.app.Interface.Homescreen.Profile.EditProfile2;
import com.pusatgadaiindonesia.app.Model.Profile.ResponseProfile;
import com.pusatgadaiindonesia.app.Model.ResponseNormal;
import com.pusatgadaiindonesia.app.R;
import com.pusatgadaiindonesia.app.Services.ImageHandling.ImageCircleTransform;
import com.pusatgadaiindonesia.app.Services.Retrofit.APIService;
import com.pusatgadaiindonesia.app.Services.Retrofit.ApiClient;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.WINDOW_SERVICE;

public class FragmentProfile extends Fragment implements View.OnClickListener {

    View rootview;
    CardView card;

    ImageView editProfile, logout;


    @BindView(R.id.image)
    ImageView image;

    @BindView(R.id.gantiPhoto)
    ImageView gantiPhoto;

    @BindView(R.id.nama)
    TextView nama;

    @BindView(R.id.email)
    TextView email;

    @BindView(R.id.tanggal)
    TextView tanggal;

    @BindView(R.id.nomorIdentitas)
    TextView nomorIdentitas;

    @BindView(R.id.alamat)
    TextView alamat;

    @BindView(R.id.handphone)
    TextView handphone;

    Dialog loading;
    String userId = "";

    Uri uriImage;
    public FragmentProfile() {
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

            rootview = inflater.inflate(R.layout.activity_fragment_profile, container, false);

            ButterKnife.bind(this, rootview);

            card = rootview.findViewById(R.id.card);

            DisplayMetrics dm = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(dm);

            int width  = dm.widthPixels;
            int height = dm.heightPixels;

            int widthCard = width + dpToPx(75);
            int heightCard = height + dpToPx(17);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(widthCard, heightCard);
            params.setMargins(dpToPx(-75), 0, 0, dpToPx(-75));
            card.setLayoutParams(params);

            Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
            String nama2 = ""+data_profile.nama;
            String jenisIdentitas = ""+data_profile.nik;
            String alamat2 = ""+data_profile.alamat;
            String noHandphone2 = ""+data_profile.noHandphone;

            email.setText(""+nama2);
            nomorIdentitas.setText(""+jenisIdentitas);
            alamat.setText(""+alamat2);
            handphone.setText(""+noHandphone2);

            editProfile = rootview.findViewById(R.id.editProfile);
            logout = rootview.findViewById(R.id.logout);

            editProfile.setOnClickListener(this);
            logout.setOnClickListener(this);
            gantiPhoto.setOnClickListener(this);

//            if(data_profile.login == true)
//            {
//                getDataProfile();
//            }
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

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.editProfile:
                Intent intent = new Intent(getActivity(), EditProfile2.class);
                startActivityForResult(intent, 1);
                break;

            case R.id.logout:
                Data_Profile data_profileX = Data_Profile.findById(Data_Profile.class,1L);
                data_profileX.accessToken = "";
                data_profileX.login = false;
                data_profileX.save();
                getActivity().finish();
                Intent intent2 = new Intent(getActivity(), Login.class);
                startActivity(intent2);
//                logout_func();
                break;

            case R.id.gantiPhoto:
                //Toast.makeText(getActivity(), "Ganti Photo", Toast.LENGTH_SHORT).show();
                changeAvatar();
                break;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        if(isVisibleToUser)
//        {
//            Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
//            if(data_profile.login == true)
//            {
//                if(nama == null)
//                {
//
//                }
//                else
//                {
//                    getDataProfile();
//                }
//
//            }
//        }
    }

    public void getDataProfile()
    {
        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        nama.setText(""+data_profile.userName);
        email.setText(""+data_profile.userEmail);

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseProfile> userCall = service.getProfile(tokenBearer);
        userCall.enqueue(new Callback<ResponseProfile>() {
            @Override
            public void onResponse(Call<ResponseProfile> call, retrofit2.Response<ResponseProfile> response) {
                //Log.w("LOGIN", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful()){
                    String status = ""+response.body().getstatus();
                    if(status.equals("success"))
                    {
                        userId = ""+response.body().getdata().getid();
                        if(!response.body().getdata().getProfilePicture().equals(""))
                        {
                            Picasso.get()
                                    .load("" + response.body().getdata().getProfilePicture())
                                    .noPlaceholder()
                                    .transform(new ImageCircleTransform())
                                    .resize(300, 300)
                                    .centerCrop()
                                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                                    .into(image);
                        }


                        nama.setText(""+response.body().getdata().getname());
                        email.setText(""+response.body().getdata().getemail());
                        tanggal.setText(""+response.body().getdata().getdateOfBirth());
                        nomorIdentitas.setText(""+response.body().getdata().getidentityNumber());
                        alamat.setText(""+response.body().getdata().getaddress());
                        handphone.setText(""+response.body().getdata().getphone());
                    }
                    else
                    {
                        Toast.makeText(getActivity(),""+response.body().getmessage(),Toast.LENGTH_SHORT).show();
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
                }
            }
            @Override
            public void onFailure(Call<ResponseProfile> call, Throwable t) {
            }
        });
    }


    public void logout_func()
    {
        dialogLoading();
        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseProfile> userCall = service.logout(tokenBearer);
        userCall.enqueue(new Callback<ResponseProfile>() {
            @Override
            public void onResponse(Call<ResponseProfile> call, retrofit2.Response<ResponseProfile> response) {
                loading.dismiss();
                Log.w("LOGIN", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful()){
                    String status = ""+response.body().getstatus();
                    if(status.equals("true"))
                    {
                        Toast.makeText(getActivity(),""+response.body().getmessage(),Toast.LENGTH_SHORT).show();
                        Data_Profile data_profileX = Data_Profile.findById(Data_Profile.class,1L);
                        data_profileX.accessToken = "";
                        data_profileX.login = false;
                        data_profileX.save();
                        getActivity().finish();
                        Intent intent2 = new Intent(getActivity(), Login.class);
                        startActivity(intent2);

                    }
                    else
                    {
                        //Toast.makeText(getActivity(),"Masuk 1",  Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(),""+response.body().getmessage(),Toast.LENGTH_SHORT).show();
                        Data_Profile data_profileX = Data_Profile.findById(Data_Profile.class,1L);
                        data_profileX.accessToken = "";
                        data_profileX.login = false;
                        data_profileX.save();
                        getActivity().finish();
                        Intent intent2 = new Intent(getActivity(), Login.class);
                        startActivity(intent2);
                    }
                }
                else {
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
                    //Toast.makeText(getActivity(),"Masuk 2",  Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(),""+message, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseProfile> call, Throwable t) {
                //Toast.makeText(getActivity(),"Masuk 3",  Toast.LENGTH_SHORT).show();
                //Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), "Logout Success", Toast.LENGTH_SHORT).show();
                Data_Profile data_profileX = Data_Profile.findById(Data_Profile.class,1L);
                data_profileX.accessToken = "";
                data_profileX.login = false;
                data_profileX.save();
                getActivity().finish();
                Intent intent2 = new Intent(getActivity(), Login.class);
                startActivity(intent2);
                loading.dismiss();
            }
        });
    }


    public void changeAvatar()
    {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setFixAspectRatio(true)
                .start(getActivity(), this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Toast.makeText(getActivity(),"request "+requestCode+", result "+resultCode,Toast.LENGTH_SHORT).show();
//        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//                //Toast.makeText(getActivity(),  "Sukses",  Toast.LENGTH_SHORT) .show();
//                uriImage = result.getUri();
//                updateAvatar();
//                //image.setImageURI(uriImage);
//
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Exception error = result.getError();
//                Toast.makeText(getActivity(),  ""+error,  Toast.LENGTH_SHORT) .show();
//            }
//        }
//        else if(requestCode == 1)
//        {
//            getDataProfile();
//        }
    }

    public void dialogLoading()
    {
        // @M - showing the loading dialog
        loading = new Dialog(getActivity());
        loading.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loading.setContentView(R.layout.dialog_loading);
        loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loading.show();
    }


    public void updateAvatar()
    {
        dialogLoading();
        File file1 = new File(uriImage.getPath());

        MultipartBody.Part sendId = MultipartBody.Part.createFormData("id", ""+userId);
        RequestBody requestBody = RequestBody.create(MediaType.parse("**/*//*"), file1);
        MultipartBody.Part sendImage = MultipartBody.Part.createFormData("image", file1.getName(), requestBody);

        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseNormal> userCall = service.updateAvatar(tokenBearer, sendId, sendImage);
        userCall.enqueue(new Callback<ResponseNormal>() {
            @Override
            public void onResponse(Call<ResponseNormal> call, retrofit2.Response<ResponseNormal> response) {
                loading.dismiss();
                if(response.isSuccessful()){
                    Picasso.get()
                            .load(uriImage)
                            .noPlaceholder()
                            .resize(400, 400)
                            .transform(new ImageCircleTransform())
                            .centerCrop()
                            .into(image);
                    Toast.makeText(getActivity(), "" + response.body().getmessage(), Toast.LENGTH_SHORT).show();
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
                        message = response.body().getmessage();
                    } catch (IOException e) {
                        e.printStackTrace();
                        message = response.body().getmessage();
                    }
                    final String myMessage = ""+message;
                    Handler handler =  new Handler();
                    Runnable myRunnable = new Runnable() {
                        public void run() {
                            // do something
                            Toast.makeText(getActivity(), "" + myMessage, Toast.LENGTH_SHORT).show();
                        }
                    };
                    handler.postDelayed(myRunnable,500);
                }
            }
            @Override
            public void onFailure(Call<ResponseNormal> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getActivity(), "" + getResources().getString(R.string.gagalCobaLagi), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
