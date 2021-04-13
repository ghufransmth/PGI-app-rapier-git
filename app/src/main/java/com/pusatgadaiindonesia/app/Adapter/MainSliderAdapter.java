package com.pusatgadaiindonesia.app.Adapter;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class MainSliderAdapter extends SliderAdapter {

    String[] myUrl;

    public MainSliderAdapter(String[] myUrl)
    {
        this.myUrl = myUrl;
    }

    @Override
    public int getItemCount() {
        return myUrl.length;
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {

        viewHolder.bindImageSlide(""+myUrl[position]);
        //viewHolder.bindImageSlide("https://assets.materialup.com/uploads/20ded50d-cc85-4e72-9ce3-452671cf7a6d/preview.jpg");


    }
}