package com.dgsw.realnamechatting.firbase;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.dgsw.realnamechatting.data.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseManager {

    private FirebaseAuth firebaseAuth;

    private static FirebaseManager instance;

    private OnAuthResultCompleteListener onCompleteListener;

    public FirebaseManager() {
        firebaseAuth = FirebaseAuth.getInstance();
        onCompleteListener = new OnAuthResultCompleteListener();
    }

    public static FirebaseManager getInstance() {
        if(instance == null) {
            instance = new FirebaseManager();
        }
        return instance;
    }

    public boolean login(String email, String password, Activity loginActivity) {

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(loginActivity, onCompleteListener);

        return onCompleteListener.getResult();
    }

    public boolean singup(User user) {
        return false;
    }

    public void logout() {
        firebaseAuth.signOut();
    }

    public FirebaseUser getLoginUser() {
        return firebaseAuth.getCurrentUser();
    }
}
