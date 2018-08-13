package com.nagare.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

public class ViewUtil {
    /**
     * Start new activity by sharedElement transition.
     * @param targetActivity        next activity to start
     * @param sharedElement         shared element between two activity
     * @param sharedElementResId    string resource of sharedElement
     */
    public static void startNewActivity(Context context, Class targetActivity, View sharedElement, int sharedElementResId) {
        Intent intent = new Intent(context, targetActivity);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                (Activity) context, sharedElement, context.getString(sharedElementResId));
        context.startActivity(intent, options.toBundle());
    }
}
