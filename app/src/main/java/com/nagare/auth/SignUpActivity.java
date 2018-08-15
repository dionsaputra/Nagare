package com.nagare.auth;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nagare.R;
import com.nagare.util.ViewUtil;

public class SignUpActivity extends AppCompatActivity {
    private Context context = SignUpActivity.this;

    private ImageView       nagareLogo;
    private LinearLayout    emailLayout, passwordLayout;
    private TextView        loginTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_signup);

        initComponent();
        setLoginAction();
    }

    /**
     * Define all component, load image if exist.
     */
    private void initComponent() {
        nagareLogo      = findViewById(R.id.iv_nagare_logo);
        emailLayout     = findViewById(R.id.ll_email_field);
        passwordLayout  = findViewById(R.id.ll_password_field);
        loginTextView   = findViewById(R.id.tv_login);

        ViewUtil.loadImage(context, nagareLogo, R.drawable.nagare_logo);
    }

    /**
     * Define action to do when login text clicked.
     */
    private void setLoginAction() {
        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
