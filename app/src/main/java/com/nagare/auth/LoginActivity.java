package com.nagare.auth;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nagare.MainActivity;
import com.nagare.R;
import com.nagare.fragment.Firebase;
import com.nagare.model.User;
import com.nagare.util.DataUtil;
import com.nagare.util.ViewUtil;

import com.google.firebase.auth.FirebaseAuth;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private final Context context = LoginActivity.this;

    private ImageView nagareLogo;
    private LinearLayout emailLayout;
    private LinearLayout passwordLayout;
    private LinearLayout loginForm;
    private LinearLayout progressView;
    private TextView loginTextView;
    private TextView signUpTextView;
    private Button loginButton;
    private EditText emailField;
    private EditText passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.auth_login);
        initComponent();
        setSignUpAction();

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Success",
                                            Toast.LENGTH_SHORT).show();
                                    doSuccessLogin();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Failed",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    // Define all UI component
    private void initComponent() {
        nagareLogo      = findViewById(R.id.iv_nagare_logo);
        emailLayout     = findViewById(R.id.ll_email_field);
        passwordLayout  = findViewById(R.id.ll_password_field);
        loginButton     = findViewById(R.id.btn_login);
        emailField      = findViewById(R.id.et_email);
        passwordField   = findViewById(R.id.et_password);
        loginForm       = findViewById(R.id.ll_form_login);
        progressView    = findViewById(R.id.ll_progress_view);
        signUpTextView  = findViewById(R.id.tv_sign_up);

        ViewUtil.loadImage(context, nagareLogo, R.drawable.nagare_logo);
    }

    // Define action to do when sign up text view click
    private void setSignUpAction() {
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair[] sharedElements = new Pair[]{
                        Pair.create((View) nagareLogo, getString(R.string.tn_nagare_logo)),
                        Pair.create((View) emailLayout, getString(R.string.tn_email_field)),
                        Pair.create((View) passwordLayout, getString(R.string.tn_password_field)),
                        Pair.create((View) loginButton, getString(R.string.tn_btn_auth))
                };
                ViewUtil.startNewActivity(context, SignUpActivity.class, sharedElements);
            }
        });
    }

    /*** Action when login success ***/
    private void doSuccessLogin() {
        ViewUtil.startNewActivity(context, MainActivity.class, nagareLogo, R.string.tn_nagare_logo);
    }

}

