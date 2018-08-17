package com.example.dell.newsarticlesummarizer.utils;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.example.dell.newsarticlesummarizer.interfaces.Callback;
import com.example.dell.newsarticlesummarizer.models.Article;
import com.example.dell.newsarticlesummarizer.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseUtils {
    public static final String BASE_URL = "https://news-article-summarizer.firebaseio.com/";
    public static final String USER_URL = BASE_URL + "users/";
    public static final String DATABASE_URL = BASE_URL + "database/";

    public static void signIn(final User user, final SharedPreferences appPreferences, final Callback<Boolean> callback) {
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
                        AppPreferences.setUserName(user.getUsername(), appPreferences);
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

    public static void changePassword(final String oldPassword, final String newPassword, String email, final Callback<Boolean> callback) {
        email = email.replace(".", "_");
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReferenceFromUrl(USER_URL + email + "/");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user.getPassword().equals(oldPassword)) {
                    databaseReference.child("password").setValue(newPassword)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    callback.call(true);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            callback.call(null);
                        }
                    });
                } else {
                    callback.call(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void changeName(final String name, String email, final Callback<Boolean> callback) {
        email = email.replace(".", "_");
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReferenceFromUrl(USER_URL + email + "/");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                databaseReference.child("username").setValue(name)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                callback.call(true);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.call(null);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void getArticlesList(final Callback<List<Article>> callback) {
        DatabaseReference databaseReference  = FirebaseDatabase.getInstance()
                .getReferenceFromUrl(DATABASE_URL);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Article> articleList = new ArrayList<>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Article post = postSnapshot.getValue(Article.class);
                    articleList.add(post);
                }
                callback.call(articleList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.call(null);
            }
        });
    }
}
