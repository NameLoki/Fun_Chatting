package com.dgsw.realnamechatting.manager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class OnChildEventImplListener implements ChildEventListener {

    public interface OnChildEventCallBackListener {
        void onChildAdded(DataSnapshot dataSnapshot, String s);
    }

    private OnChildEventCallBackListener onChildEventCallBackListener;

    public OnChildEventImplListener(OnChildEventCallBackListener onChildEventCallBackListener) {
        this.onChildEventCallBackListener = onChildEventCallBackListener;
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        onChildEventCallBackListener.onChildAdded(dataSnapshot, s);
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
}
