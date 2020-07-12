package com.dgsw.realnamechatting.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dgsw.realnamechatting.R;
import com.dgsw.realnamechatting.data.User;
import com.dgsw.realnamechatting.databinding.ActivityChatCreateRoomBinding;

import java.util.ArrayList;
import java.util.List;

public class ChatCreateRoomActivity extends AppCompatActivity {

    private ActivityChatCreateRoomBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatCreateRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void loadFriendList() {
        ArrayList<User> friends = getIntent().getParcelableArrayListExtra("friends");
    }
}