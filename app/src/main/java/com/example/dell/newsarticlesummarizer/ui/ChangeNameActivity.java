package com.example.dell.newsarticlesummarizer.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dell.newsarticlesummarizer.BaseActivity;
import com.example.dell.newsarticlesummarizer.R;
import com.example.dell.newsarticlesummarizer.interfaces.Callback;
import com.example.dell.newsarticlesummarizer.utils.AppPreferences;
import com.example.dell.newsarticlesummarizer.utils.FirebaseUtils;
import com.example.dell.newsarticlesummarizer.utils.Utils;

public class ChangeNameActivity extends BaseActivity {

    Button changeNameBtn;
    EditText etFirstName;
    EditText etLastName;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change Name");
        changeNameBtn = findViewById(R.id.changeNameBtn);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Changing Name");
        progressDialog.setMessage("please wait...");
        progressDialog.setCancelable(false);
        changeNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                if (Utils.isNotEmpty(etFirstName.getText()) && Utils.isNotEmpty(etLastName.getText())) {
                    changeName();
                } else {
                    showError("Name fields are required!");
                }

            }
        });
    }

    private void changeName() {
        progressDialog.show();
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        AppPreferences.setUserName(firstName + " " + lastName, AppPreferences.getInstance(this));
        FirebaseUtils.changeName(firstName + " " + lastName, AppPreferences.getUserEmail(this), new Callback<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                progressDialog.dismiss();
                if (aBoolean == null) {
                    showError("Error connecting! Please try again");
                } else if (aBoolean) {
                    Toast.makeText(ChangeNameActivity.this, "Name changed successful", Toast.LENGTH_SHORT).show();
                    finish();
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
