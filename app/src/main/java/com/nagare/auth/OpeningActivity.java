package com.nagare.auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nagare.MainActivity;
import com.nagare.R;

public class OpeningActivity extends AppCompatActivity {
    private final int OPENING_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);

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
        ImageView nagareLogo = (ImageView) findViewById(R.id.nagare_logo);
        nagareLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasLogin()) {
                    Intent intent = new Intent(OpeningActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(OpeningActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
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
