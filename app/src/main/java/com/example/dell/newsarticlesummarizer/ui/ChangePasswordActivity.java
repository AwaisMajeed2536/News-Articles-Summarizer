package com.example.dell.newsarticlesummarizer.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dell.newsarticlesummarizer.BaseActivity;
import com.example.dell.newsarticlesummarizer.R;
import com.example.dell.newsarticlesummarizer.interfaces.Callback;
import com.example.dell.newsarticlesummarizer.utils.AppPreferences;
import com.example.dell.newsarticlesummarizer.utils.FirebaseUtils;
import com.example.dell.newsarticlesummarizer.utils.Utils;

public class ChangePasswordActivity extends BaseActivity {

    Button changePassBtn;
    EditText etOldPassword;
    EditText etNewPassword;
    EditText etConfirmPassword;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change Password");
        changePassBtn = findViewById(R.id.changePassBtn);
        etOldPassword = findViewById(R.id.etOldPassword);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Changing Password");
        progressDialog.setMessage("please wait...");
        progressDialog.setCancelable(false);
        changePassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                if(Utils.isValidPassword(etOldPassword.getText()) && Utils.isValidPassword(etNewPassword.getText()) && Utils.isValidPassword(etConfirmPassword.getText())){
                    changePassword();
                } else {
                    showError("Password should not be not null and minimum 8 characters");
                }

            }
        });
    }

    private void changePassword(){
        String oldPassword = etOldPassword.getText().toString();
        String newPassword = etNewPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();
        if(!newPassword.equals(confirmPassword)){
            etNewPassword.setError("Password doesn't match");
            showError("New Password doesn't match");
            return;
        }
        progressDialog.show();
        FirebaseUtils.changePassword(oldPassword, newPassword, AppPreferences.getUserEmail(this), new Callback<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                progressDialog.dismiss();
                if(aBoolean == null) {
                    showError("Error connecting! Please try again");
                } else if(aBoolean) {
                    Toast.makeText(ChangePasswordActivity.this, "Password changed successful", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    etConfirmPassword.setError("Wrong password");
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
