package com.dgsw.realnamechatting.main;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dgsw.realnamechatting.data.ChatRoom;
import com.dgsw.realnamechatting.data.User;
import com.dgsw.realnamechatting.manager.ActivityLoadManager;
import com.dgsw.realnamechatting.manager.FirebaseManager;
import com.dgsw.realnamechatting.login.LoginActivity;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {

    private FirebaseManager firebaseManager;
    private FirebaseUser firebaseUser;

    private MutableLiveData<User> user;
    private List<User> friends;
    private MutableLiveData<String> friendCount;

    private List<ChatRoom> rooms;

    private ActivityLoadManager activityLoadManager;

    public MainViewModel() {
        firebaseManager = FirebaseManager.getInstance();
        activityLoadManager = ActivityLoadManager.getInstance();

        firebaseUser = firebaseManager.getLoginUser();

        user = new MutableLiveData<>();
        friends = new ArrayList<>();
        rooms = new ArrayList<>();
        friendCount = new MutableLiveData<>();

        if(firebaseUser != null) {
            DatabaseReference myRef = firebaseManager.getDBReference("users/" + firebaseUser.getUid() + "/info");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    user.setValue(dataSnapshot.getValue(User.class));
                    loadFriend();
                    loadRooms();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void loadRooms() {
        DatabaseReference myRef = firebaseManager.getDBReference("users/" + firebaseUser.getUid() + "/rooms");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                rooms.add(new ChatRoom(dataSnapshot.getKey()));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addFriend() {

    }

    public List<User> getFriends() {
        return friends;
    }

    private void loadFriend() {
        firebaseManager.getDBReference("users/" + user.getValue().getUid() + "/friends").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                firebaseManager.getDBReference("users/" + dataSnapshot.getKey() + "/info").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        friends.add(dataSnapshot.getValue(User.class));
                        friendCount.setValue(friends.size() + " 명");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public MutableLiveData<String> getFriendCount() {
        return friendCount;
    }

    public List<ChatRoom> getRooms() {
        return rooms;
    }

    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    public void setFirebaseUser(FirebaseUser firebaseUser) {
        this.firebaseUser = firebaseUser;
    }

    public MutableLiveData<User> getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user.setValue(user);
    }

    public FirebaseManager getFirebaseManager() {
        return firebaseManager;
    }

    public ActivityLoadManager getActivityLoadManager() {
        return activityLoadManager;
    }
}