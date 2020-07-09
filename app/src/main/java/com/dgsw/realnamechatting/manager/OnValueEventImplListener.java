package com.dgsw.realnamechatting.manager;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class OnValueEventImplListener implements ValueEventListener {

    public interface OnValueEventCallBackListener {
        void onDataChangeCallBack(DataSnapshot dataSnapshot);
    }

    private OnValueEventCallBackListener onValueEventCallBackListener;

    public OnValueEventImplListener(OnValueEventCallBackListener onValueEventCallBackListener) {
        this.onValueEventCallBackListener = onValueEventCallBackListener;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        onValueEventCallBackListener.onDataChangeCallBack(dataSnapshot);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
