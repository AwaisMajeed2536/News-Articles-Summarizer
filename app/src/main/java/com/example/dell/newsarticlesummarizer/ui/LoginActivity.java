package com.example.dell.newsarticlesummarizer.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.dell.newsarticlesummarizer.BaseActivity;
import com.example.dell.newsarticlesummarizer.R;
import com.example.dell.newsarticlesummarizer.interfaces.Callback;
import com.example.dell.newsarticlesummarizer.models.User;
import com.example.dell.newsarticlesummarizer.services.GoogleApi;
import com.example.dell.newsarticlesummarizer.utils.AppPreferences;
import com.example.dell.newsarticlesummarizer.utils.Constants;
import com.example.dell.newsarticlesummarizer.utils.FirebaseUtils;
import com.example.dell.newsarticlesummarizer.utils.Utils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends BaseActivity implements View.OnClickListener {


    private SignInButton signInButton;

    private String email, password;
    private EditText emailEt, passwordEt;
    private ProgressDialog progressDialog;
    private SharedPreferences appPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        Utils.isNetworkAvailable(this, false, true);
        appPreferences = AppPreferences.getInstance(this);
        GoogleApi.initGoogle(this);
    }

    private void initView() {
        findViewById(R.id.login_btn).setOnClickListener(this);
        emailEt = findViewById(R.id.email_login);
        passwordEt = findViewById(R.id.pasword_login);
        findViewById(R.id.sign_up_link).setOnClickListener(this);

        /*Google buttton initialization*/
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(AppPreferences.isLoggedIn(appPreferences)) {
            goToMainActivity();
            finish();
        } else {
            GoogleSignInAccount account = GoogleApi.getAccount(this);
            updateUI(account);
        }

    }

    private void updateUI(GoogleSignInAccount account) {
        if (account == null) {
            // show signInButton
            signInButton.setVisibility(View.VISIBLE);
        } else {
            // you are already signed
            signInButton.setVisibility(View.GONE);
            AppPreferences.setLoggedIn(account.getEmail(), appPreferences);
            AppPreferences.setUserName(account.getDisplayName(), appPreferences);
            goToMainActivity();
//            finish();
        }
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signInWithGoogle();
                break;
            case R.id.login_btn:
                login();
                break;
            case R.id.sign_up_link:
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                break;
        }
    }

    private void login() {
        if (inputsAreOk()) {
            hideKeyboard();
            progressDialog = showProgressDialog("Validating Credentials", "Please wait...") ;
            FirebaseUtils.signIn(new User(email, password), appPreferences, new Callback<Boolean>() {
                @Override
                public void call(Boolean aBoolean) {
                    progressDialog.dismiss();
                    if (aBoolean == null) {
                        showError("Some went wrong! Please Try Again");
                    } else if (aBoolean) {
                        AppPreferences.setLoggedIn(email, appPreferences);
                        goToMainActivity();
                    } else {
                        showError("Invalid Username or Password!");
                    }
                }
            });
        }
    }

    private void signInWithGoogle() {
        Intent signInIntent = GoogleApi.getSignInClient().getSignInIntent();
        startActivityForResult(signInIntent, Constants.RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            updateUI(account);
        } catch (ApiException e) {
            Log.w("Google Error", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private boolean inputsAreOk() {
        if (TextUtils.isEmpty(emailEt.getText())) {
            emailEt.setError("Required Field");
            emailEt.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(passwordEt.getText())) {
            passwordEt.setError("Required Field");
            passwordEt.requestFocus();
            return false;
        } else if (!Utils.isValidEmail(emailEt.getText())) {
            emailEt.setError("Email is invalid!");
            emailEt.requestFocus();
            return false;
        } else if (passwordEt.getText().length() < 6) {
            passwordEt.setError("Password should be minimum 6 characters");
            passwordEt.requestFocus();
            return false;
        }
        email = emailEt.getText().toString();
        password = passwordEt.getText().toString();
        return true;
    }
}
