package com.nagare.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nagare.MainActivity;
import com.nagare.R;
import com.nagare.util.ViewUtil;

public class OpeningActivity extends AppCompatActivity {
    private final Context context = this;

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
        Glide.with(context).load(R.drawable.opening_background).into(openingBackground);
    }

    /**
     * Define action to do when nagare logo clicked.
     */
    private void setNagareLogoAction() {
        final ImageView nagareLogo = (ImageView) findViewById(R.id.nagare_logo);
        nagareLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class targetActivity = hasLogin() ? MainActivity.class : LoginActivity.class;
                ViewUtil.startNewActivity(context, targetActivity, nagareLogo, R.string.tn_nagare_logo);
            }
        });
    }

    /**
     * @return true if user has login, else false.
     */
    private boolean hasLogin() {
        // TODO use preference to check login state of user
        return false;
    }
}
