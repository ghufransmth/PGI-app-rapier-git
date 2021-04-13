package com.pusatgadaiindonesia.app.Interface.Homescreen;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.pusatgadaiindonesia.app.R;

public class FragmentKontrak extends Fragment implements View.OnClickListener {

    View rootview;

    LinearLayout layAktif, layRiwayat;
    RelativeLayout kontrakAktif, riwayatKontrak;
    LinearLayout aktifSelected, riwayatSelected;
    ImageView bayar;
    CheckBox checkBox;
    public FragmentKontrak() {
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

            rootview = inflater.inflate(R.layout.activity_fragment_kontrak, container, false);

            //SetUp layout To Specific
            /*card = rootview.findViewById(R.id.card);

            DisplayMetrics dm = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(dm);

            int width  = dm.widthPixels;
            int height = dm.heightPixels;

            int widthCard = width + dpToPx(75);
            int heightCard = height + dpToPx(17);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(widthCard, heightCard);
            params.setMargins(dpToPx(-75), 0, 0, dpToPx(-75));
            card.setLayoutParams(params);*/

            layAktif = rootview.findViewById(R.id.layAktif);
            layRiwayat = rootview.findViewById(R.id.layRiwayat);
            kontrakAktif = rootview.findViewById(R.id.kontrakAktif);
            riwayatKontrak = rootview.findViewById(R.id.riwayatKontrak);
            aktifSelected = rootview.findViewById(R.id.aktifSelected);
            riwayatSelected = rootview.findViewById(R.id.riwayatSelected);
            bayar = rootview.findViewById(R.id.bayar);
            checkBox = rootview.findViewById(R.id.checkbox);

            kontrakAktif.setOnClickListener(this);
            riwayatKontrak.setOnClickListener(this);
            layAktif.setOnClickListener(this);
            layRiwayat.setOnClickListener(this);
            bayar.setOnClickListener(this);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked)
                    {
                        bayar.setBackgroundResource(R.drawable.button_bayar);
                    }
                    else
                    {
                        bayar.setBackgroundResource(R.drawable.button_bayar_grey);
                    }
                }
            });
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
            case R.id.kontrakAktif:
                layAktif.setVisibility(View.VISIBLE);
                layRiwayat.setVisibility(View.GONE);
                aktifSelected.setVisibility(View.VISIBLE);
                riwayatSelected.setVisibility(View.GONE);
                break;

            case R.id.riwayatKontrak:
                layAktif.setVisibility(View.GONE);
                layRiwayat.setVisibility(View.VISIBLE);
                aktifSelected.setVisibility(View.GONE);
                riwayatSelected.setVisibility(View.VISIBLE);
                break;

            case R.id.layAktif:

                break;

            case R.id.layRiwayat:
                dialogLunas();
                break;

            case R.id.bayar:
                if(checkBox.isChecked())
                {
                    dialogCicilan();
                }
                else
                {
                    Toast.makeText(getActivity(),"Pilih barang terlebih dahulu",Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }


    private void dialogCicilan()
    {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.model_kontrak_undone);
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

    private void dialogLunas()
    {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.model_kontrak_done);
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
}
