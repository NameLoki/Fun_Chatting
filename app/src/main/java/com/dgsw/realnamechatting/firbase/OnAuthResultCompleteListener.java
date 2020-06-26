package com.dgsw.realnamechatting.firbase;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class OnAuthResultCompleteListener implements OnCompleteListener<AuthResult> {

    private boolean result;

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        result = task.isSuccessful();
    }

    public boolean getResult() {
        return result;
    }
}
