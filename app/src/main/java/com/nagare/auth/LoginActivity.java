package com.nagare.auth;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nagare.MainActivity;
import com.nagare.R;
import com.nagare.model.User;
import com.nagare.util.DataUtil;
import com.nagare.util.ViewUtil;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private final Context context = this;

    /*** UI Component ***/
    private ImageView       nagareLogo;
    private LinearLayout    emailLayout, passwordLayout, loginForm, progressView;
    private TextView        loginTextView, signUpTextView;
    private Button          loginButton;
    private EditText emailField, passwordField;

    /*** Asyncronous task to login ***/
    private UserLoginTask loginTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_login);

        DataUtil.getInstance().initUserStub();

        initComponent();
        setSignUpAction();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attempLogin();
            }
        });
    }

    /*** Define all UI component ***/
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

    /*** Define action to do when sign up text view click ***/
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

    /*** Login function ***/
    private void attempLogin() {
        if (loginTask != null) return;

        String email = emailField.getText().toString().trim().toLowerCase();
        String password = passwordField.getText().toString();

        boolean validLoginInput = isValidLoginInput(email, password);

        if (validLoginInput) {
            showProgress(true);
            loginTask = new UserLoginTask(email, password);
            loginTask.execute();
        }
    }

    /*** True if email and password field valid  ***/
    private boolean isValidLoginInput(String email, String password) {
        boolean valid = true;
        if (!isValidEmail(email)) {
            emailField.setHint("email not valid");
            valid = false;
        }
        if (!isValidPassword((password))) {
            passwordField.setHint("password not valid");
            valid = false;
        }

        return valid;
    }

    /*** True if email is valid ***/
    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email);
    }

    /*** True if password is valid ***/
    private boolean isValidPassword(String password) {
        return !TextUtils.isEmpty(password) && password.length() > 0;
    }

    /*** True if username and password exist in database ***/
    private boolean isSuccessLogin(String email, String password) {
        return true;
//        return DataUtil.getInstance().isExistUser(new User(email,password));
    }

    /*** Action when login success ***/
    private void doSuccessLogin() {
        Toast.makeText(context, "Login success", Toast.LENGTH_SHORT).show();
        ViewUtil.startNewActivity(context, MainActivity.class, nagareLogo, R.string.tn_nagare_logo);
    }

    /*** Action when login failed ***/
    private void doFailedLogin() {
        Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show();
    }

    /*** Show loading progress ***/
    private void showProgress(boolean show) {
        loginForm.setVisibility(show ? View.GONE : View.VISIBLE);
        progressView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    /*** Represents an asynchronous login/registration task used to authenticate the user ***/
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String email;
        private final String password;

        public UserLoginTask(String email, String password) {
            this.email = email;
            this.password = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

//            try {
//                // Simulate network access.
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                return false;
//            }

            return isSuccessLogin(email, password);
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            loginTask = null;
            showProgress(false);

            if (success) {
                doSuccessLogin();
            } else {
                doFailedLogin();
            }
        }

        @Override
        protected void onCancelled() {
            loginTask = null;
            showProgress(false);
        }
    }
}

