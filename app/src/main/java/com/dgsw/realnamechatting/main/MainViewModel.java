package com.dgsw.realnamechatting.main;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dgsw.realnamechatting.data.User;
import com.dgsw.realnamechatting.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainViewModel extends ViewModel {

    private MutableLiveData<String> mText; 
    private FirebaseUser firebaseUser;
    private MutableLiveData<User> user;

    public MainViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser != null) {
            mText.setValue("인증된 사용자");
        } else {
            mText.setValue("인증안된 사용자");
        }
    }

    public LiveData<String> getText() {
        return mText;
    }
}