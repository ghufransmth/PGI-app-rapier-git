package com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pusatgadaiindonesia.app.Database.Data_Profile;
import com.pusatgadaiindonesia.app.Model.Location.DataLocation;
import com.pusatgadaiindonesia.app.Model.Location.DataLocation2;
import com.pusatgadaiindonesia.app.Model.Location.ResponseLocation;
import com.pusatgadaiindonesia.app.Model.Location.ResponseLocation2;
import com.pusatgadaiindonesia.app.R;
import com.pusatgadaiindonesia.app.Services.Retrofit.APIService;
import com.pusatgadaiindonesia.app.Services.Retrofit.ApiClient;
import com.pusatgadaiindonesia.app.Utils.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class CabangTerdekat extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, LocationListener,GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.cari)
    CardView cari;

    @BindView(R.id.editCari)
    EditText editCari;

    @BindView(R.id.textAlamat)
    TextView textAlamat;

    GoogleMap mMap;
    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    Geocoder geocoder;

    Dialog loading;

    List<DataLocation2> listAllDataLocation = new ArrayList<>();
    List<Address> addresses;
    LatLng nearestLatLng;
    private Circle mCircle;
    String getAlamat = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_cabang_terdekat);
        ButterKnife.bind(this);

//        SupportMapFragment mapFragment =(SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.mapView2);
//        mapFragment.getMapAsync(this);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFrag = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView2);
        mapFrag.getMapAsync(this);

        back.setOnClickListener(this);
        cari.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.back:
                onBackPressed();
                break;

            case R.id.cari:
                //Toast.makeText(getApplicationContext(),"Fitur pencarian Belum Tersedia", Toast.LENGTH_SHORT).show();
//                loadLocation(editCari.getText().toString());
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        MyApplication.activityPaused();// On Pause notify the Application
        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (com.google.android.gms.location.LocationListener) this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-6.2402391, 106.8688949);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Pusat Gadai Indonesia"));
        //.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_utama_bpkb))
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(sydney)
                .zoom(17)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),2000,null);*/
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        loadLocation("");
        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        }
        else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {}

    @Override
    public void onLocationChanged(Location location)
    {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(latLng);
//        markerOptions.title("Current Position");
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
//        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);

        //move map camera
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
        //location Address
        geocoder = new Geocoder(this, Locale.getDefault());


        if(mCircle == null){
            drawMarkerWithCircle(latLng);
        }else{
            updateMarkerWithCircle(latLng);
        }
    }

    public void drawMarkerWithCircle(LatLng latLng){

        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

            if (addresses != null && addresses.size() > 0) {
                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();


//                textView8.setText(address + "," + country + "," + postalCode + "," + state );
                Log.d("city:",""+city);
                Log.d("state:",""+state);
                Log.d("postalCode:",""+postalCode);
                Log.d("knownName:",""+knownName);
                textAlamat.setText(""+address);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        drawMarker(latLng);

        double radiusInMeters = 500;
        CircleOptions circleOptions = new CircleOptions().center(latLng).radius(radiusInMeters)   //set radius in meters
                .fillColor(Color.TRANSPARENT)  //default
                .strokeColor(Color.CYAN)
                .strokeWidth(5);
        mCircle = mGoogleMap.addCircle(circleOptions);

        Log.d("draw","1");
    }

    public void updateMarkerWithCircle(LatLng latLng){
        Log.d("update","2");
        mCircle.setCenter(latLng);
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(CabangTerdekat.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void dialogLoading() {
        // @M - showing the loading dialog
        loading = new Dialog(this);
        loading.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loading.setContentView(R.layout.dialog_loading);
        loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loading.setCancelable(false);
    }

    public void dialogWarning(String message)
    {
        // @M - showing the warning dialog in old style dialog
        final Dialog dialog = new Dialog(this);
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
            }
        });
        dialog.show();
    }

    public void drawMarker(LatLng loc)
    {
        dialogLoading();
        mGoogleMap.clear();
        Data_Profile data_profile = Data_Profile.findById(Data_Profile.class,1L);
        String tokenBearer = "Bearer" + " " +data_profile.accessToken;

        String baseUrl = ""+ApiClient.BASE_URL;

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ResponseLocation2> userCall = service.getAllLocation2(tokenBearer, baseUrl+"branch/?lat="+loc.latitude+"&lon="+loc.longitude);
        userCall.enqueue(new Callback<ResponseLocation2>() {
            @Override
            public void onResponse(Call<ResponseLocation2> call, retrofit2.Response<ResponseLocation2> response) {
                if(response.isSuccessful())
                {
                    String status = ""+response.body().getstatus();
                    Toast.makeText(getApplicationContext(),""+response.body().getreason(),Toast.LENGTH_SHORT).show();
                    if(status.equals("true"))
                    {
                        listAllDataLocation = new ArrayList<>();
                        List<DataLocation2> arrayku = response.body().getdata();
                        if(arrayku.size() > 0)
                        {
                            Boolean nearestLoaded = false;
                            for (int  i=0; i<arrayku.size();  i++)
                            {
                                DataLocation2 dataLocation = new DataLocation2(
                                        ""+arrayku.get(i).getname(),
                                        ""+arrayku.get(i).getLatitude(),
                                        ""+arrayku.get(i).getLongitude(),
                                        ""+arrayku.get(i).getAddress(),
                                        ""+arrayku.get(i).getProvince(),
                                        ""+arrayku.get(i).getCity()
                                );
                                listAllDataLocation.add(dataLocation);

                                try {
                                    Double xLat = 0.0;
                                    Double xLng = 0.0;
                                    xLat = Double.parseDouble(""+arrayku.get(i).getLatitude());
                                    xLng = Double.parseDouble(""+arrayku.get(i).getLongitude());
                                    LatLng latLng = new LatLng(xLat, xLng);
                                    Marker mark = mGoogleMap.addMarker(new MarkerOptions().position(latLng).title(""+arrayku.get(i).getname()));
                                    if(nearestLoaded == false)
                                    {
                                        mark.showInfoWindow();
                                        nearestLatLng = latLng;
                                        nearestLoaded = true;
                                    }

                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                            }

                            CameraPosition cameraPosition = new CameraPosition.Builder()
                                    .target(nearestLatLng)
                                    .zoom(17)
                                    .build();
                            mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),2000,null);

                        }
                        else
                        {
                            dialogWarning("Data Lokasi Kosong");
                        }
                    }
                    else
                    {
                        dialogWarning(""+response.body().getreason());
                    }

                }
                else
                {
                    // @M - If response from server is not success, get the message and show it in dialog
                    String message = "Gagal. Silakan coba lagi";
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                        message = ""+jObjError.getString("message");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    dialogWarning(""+message);
                }
                loading.dismiss();
            }
            @Override
            public void onFailure(Call<ResponseLocation2> call, Throwable t) {
                Log.w("Lokasi","Error : "+t.getMessage());
                loading.dismiss();
                dialogWarning(""+t.getMessage());
            }
        });
    }
}
