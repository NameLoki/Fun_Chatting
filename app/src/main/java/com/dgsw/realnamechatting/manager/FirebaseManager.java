package com.dgsw.realnamechatting.manager;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dgsw.realnamechatting.data.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseManager {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    private static FirebaseManager instance;

    private OnAuthResultCompleteListener onCompleteListener;

    public FirebaseManager() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        onCompleteListener = new OnAuthResultCompleteListener();
    }

    public static FirebaseManager getInstance() {
        if(instance == null) {
            instance = new FirebaseManager();
        }
        return instance;
    }

    public DatabaseReference getDBReference(String path) {
        return firebaseDatabase.getReference(path);
    }

    public boolean login(String email, String password, Activity loginActivity) {

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(loginActivity, onCompleteListener);

        return onCompleteListener.getResult();
    }

    public User getEmailSeachUsers(String email) {

        List<User> users = new ArrayList<>();
        User user;

        DatabaseReference ref = getDBReference("users");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    firebaseDatabase.getReference("users/" + snapshot.getKey() + "/info").orderByChild("email").equalTo(email).addChildEventListener(new ChildEventListener() {
                    firebaseDatabase.getReference("users/" + snapshot.getKey() + "/info").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d("이자시가", dataSnapshot.toString());
                            User user = dataSnapshot.getValue(User.class);
                            if(user.getEmail().equals(email)) {
                                users.add(user);
                            }
//                            User user = new User();
//                            user.setEmail(s);
//                            users.add(user);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return users.get(0);
    }

    public FirebaseDatabase getFirebaseDatabase() {
        return firebaseDatabase;
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
