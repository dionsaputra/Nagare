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

    /**
     * Define all component, load image if exist.
     */
    private void initComponent() {
        nagareLogo      = findViewById(R.id.iv_nagare_logo);
        emailLayout     = findViewById(R.id.ll_email_field);
        passwordLayout  = findViewById(R.id.ll_password_field);
        loginButton     = findViewById(R.id.btn_login);
        emailField       = findViewById(R.id.et_email);
        passwordField   = findViewById(R.id.et_password);
        loginForm       = findViewById(R.id.ll_form_login);
        progressView    = findViewById(R.id.ll_progress_view);
        signUpTextView  = findViewById(R.id.tv_sign_up);

        ViewUtil.loadImage(context, nagareLogo, R.drawable.nagare_logo);
    }

    /**
     * Define action to do when sign up text clicked.
     */
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

    private void attempLogin() {
        if (loginTask != null) return;

        String email = emailField.getText().toString().trim().toLowerCase();
        String password = passwordField.getText().toString();

        showProgress(true);
        loginTask = new UserLoginTask(email, password);
        loginTask.execute();
    }

    private boolean isValidLoginInput(String username, String password) {
        View focusView = null;
        boolean valid = true;

        if (TextUtils.isEmpty(username)) {
            emailField.setError("This field is required");
            focusView = emailField;
            valid = false;
        }

        if (TextUtils.isEmpty((password))) {
            passwordField.setError("This field is required");
            focusView = emailField;
            valid = false;
        }

        if (!valid) focusView.requestFocus();

        return valid;
    }

    private boolean isSuccessLogin(String email, String password) {
        return DataUtil.getInstance().isExistUser(new User(email,password));
    }

    private void doSuccessLogin() {
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
    }

    private void doFailedLogin() {

    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            loginForm.setVisibility(show ? View.GONE : View.VISIBLE);
            loginForm.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loginForm.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            loginForm.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate the user.
     */
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

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            return DataUtil.getInstance().isExistUser(new User(email,password));
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            loginTask = null;
            showProgress(false);

            if (success) {
                doSuccessLogin();
            } else {
//                mPasswordView.setError(getString(R.string.error_incorrect_password));
//                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            loginTask = null;
            showProgress(false);
        }
    }
}

