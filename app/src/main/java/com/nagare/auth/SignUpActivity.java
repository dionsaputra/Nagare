package com.nagare.auth;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nagare.R;
import com.nagare.model.User;
import com.nagare.util.ViewUtil;

import java.util.HashMap;
import java.util.Map;

// Firebase

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

//    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mAuth = FirebaseAuth.getInstance();

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

    // Define components
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

    // Define action to do when login text clicked.
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

        final User user = new User(fullname, email, password);
        final DatabaseReference dbUsers = FirebaseDatabase.getInstance().getReference("/users");
        final Map<String, Object> mUser = new HashMap<>();

//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(SignUpActivity.this, "Success",
//                                    Toast.LENGTH_SHORT).show();
//                            String UID = mAuth.getCurrentUser().getUid();
//                            mUser.put(UID, user);
//                            dbUsers.updateChildren(mUser);
//                            ViewUtil.startNewActivity(SignUpActivity.this, LoginActivity.class);
//                        } else {
//                            Toast.makeText(SignUpActivity.this, "Failed",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
    }
}
