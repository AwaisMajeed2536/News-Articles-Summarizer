package com.example.dell.newsarticlesummarizer.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.dell.newsarticlesummarizer.BaseActivity;
import com.example.dell.newsarticlesummarizer.R;
import com.example.dell.newsarticlesummarizer.interfaces.Callback;
import com.example.dell.newsarticlesummarizer.models.User;
import com.example.dell.newsarticlesummarizer.utils.AppPreferences;
import com.example.dell.newsarticlesummarizer.utils.FirebaseUtils;
import com.example.dell.newsarticlesummarizer.utils.Utils;

public class SignUpActivity extends BaseActivity implements View.OnClickListener {

    private EditText nameEt, emailEt, passwordEt;
    private User userData;
    private ProgressDialog progressDialog;
    private AppPreferences appPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initView();
        Utils.isNetworkAvailable(this, false, true);
        appPreferences = new AppPreferences(this);
    }

    private void registerUser() {
        progressDialog.show();
        FirebaseUtils.signUp(userData, new Callback<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                progressDialog.dismiss();
                if(aBoolean == null || !aBoolean) {
                    showError("Sign up failed!");
                } else {
                    startActivity(new Intent(SignUpActivity.this, ArticlesActivity.class));
                    appPreferences.setLoggedIn(true);
                    finish();
                }
            }
        });
    }

    private boolean inputsAreOk() {
        CharSequence name = nameEt.getText(), email = emailEt.getText(), password = passwordEt.getText();
        if (Utils.isEmpty(name)) {
            nameEt.setError("Required Field");
            nameEt.requestFocus();
            return false;
        } else if (Utils.isEmpty(email)) {
            emailEt.setError("Required Field");
            emailEt.requestFocus();
            return false;
        } else if (Utils.isEmpty(password)) {
            passwordEt.setError("Required Field");
            passwordEt.requestFocus();
            return false;
        } else if (!Utils.isValidEmail(email)) {
            emailEt.setError("Invalid Email!");
            emailEt.requestFocus();
            return false;
        } else if (password.length() < 6) {
            passwordEt.setError("Password should be minimum 6 characters!");
            passwordEt.requestFocus();
            return false;
        }
        userData = new User();
        userData.setUsername(nameEt.getText().toString());
        userData.setEmail(emailEt.getText().toString());
        userData.setPassword(passwordEt.getText().toString());
        return true;
    }

    private void initView() {
        findViewById(R.id.sign_up_btn).setOnClickListener(this);
        nameEt = findViewById(R.id.name_tv);
        passwordEt = findViewById(R.id.pasword_tv);
        emailEt = findViewById(R.id.email_tv);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("please wait...");
        progressDialog.setCancelable(false);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sign_up_btn) {
            if (inputsAreOk()) {
                hideKeyboard();
                registerUser();
            }
        }
    }
}
