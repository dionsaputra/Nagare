package com.nagare.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nagare.R;

public class ViewUtil {

    /**
     * Start new activity by multiple sharedElements transition.
     * @param context               current activity
     * @param targetActivity        next activity
     * @param sharedElements        all elements to share
     */
    public static void startNewActivity(Context context, Class targetActivity, Pair<View,String>... sharedElements) {
        Intent intent = new Intent(context, targetActivity);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, sharedElements);
        context.startActivity(intent, options.toBundle());
    }

    /**
     * Start new activity by single sharedElement transition.
     * @param context               current activity
     * @param targetActivity        next activity
     * @param sharedElement         element to share
     * @param sharedElementResId    sharedElement resource in string.xml
     */
    public static void startNewActivity(Context context, Class targetActivity, View sharedElement, int sharedElementResId) {
        startNewActivity(context, targetActivity, Pair.create(sharedElement, context.getString(sharedElementResId)));
    }

    public static void loadImage(Context context, ImageView imageView, int imageViewResId) {
        Glide.with(context).load(imageViewResId).into(imageView);
    }
}
