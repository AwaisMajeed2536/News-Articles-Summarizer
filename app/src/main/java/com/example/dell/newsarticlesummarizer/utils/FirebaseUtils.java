package com.example.dell.newsarticlesummarizer.utils;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.dell.newsarticlesummarizer.interfaces.Callback;
import com.example.dell.newsarticlesummarizer.models.User;
import com.example.dell.newsarticlesummarizer.ui.MainActivity;
import com.example.dell.newsarticlesummarizer.ui.SignUpActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseUtils {
    public static final String BASE_URL = "https://news-article-summarizer.firebaseio.com/";
    public static final String USER_URL = BASE_URL + "users/";

    public static void signIn(final User user, final Callback<Boolean> callback) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReferenceFromUrl(USER_URL + user.getEmail().replace(".", "_"));
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() == null) {
                    callback.call(false);
//                    Toast.makeText(LoginActivity.this, "Invalid Username or password", Toast.LENGTH_SHORT).show();
                } else {
                    User data = dataSnapshot.getValue(User.class);
                    if (data != null && user.getPassword().equals(data.getPassword())) {
                        callback.call(true);
//                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else {
                        callback.call(false);
//                        Toast.makeText(LoginActivity.this, "Invalid Username or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.call(null);
            }
        });
    }

    public static void signUp(User user, final Callback<Boolean> callback) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReferenceFromUrl(USER_URL);
        databaseReference.child(user.getEmail().replace(".", "_")).setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.call(true);
//                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.call(false);
//                Toast.makeText(SignUpActivity.this, "Sign up failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
