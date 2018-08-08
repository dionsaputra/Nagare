package com.nagare.auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nagare.MainActivity;
import com.nagare.R;

import java.util.Timer;
import java.util.TimerTask;

public class OpeningActivity extends AppCompatActivity {
    private final int OPENING_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);

        // load openingBackground using glide to minimize memory use
        ImageView openingBackground = (ImageView) findViewById(R.id.opening_background);
        Glide.with(this).load(R.drawable.opening_background).into(openingBackground);

        startOpening(OPENING_TIME);
    }

    /***
     * Return true if user has login before.
     * @return
     */
    private boolean hasLogin() {
        // TODO use preference to check login state of user
        return true;
    }

    /**
     * Show opening layer in timerValue ms and then show next activity.
     * @param timerValue
     */
    private void startOpening(int timerValue) {
        try {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Class nextClass = hasLogin() ? MainActivity.class : Login.class;
                    Intent intent = new Intent(OpeningActivity.this, nextClass);
                    startActivity(intent);
                    finish();
                }
            }, timerValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
