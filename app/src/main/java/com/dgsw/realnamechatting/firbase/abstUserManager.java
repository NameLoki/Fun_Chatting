package com.dgsw.realnamechatting.firbase;

import android.app.Activity;

import com.dgsw.realnamechatting.data.User;
import com.google.firebase.auth.FirebaseUser;

public abstract class abstUserManager {

    public abstract boolean login(String email, String password, Activity loginActivity);
    public abstract boolean singup(User user);
    public abstract void logout();
    public abstract FirebaseUser getLoginUser();
}
