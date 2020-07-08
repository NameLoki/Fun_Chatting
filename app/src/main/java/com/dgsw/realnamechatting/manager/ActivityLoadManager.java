package com.dgsw.realnamechatting.manager;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.dgsw.realnamechatting.login.LoginActivity;
import com.dgsw.realnamechatting.main.MainActivity;
import com.dgsw.realnamechatting.singup.SingupActivity;

public class ActivityLoadManager {

    private static ActivityLoadManager instance;
    private FirebaseManager firebaseManager;

    public ActivityLoadManager() {
        firebaseManager = FirebaseManager.getInstance();
    }

    public static ActivityLoadManager getInstance() {
        if(instance == null) {
            instance = new ActivityLoadManager();
        }

        return instance;
    }

    public void logout(Activity activity) {
        firebaseManager.logout();
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    public void loadAddFrindActivity(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    public void loadMainActivity(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        Toast.makeText(activity, "로그인 성공", Toast.LENGTH_SHORT).show();
        activity.finish();
    }

    public void loadSingup(Activity activity) {
        Intent intent = new Intent(activity, SingupActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}
