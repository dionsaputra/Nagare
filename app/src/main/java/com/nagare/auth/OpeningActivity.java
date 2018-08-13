package com.nagare.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nagare.MainActivity;
import com.nagare.R;

public class OpeningActivity extends AppCompatActivity {
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_opening);

        loadOpeningBackground();
        setNagareLogoAction();
    }

    /**
     * Load opening background image using glide to minimize memory use.
     */
    private void loadOpeningBackground() {
        ImageView openingBackground = (ImageView) findViewById(R.id.opening_background);
        Glide.with(this).load(R.drawable.opening_background).into(openingBackground);
    }

    /**
     * Define action to do when nagare logo clicked.
     */
    private void setNagareLogoAction() {
        final ImageView nagareLogo = (ImageView) findViewById(R.id.nagare_logo);
        nagareLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasLogin()) {
                    startNewActivity(MainActivity.class, nagareLogo, R.string.nagare_logo);
                } else {
                    startNewActivity(LoginActivity.class, nagareLogo, R.string.nagare_logo);
                }
            }
        });
    }

    /**
     * Start new activity by sharedElement transition.
     * @param targetActivity        next activity to start
     * @param sharedElement         shared element between two activity
     * @param sharedElementResId    string resource of sharedElement
     */
    private void startNewActivity(Class targetActivity, View sharedElement, int sharedElementResId) {
        Intent intent = new Intent(context, targetActivity);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                (Activity) context, sharedElement, getString(sharedElementResId));
        startActivity(intent, options.toBundle());
    }

    /**
     * @return true if user has login, else false.
     */
    private boolean hasLogin() {
        // TODO use preference to check login state of user
        return false;
    }
}
