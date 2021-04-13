package com.pusatgadaiindonesia.app.Interface.Homescreen.Beranda;

import android.content.pm.ActivityInfo;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pusatgadaiindonesia.app.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsFeedDetail extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.image)
    ImageView image;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.desc)
    TextView desc;

    @BindView(R.id.date)
    TextView date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_news_feed_detail);
        ButterKnife.bind(this);

        back.setOnClickListener(this);

        title.setText(""+getIntent().getStringExtra("title"));
        desc.setText(""+getIntent().getStringExtra("desc"));
        date.setText(""+getIntent().getStringExtra("date"));

        Picasso.get()
                .load("" + getIntent().getStringExtra("image"))
                .noPlaceholder()
                .resize(300, 300)
                .centerCrop()
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(image);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
        }
    }

}
