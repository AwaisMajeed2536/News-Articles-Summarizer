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
import com.example.dell.newsarticlesummarizer.utils.FirebaseUtils;

public class SignUpActivity extends BaseActivity implements View.OnClickListener {

    private EditText nameEt, emailEt, passwordEt;
    private User userData;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initView();

    }

    private void registerUser() {
        progressDialog.show();
        FirebaseUtils.signUp(userData, new Callback<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                progressDialog.dismiss();
                if(aBoolean == null || !aBoolean){
                    showError("Sign up failed!");
                } else {
                    startActivity(new Intent(SignUpActivity.this, ArticlesActivity.class));
                }
            }
        });
    }

    private boolean inputsAreOk() {
        if (TextUtils.isEmpty(nameEt.getText())) {
            nameEt.setError("Required Field");
            nameEt.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(emailEt.getText())) {
            emailEt.setError("Required Field");
            emailEt.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(passwordEt.getText())) {
            passwordEt.setError("Required Field");
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
        findViewById(R.id.cardview).setOnClickListener(this);
        findViewById(R.id.signup).setOnClickListener(this);
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
        if (v.getId() == R.id.cardview) {
            if (inputsAreOk())
                registerUser();
        }
    }
}
