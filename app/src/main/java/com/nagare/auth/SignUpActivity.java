package com.nagare.auth;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nagare.R;
import com.nagare.fragment.Firebase;
import com.nagare.util.ViewUtil;
import com.nagare.model.User;
/**
 * FIREBASE
 */
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private Context context = SignUpActivity.this;
    private ImageView nagareLogo;
    private LinearLayout emailLayout;
    private LinearLayout passwordLayout;
    private TextView loginTextView;
    private Button signUpButton;
    private EditText fullnameEt;
    private EditText emailEt;
    private EditText passwordEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_signup);
        initComponent();
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
        setLoginAction();
    }

    /**
     * Define all component, load image if exist.
     */
    private void initComponent() {
        fullnameEt = findViewById(R.id.et_full_name);
        emailEt = findViewById(R.id.et_email);
        passwordEt = findViewById(R.id.et_password);
        nagareLogo = findViewById(R.id.iv_nagare_logo);
        emailLayout = findViewById(R.id.ll_email_field);
        passwordLayout = findViewById(R.id.ll_password_field);
        loginTextView = findViewById(R.id.tv_login);
        signUpButton = findViewById(R.id.btn_sign_up);
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

    private void signUp() {
        /**
         * TODO : Input validation
         */
        String fullname = fullnameEt.getText().toString();
        String email = emailEt.getText().toString();
        String password = passwordEt.getText().toString();

        DatabaseReference users = FirebaseDatabase.getInstance().getReference().child("users");
        User newUser = new User(fullname, email, password);
        Map<String, Object> newUserMap = new HashMap<>();
        String key = Integer.toString(email.hashCode());
        newUserMap.put(key, newUser);
        users.updateChildren(newUserMap);
    }
}
