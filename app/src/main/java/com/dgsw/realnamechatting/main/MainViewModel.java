package com.dgsw.realnamechatting.main;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dgsw.realnamechatting.data.CallBackList;
import com.dgsw.realnamechatting.data.ChatRoom;
import com.dgsw.realnamechatting.data.OnValueChangedCallBack;
import com.dgsw.realnamechatting.data.User;
import com.dgsw.realnamechatting.manager.ActivityLoadManager;
import com.dgsw.realnamechatting.manager.FirebaseManager;
import com.dgsw.realnamechatting.manager.OnValueEventImplListener;
import com.google.firebase.auth.FirebaseUser;

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

    private OnValueChangedCallBack onValueChangedCallBack;

    public MainViewModel() {
        firebaseManager = FirebaseManager.getInstance();
        activityLoadManager = ActivityLoadManager.getInstance();

        firebaseUser = firebaseManager.getLoginUser();

        user = new MutableLiveData<>();
        friends = new CallBackList<>(getOnValueChangedCallBack());
        rooms = new CallBackList<>(getOnValueChangedCallBack());
        friendCount = new MutableLiveData<>();

        if(firebaseUser != null) {
            firebaseManager.getUserInfo(firebaseUser.getUid(), new OnValueEventImplListener((dataSnapshot) -> {
                user.setValue(dataSnapshot.getValue(User.class));
                firebaseManager.loadFriend(friends);
                firebaseManager.loadChatRooms(rooms);
            }));
        }
    }

    public List<User> getFriends() {
        return friends;
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

    public OnValueChangedCallBack getOnValueChangedCallBack() {
        if(onValueChangedCallBack == null) {
            Log.d("콜백 에러", "인터페이스에 기능이 구현되어있지 않습니다.");
        }
        return onValueChangedCallBack;
    }

    public void setOnValueChangedCallBack(OnValueChangedCallBack onValueChangedCallBack) {
        this.onValueChangedCallBack = onValueChangedCallBack;
    }
}