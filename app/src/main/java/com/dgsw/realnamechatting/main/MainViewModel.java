package com.dgsw.realnamechatting.main;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dgsw.realnamechatting.data.User;
import com.dgsw.realnamechatting.firbase.FirebaseManager;
import com.dgsw.realnamechatting.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    private FirebaseManager firebaseManager;

    private FirebaseUser firebaseUser;
    private static MutableLiveData<User> user;

    public MainViewModel() {
        mText = new MutableLiveData<>();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseManager = FirebaseManager.getInstance();

        user = new MutableLiveData<>();

        if(firebaseUser != null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("users/" + firebaseUser.getUid() + "/info");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    user.setValue(dataSnapshot.getValue(User.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else {
            mText.setValue("인증안된 사용자");
        }
    }


    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    public void setFirebaseUser(FirebaseUser firebaseUser) {
        this.firebaseUser = firebaseUser;
    }

    public static MutableLiveData<User> getUser() {
        return user;
    }

    public void setUser(MutableLiveData<User> user) {
        this.user = user;
    }

    public FirebaseManager getFirebaseManager() {
        return firebaseManager;
    }
}