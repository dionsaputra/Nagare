package com.nagare.auth;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.nagare.MainActivity;
import com.nagare.R;
import com.nagare.util.DataUtil;
import com.nagare.util.PermissionUtil;
import com.nagare.util.ViewUtil;

public class OpeningActivity extends AppCompatActivity {
    private final Context context = this;

    private ImageView nagareLogo, openingBackground;
    private FrameLayout openingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(hasLogin()){
            ViewUtil.startNewActivity(context, MainActivity.class, nagareLogo, R.string.tn_nagare_logo);
        }

        setContentView(R.layout.auth_opening);
        initComponent();
        setOpeningLayoutAction();
        PermissionUtil.requestPermission(this, 1, Manifest.permission.ACCESS_FINE_LOCATION, true);

    }

    /**
     * Define all component, load image if exist.
     */
    private void initComponent() {
        openingLayout = findViewById(R.id.fl_opening);
        nagareLogo = findViewById(R.id.iv_nagare_logo);
        openingBackground = findViewById(R.id.iv_opening_background);

        ViewUtil.loadImage(context, nagareLogo, R.drawable.nagare_logo);
        ViewUtil.loadImage(context, openingBackground, R.drawable.opening_background);
    }

    /**
     * Define action to do when openingLayout clicked.
     */
    private void setOpeningLayoutAction() {
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
        SharedPreferences spLogin;
        spLogin = getSharedPreferences("login",MODE_PRIVATE);
        if(spLogin.getBoolean("logged",false)){
            DataUtil.USER_KEY = spLogin.getString("userKey","");
            DataUtil.USER_NAMA = spLogin.getString("userNama","");
            DataUtil.USER_PASSWORD = spLogin.getString("userPassword","");
            DataUtil.USER_EMAIL = spLogin.getString("userEmail","");
            DataUtil.USER_LURAH = spLogin.getBoolean("userLurah",false);
            return true;
        } else {
            return false;
        }
    }

}
