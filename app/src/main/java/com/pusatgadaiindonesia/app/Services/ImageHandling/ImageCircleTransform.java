package com.pusatgadaiindonesia.app.Services.ImageHandling;

/**
 * Created by HP-PC on 30/03/2018.
 */


import android.graphics.Bitmap;

import com.squareup.picasso.Transformation;

public class ImageCircleTransform implements Transformation {
    @Override
    public Bitmap transform(Bitmap source) {
        return ImageUtilities.getCircularBitmapImage(source);
    }
    @Override
    public String key() {
        return "circle-image";
    }
}