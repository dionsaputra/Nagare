package com.nagare.auth;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.nagare.R;
import com.nagare.util.ViewUtil;

public class SignUpActivity extends AppCompatActivity {
    private Context context = SignUpActivity.this;

    private ImageView nagareLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_signup);

        initComponent();
    }

    private void initComponent() {
        nagareLogo = findViewById(R.id.iv_nagare_logo);
        ViewUtil.loadImage(context, nagareLogo, R.drawable.nagare_logo);
    }

}
