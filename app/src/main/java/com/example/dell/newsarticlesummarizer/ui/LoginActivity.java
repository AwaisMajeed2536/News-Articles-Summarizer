package com.example.dell.newsarticlesummarizer.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.dell.newsarticlesummarizer.BaseActivity;
import com.example.dell.newsarticlesummarizer.R;
import com.example.dell.newsarticlesummarizer.interfaces.Callback;
import com.example.dell.newsarticlesummarizer.models.User;
import com.example.dell.newsarticlesummarizer.utils.AppPreferences;
import com.example.dell.newsarticlesummarizer.utils.Constants;
import com.example.dell.newsarticlesummarizer.utils.FirebaseUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton signInButton;

    private String email, password;
    private EditText emailEt, passwordEt;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void initView() {
        findViewById(R.id.login_btn).setOnClickListener(this);
        emailEt = findViewById(R.id.email_login);
        passwordEt = findViewById(R.id.pasword_login);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Validating Credentials");
        progressDialog.setMessage("please wait...");
        progressDialog.setCancelable(false);
        findViewById(R.id.sign_up_link).setOnClickListener(this);

        /*Google buttton initialization*/
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    private void updateUI(GoogleSignInAccount account) {
        if (account == null) {
            // show signInButton
            signInButton.setVisibility(View.VISIBLE);
        } else {
            // you are already signed
            signInButton.setVisibility(View.GONE);
            goToMainActivity();
        }
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, ArticlesActivity.class);
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
            progressDialog.show();
            FirebaseUtils.signIn(new User(email, password), new Callback<Boolean>() {
                @Override
                public void call(Boolean aBoolean) {
                    progressDialog.dismiss();
                    if (aBoolean == null) {
                        showError("Some went wrong!");
                    } else if (aBoolean) {
                        goToMainActivity();
                    } else {
                        showError("Invalid Username or password");
                    }
                }
            });
        }
    }

    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
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
        }
        email = emailEt.getText().toString();
        password = passwordEt.getText().toString();
        return true;
    }
}
