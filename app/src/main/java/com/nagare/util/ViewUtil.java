package com.nagare.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nagare.R;

import java.util.Random;

public class ViewUtil {

    /**
     * Start new activity by multiple sharedElements transition.
     * @param context               current activity
     * @param targetActivity        next activity
     * @param sharedElements        all elements to share
     */
    public static void startNewActivity(Context context, Class targetActivity, String paramKey, String[] params, Pair<View,String>... sharedElements) {
        Intent intent = new Intent(context, targetActivity);
        intent.putExtra(paramKey, params);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, sharedElements);
        context.startActivity(intent, options.toBundle());
    }

    /**
     * Start new activity by multiple sharedElements transition.
     * @param context               current activity
     * @param targetActivity        next activity
     * @param sharedElements        all elements to share
     */
    public static void startNewActivity(Context context, Class targetActivity, Pair<View,String>... sharedElements) {
        startNewActivity(context,targetActivity,null,null,sharedElements);
    }

    /**
     * Start new activity by single sharedElement transition.
     * @param context               current activity
     * @param targetActivity        next activity
     * @param sharedElement         element to share
     * @param transitionNameResId    sharedElement resource in string.xml
     */
    public static void startNewActivity(Context context, Class targetActivity, String paramKey, String[] params, View sharedElement, int transitionNameResId) {
        startNewActivity(context, targetActivity, paramKey, params, Pair.create(sharedElement, context.getString(transitionNameResId)));
    }

    /**
     * Start new activity by single sharedElement transition.
     * @param context               current activity
     * @param targetActivity        next activity
     * @param sharedElement         element to share
     * @param transitionNameResId    sharedElement resource in string.xml
     */
    public static void startNewActivity(Context context, Class targetActivity, View sharedElement, int transitionNameResId) {
        startNewActivity(context, targetActivity, Pair.create(sharedElement, context.getString(transitionNameResId)));
    }

    /**
     * Load image with glide to minimize memory use.
     * @param context           current activity
     * @param imageView         ImageView defined object
     * @param imageSrc    ImageView src in drawable
     */
    public static void loadImage(Context context, ImageView imageView, int imageSrc) {
        Glide.with(context).load(imageSrc).into(imageView);
    }

    public static void loadFragment(Context context, Fragment fragment, int fragmentResId) {
        FragmentTransaction transaction =
                ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
        transaction.replace(fragmentResId, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static int getRandomPlaceHolder() {
        int[] placeHolderDrawables = new int[] {
                R.drawable.placeholder1,
                R.drawable.placeholder2,
                R.drawable.placeholder3,
                R.drawable.placeholder4,
                R.drawable.placeholder5
        };
        Random random = new Random();
        return placeHolderDrawables[random.nextInt(5)];
    }
}
