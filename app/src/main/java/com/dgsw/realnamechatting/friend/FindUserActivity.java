package com.dgsw.realnamechatting.friend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dgsw.realnamechatting.R;
import com.dgsw.realnamechatting.data.User;
import com.dgsw.realnamechatting.databinding.ActivityFindUserBinding;
import com.dgsw.realnamechatting.login.LoginActivity;
import com.dgsw.realnamechatting.manager.FirebaseManager;
import com.dgsw.realnamechatting.manager.OnFindUserCallBack;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FindUserActivity extends AppCompatActivity {

    private ActivityFindUserBinding binding;
    private FirebaseManager firebaseManager;
    private List<User> findUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFindUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        binding.imageButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findUser(binding.editTextSearch.getText().toString(), findUsers, new OnFindUserCallBack() {
                    @Override
                    public void onFindUserCallBack(User user) {
                        findUserSetting(user);
                    }
                });
            }
        });

        binding.buttonAddFriend.setOnClickListener(v -> {
            addFriend(findUsers.get(0));
        });

        findUsers = new ArrayList<>();
        firebaseManager = FirebaseManager.getInstance();
    }

    private void findUser(String email, List<User> users, OnFindUserCallBack onFindUserCallBack) {
        firebaseManager.loadEmailSeachUsers(email, users, onFindUserCallBack);
    }

    /**
     * 사용안하는 함수
     * @param email
     */
    private void findUser(String email) {
        DatabaseReference ref = firebaseManager.getDBReference("users");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    firebaseDatabase.getReference("users/" + snapshot.getKey() + "/info").orderByChild("email").equalTo(email).addChildEventListener(new ChildEventListener() {
                    firebaseManager.getFirebaseDatabase().getReference("users/" + snapshot.getKey() + "/info").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            User user = dataSnapshot.getValue(User.class);
                            if(user.getEmail().equals(email)) {
                                addUser(user);
                            }
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
    }

    private void addFriend(User user) {
        FirebaseUser firebaseUser = firebaseManager.getLoginUser();
        DatabaseReference ref = firebaseManager.getDBReference("users/" + firebaseUser.getUid() + "/friends/" + user.getUid());
        ref.setValue(user.getEmail());
        finish();
    }


    private void addUser(User user) {
        findUsers.add(user);
        findUserSetting(user);
    }

    private void findUserSetting(User user) {
        binding.textViewName.setText(user.getName());
        binding.linearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        binding.linearLayout.setVisibility(View.GONE);
        findUsers.clear();
    }
}