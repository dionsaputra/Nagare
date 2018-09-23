package com.nagare.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.nagare.MainActivity;
import com.nagare.R;
import com.nagare.model.User;
import com.nagare.util.DataUtil;
import com.nagare.util.ViewUtil;

public class LoginActivity extends AppCompatActivity {
    private final Context context = LoginActivity.this;

    private ImageView nagareLogo;
    private LinearLayout emailLayout, passwordLayout, loginForm, progressView, loginTextView;
    private Button loginButton;
    private EditText emailField, passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_login);
        initComponent();

    }

    private void initComponent() {
        nagareLogo      = findViewById(R.id.iv_nagare_logo);
        emailLayout     = findViewById(R.id.ll_email_field);
        passwordLayout  = findViewById(R.id.ll_password_field);
        loginButton     = findViewById(R.id.btn_login);
        emailField      = findViewById(R.id.et_email);
        passwordField   = findViewById(R.id.et_password);
        loginForm       = findViewById(R.id.ll_form_login);
        progressView    = findViewById(R.id.ll_progress_view);

        ViewUtil.loadImage(context, nagareLogo, R.drawable.nagare_logo);
    }

    public void onSignUpClick(View view) {
        Pair[] sharedElements = new Pair[]{
                Pair.create((View) nagareLogo, getString(R.string.tn_nagare_logo)),
                Pair.create((View) emailLayout, getString(R.string.tn_email_field)),
                Pair.create((View) passwordLayout, getString(R.string.tn_password_field)),
                Pair.create((View) loginButton, getString(R.string.tn_btn_auth))
        };
        ViewUtil.startNewActivity(context, SignUpActivity.class, sharedElements);
    }

    public void onLoginButtonClick(final View view) {
        final String email = emailField.getText().toString(), password = passwordField.getText().toString();
        final boolean[] authenticate = new boolean[] {false};

        progressView.setVisibility(View.VISIBLE);
        loginForm.setVisibility(View.GONE);

        DataUtil.dbUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)) {
                        authenticate[0] = true;
                        DataUtil.USER_KEY = user.getKey();
                        break;
                    }
                }
                if (authenticate[0]) {
                    SharedPreferences spLogin;
                    spLogin = getSharedPreferences("login",MODE_PRIVATE);
                    spLogin.edit().putBoolean("logged",true).apply();
                    spLogin.edit().putString("userKey",DataUtil.USER_KEY).apply();
                    finish();
                    startActivity(new Intent(context, MainActivity.class));
                } else {
                    Snackbar.make(view,"Login failed", Snackbar.LENGTH_SHORT).show();
                    loginForm.setVisibility(View.VISIBLE);
                }
                progressView.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

}

