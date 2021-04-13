package com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pusatgadaiindonesia.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GadaiKendaraan2 extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.lanjut)
    CardView lanjut;

    @BindView(R.id.editStnkNama)
    EditText editStnkNama;

    @BindView(R.id.editStnkAlamat)
    EditText editStnkAlamat;

    @BindView(R.id.editBpkbNo)
    EditText editBpkbNo;

    @BindView(R.id.editBpkbNama)
    EditText editBpkbNama;

    @BindView(R.id.editBpkbNoMesin)
    EditText editBpkbNoMesin;

    @BindView(R.id.editBpkbNoRangka)
    EditText editBpkbNoRangka;

    @BindView(R.id.layPajak)
    LinearLayout layPajak;

    @BindView(R.id.txtPajak)
    TextView txtPajak;

    String tanggalPajak = "";
    int dDay=1, dMonth=1, dYear=2020;


    String sendNama="", sendNopol="", sendKategori="", sendMerk="", sendTipe="",sendYear="", sendGrade="", sendAtasNamaId="", sendAtasNamaText="";
    String sendMaxLoan="", sendSubTipe="";/*, getPercentage="";*/
    String sendAtasNamaNumb="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_gadai_kendaraan2);
        ButterKnife.bind(this);

        back.setOnClickListener(this);
        lanjut.setOnClickListener(this);
        layPajak.setOnClickListener(this);

        sendNama = ""+getIntent().getStringExtra("sendNama");
        sendNopol = ""+getIntent().getStringExtra("sendNopol");
        sendKategori = ""+getIntent().getStringExtra("sendKategori");
        sendMerk = ""+getIntent().getStringExtra("sendMerk");
        sendTipe = ""+getIntent().getStringExtra("sendTipe");
        sendYear = ""+getIntent().getStringExtra("sendYear");
        sendGrade = ""+getIntent().getStringExtra("sendGrade");
        sendAtasNamaId = ""+getIntent().getStringExtra("sendAtasNamaId");
        sendAtasNamaText = ""+getIntent().getStringExtra("sendAtasNamaText");
        sendMaxLoan = ""+getIntent().getStringExtra("sendMaxLoan");
        sendSubTipe = ""+getIntent().getStringExtra("sendSubTipe");
        sendAtasNamaNumb = ""+getIntent().getStringExtra("sendAtasNamaNumb");
        //getPercentage = ""+getIntent().getStringExtra("percentage");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.back:
                onBackPressed();
                break;

            case R.id.lanjut:

                Boolean stnkNamaFilled = cekStnkNama();
                Boolean stnkAlamatFilled = cekStnkAlamat();
                Boolean bpkbNoFilled = cekBpkbNo();
                Boolean bpkbNamaFilled = cekBpkbNama();
                Boolean bpkbNoMesinFilled = cekBpkbNoMesin();
                Boolean bpkbNoRangkaFilled = cekBpkbNoRangka();
                Boolean pajakFilled = cekPajak();

                if(stnkNamaFilled && stnkAlamatFilled && bpkbNoFilled && bpkbNamaFilled && bpkbNoMesinFilled && bpkbNoRangkaFilled && pajakFilled)
                {
                    Intent intent = new Intent(GadaiKendaraan2.this, GadaiKendaraan3.class);
                    intent.putExtra("sendNama", ""+sendNama);
                    intent.putExtra("sendNopol", ""+sendNopol);
                    intent.putExtra("sendKategori", ""+sendKategori);
                    intent.putExtra("sendMerk", ""+sendMerk);
                    intent.putExtra("sendTipe", ""+sendTipe);
                    intent.putExtra("sendYear", ""+sendYear);
                    intent.putExtra("sendGrade", ""+sendGrade);
                    intent.putExtra("sendAtasNamaId", ""+sendAtasNamaId);
                    intent.putExtra("sendAtasNamaText", ""+sendAtasNamaText);
                    intent.putExtra("sendStnkNama", ""+editStnkNama.getText().toString());
                    intent.putExtra("sendStnkAlamat", ""+editStnkAlamat.getText().toString());
                    intent.putExtra("sendBpkbNo", ""+editBpkbNo.getText().toString());
                    intent.putExtra("sendBpkbNama", ""+editBpkbNama.getText().toString());
                    intent.putExtra("sendBpkbNoMesin", ""+editBpkbNoMesin.getText().toString());
                    intent.putExtra("sendBpkbNoRangka", ""+editBpkbNoRangka.getText().toString());
                    intent.putExtra("sendPajak", ""+tanggalPajak);
                    intent.putExtra("sendMaxLoan", ""+sendMaxLoan);
                    intent.putExtra("sendSubTipe", ""+sendSubTipe);
                    intent.putExtra("sendAtasNamaNumb", ""+sendAtasNamaNumb);
                    //intent.putExtra("percentage",""+getPercentage);
                    startActivity(intent);

                }

                break;

            case R.id.layPajak:
                showCalendar();
                break;

        }
    }

    public Boolean cekStnkNama()
    {
        String x = ""+editStnkNama.getText().toString();
        if(x.equals("") || x.equals("null"))
        {
            editStnkNama.setError("Masukan Nama pada STNK");
            return false;
        }
        else
        {
            editStnkNama.setError(null);
            return true;
        }
    }

    public Boolean cekStnkAlamat()
    {
        String x = ""+editStnkAlamat.getText().toString();
        if(x.equals("") || x.equals("null"))
        {
            editStnkAlamat.setError("Masukan Alamat pada STNK");
            return false;
        }
        else
        {
            editStnkAlamat.setError(null);
            return true;
        }
    }

    public Boolean cekBpkbNo()
    {
        String x = ""+editBpkbNo.getText().toString();
        if(x.equals("") || x.equals("null"))
        {
            editBpkbNo.setError("Masukan Nomor BPKB");
            return false;
        }
        else
        {
            editBpkbNo.setError(null);
            return true;
        }
    }

    public Boolean cekBpkbNama()
    {
        String x = ""+editBpkbNama.getText().toString();
        if(x.equals("") || x.equals("null"))
        {
            editBpkbNama.setError("Masukan Nama dalam BPKB");
            return false;
        }
        else
        {
            editBpkbNama.setError(null);
            return true;
        }
    }

    public Boolean cekBpkbNoMesin()
    {
        String x = ""+editBpkbNoMesin.getText().toString();
        if(x.equals("") || x.equals("null"))
        {
            editBpkbNoMesin.setError("Masukan Nomor Mesin dalam BPKB");
            return false;
        }
        else
        {
            editBpkbNoMesin.setError(null);
            return true;
        }
    }

    public Boolean cekBpkbNoRangka()
    {
        String x = ""+editBpkbNoRangka.getText().toString();
        if(x.equals("") || x.equals("null"))
        {
            editBpkbNoRangka.setError("Masukan Nomor Rangka dalam BPKB");
            return false;
        }
        else
        {
            editBpkbNoRangka.setError(null);
            return true;
        }
    }

    public Boolean cekPajak()
    {
        if(tanggalPajak.equals("") || tanggalPajak.equals("null"))
        {
            dialogWarning("Silakan masukkan tanggal berakhir pajak kendaraan");
            return false;
        }
        else
        {
            return true;
        }
    }

    public void showCalendar()
    {
        DatePickerDialog datePickerDialog = new android.app.DatePickerDialog(this,
                new android.app.DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        int month2 = monthOfYear+1;
                        String monthString = ""+month2;
                        String dayString = ""+dayOfMonth;
                        if(month2 > 9)
                        {
                            monthString = ""+month2;
                        }
                        else
                        {
                            monthString = "0"+month2;
                        }
                        if(dayOfMonth > 9)
                        {
                            dayString = ""+dayOfMonth;
                        }
                        else
                        {
                            dayString = "0"+dayOfMonth;
                        }
                        tanggalPajak = year+"-"+monthString+"-"+dayString;
                        txtPajak.setText(dayString+" / "+monthString+" / "+year);
                        dDay = dayOfMonth;
                        dMonth = monthOfYear;
                        dYear = year;
                    }
                }, dYear, dMonth, dDay);
        datePickerDialog.show();
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
}
