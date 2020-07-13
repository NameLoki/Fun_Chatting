package com.dgsw.realnamechatting.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.dgsw.realnamechatting.R;
import com.dgsw.realnamechatting.data.CreateUser;
import com.dgsw.realnamechatting.data.User;
import com.dgsw.realnamechatting.databinding.ActivityChatCreateRoomBinding;

import java.util.ArrayList;
import java.util.List;

public class ChatCreateRoomActivity extends AppCompatActivity {

    private ActivityChatCreateRoomBinding binding;
    private List<CreateUser> friends;

    private ChatCreateAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatCreateRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        loadFriendList();

        adapter = new ChatCreateAdapter(friends, new ChatCreateAdapter.OnClickChatCreateListener() {
            @Override
            public void onItemClick(User user) {

            }

            @Override
            public void onItemLongClick(User user) {

            }
        });

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadFriendList() {
        friends = getIntent().getParcelableArrayListExtra("friends");
    }
}