package com.nagare.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.nagare.MainActivity;
import com.nagare.R;
import com.nagare.model.User;
import com.nagare.util.DataUtil;
import com.nagare.util.ViewUtil;

public class SignUpActivity extends AppCompatActivity {

    private Context context = SignUpActivity.this;
    private ImageView nagareLogo;
    private LinearLayout emailLayout, passwordLayout;
    private TextView loginTextView;
    private Button signUpButton;
    private EditText nameEt, emailEt, passwordEt, confirmEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_signup);
        initComponent();

        setLoginAction();
    }

    private void initComponent() {
        nameEt = findViewById(R.id.et_full_name);
        emailEt = findViewById(R.id.et_email);
        passwordEt = findViewById(R.id.et_password);
        confirmEt = findViewById(R.id.et_password_confirm);
        nagareLogo = findViewById(R.id.iv_nagare_logo);
        emailLayout = findViewById(R.id.ll_email_field);
        passwordLayout = findViewById(R.id.ll_password_field);
        loginTextView = findViewById(R.id.tv_login);
        signUpButton = findViewById(R.id.btn_sign_up);
        ViewUtil.loadImage(context, nagareLogo, R.drawable.nagare_logo);
    }

    private void setLoginAction() {
        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void onSignUpButtonClick(View view) {
        String name = nameEt.getText().toString();
        final String email = emailEt.getText().toString().trim();
        String password = passwordEt.getText().toString();
        String confirm = confirmEt.getText().toString();

        if (password.equals(confirm)) {
            final boolean[] emailUnique = new boolean[] {true};
            DataUtil.dbUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        User user = ds.getValue(User.class);
                        if (user.getEmail().equalsIgnoreCase(email)) {
                            emailUnique[0] = false;
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) { }
            });

            if (emailUnique[0]) {
                String key = DataUtil.dbUser.push().getKey();
                User user = new User(name,email,password);
                user.setKey(key);
                DataUtil.dbUser.child(key).setValue(user);
                DataUtil.USER_KEY = key;
                DataUtil.USER_LURAH = false;
                DataUtil.USER_NAMA = name;
                DataUtil.USER_PASSWORD = password;
                DataUtil.USER_EMAIL = email;

                SharedPreferences spLogin;
                spLogin = getSharedPreferences("login",MODE_PRIVATE);
                spLogin.edit().putBoolean("logged",true).apply();
                spLogin.edit().putString("userKey",DataUtil.USER_KEY ).apply();
                spLogin.edit().putString("userNama",DataUtil.USER_NAMA ).apply();
                spLogin.edit().putString("userPassword",DataUtil.USER_PASSWORD ).apply();
                spLogin.edit().putString("userEmail",DataUtil.USER_EMAIL ).apply();
                spLogin.edit().putBoolean("userLurah",DataUtil.USER_LURAH ).apply();

                startActivity(new Intent(context, MainActivity.class));

            } else {
                Snackbar.make(view, "Email has been used", Snackbar.LENGTH_SHORT);
            }
        } else {
            Snackbar.make(view, "Password not match", Snackbar.LENGTH_SHORT);
        }
    }
}
