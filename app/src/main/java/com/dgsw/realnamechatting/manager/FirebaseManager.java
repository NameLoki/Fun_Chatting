package com.dgsw.realnamechatting.manager;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.dgsw.realnamechatting.data.ChatRoom;
import com.dgsw.realnamechatting.data.User;
import com.dgsw.realnamechatting.data.OnValueChangedCallBack;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    public void getUserInfo(String friendUid, ValueEventListener valueEventListener) {
          getDBReference("users/" + friendUid + "/info").addValueEventListener(valueEventListener);
    }

    public void loadChatRooms(List<ChatRoom> rooms, OnValueChangedCallBack onValueChangedCallBack) {
        DatabaseReference myRef = getDBReference("users/" + getLoginUser().getUid() + "/rooms");

        myRef.addChildEventListener(new OnChildEventImplListener((dataSnapshot, s) -> {
            rooms.add(new ChatRoom(dataSnapshot.getKey(), dataSnapshot.getValue().toString()));
            if(onValueChangedCallBack != null) {
                onValueChangedCallBack.OnValueChangeCallBack();
            }
        }));
    }

    public void loadChatRooms(List<ChatRoom> rooms) {
        this.loadChatRooms(rooms, null);
    }

    public void loadFriend(List<User> friends) {
        this.loadFriend(friends, null);
    }

    public void loadFriend(List<User> friends, OnValueChangedCallBack onValueChangedCallBack) {
        DatabaseReference friendRef = getDBReference("users/" + getLoginUser().getUid() + "/friends");

        friendRef.addChildEventListener(new OnChildEventImplListener(((dataSnapshot, s) -> {
            getDBReference("users/" + dataSnapshot.getKey() + "/info").addValueEventListener(
                new OnValueEventImplListener((d) -> {
                    friends.add(d.getValue(User.class));
                    if(onValueChangedCallBack != null) {
                        onValueChangedCallBack.OnValueChangeCallBack();
                    }
            }));
        })));
    }

    public void loadEmailSeachUsers(String email, List<User> users, OnFindUserCallBack onFindUserCallBack) {

        DatabaseReference ref = getDBReference("users");

        ref.addValueEventListener(new OnValueEventImplListener(dataSnapshot -> {
            for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                firebaseDatabase.getReference("users/" + snapshot.getKey() + "/info").addValueEventListener(new OnValueEventImplListener(dSnapshot -> {
                    User user = dSnapshot.getValue(User.class);
                    if(user.getEmail().equals(email)) {
                        users.add(user);
                        onFindUserCallBack.onFindUserCallBack(user);
                    }
                }));
            }
        }));
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
