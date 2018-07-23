package com.example.dell.newsarticlesummarizer.services;

import android.content.Context;
import android.content.Intent;

import com.example.dell.newsarticlesummarizer.utils.Constants;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class GoogleApi {
    private static GoogleSignInClient mGoogleSignInClient;

    public static void initGoogle(Context context) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
    }

    public static GoogleSignInAccount getAccount(Context context){
        return GoogleSignIn.getLastSignedInAccount(context);
    }

    public static GoogleSignInClient getSignInClient() {
        return mGoogleSignInClient;
    }
}
