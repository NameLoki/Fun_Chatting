package com.dgsw.realnamechatting.chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dgsw.realnamechatting.data.Chatting;
import com.dgsw.realnamechatting.data.User;
import com.dgsw.realnamechatting.databinding.ActivityChatBinding;
import com.dgsw.realnamechatting.login.LoginActivity;
import com.dgsw.realnamechatting.main.MainViewModel;
import com.dgsw.realnamechatting.manager.FirebaseManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;
    private LinearLayoutManager lm;

    private List<Chatting> chattings;

    private String roomId;
    private String roomName;

    private User user;
    private FirebaseManager firebaseManager;

    private ChatAdapter adapter;
    private DatabaseReference roomRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        init();
        downScroll();
    }

    private void init() {

        this.roomId = getIntent().getStringExtra("id");
        this.roomName = getIntent().getStringExtra("name");

        ActionBar ab = getSupportActionBar();
        ab.setTitle(roomName);

        chattings = new ArrayList<>();

        adapter = new ChatAdapter(chattings, null);

        binding.chatRecyclerView.setAdapter(adapter);
        lm = new LinearLayoutManager(this);
        binding.chatRecyclerView.setLayoutManager(lm);

        firebaseManager = FirebaseManager.getInstance();

        roomRef = firebaseManager.getDBReference("rooms/" + roomId + "/chattings");

        binding.chatButtonSend.setOnClickListener(v -> {
            String msg = binding.chatEditTextMessage.getText().toString();

            if(!msg.isEmpty()) {
                addChat(msg);
            }
        });

        binding.chatRecyclerView.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            if(bottom < oldBottom) {
                lm.smoothScrollToPosition(binding.chatRecyclerView, null, adapter.getItemCount());
            }
        });

        firebaseManager.getUserInfo(firebaseManager.getLoginUser().getUid() , new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        roomRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Chatting chatting = dataSnapshot.getValue(Chatting.class);
                adapter.addChatting(chatting);
                downScroll();
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
        });
    }

    private void downScroll() {
        binding.chatRecyclerView.scrollToPosition(chattings.size() - 1);
    }

    private void addChat(String msg) {
        Chatting chatting = new Chatting();
        chatting.setMessage(msg);
        chatting.setUid(user.getUid());
//                roomRef.child(LocalDateTime.now().toString()).setValue(chatting);
        roomRef.push().setValue(chatting);
        binding.chatEditTextMessage.setText("");
    }
}


//         Write a message to the database
//
//        ChatData chatData = new ChatData();
//
//        chatData.setMessage("ddddddddddaooo");
//        chatData.setName("이름2");
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("chatting/" + chatData.getName());
//
//
//
//        myRef.push().setValue(chatData);
//
//        myRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                ChatData chat = dataSnapshot.getValue(ChatData.class);
////                Log.i("test", chat.getMessage());
//                binding.textViewName.setText(chat.getName() + chat.getMessage());
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });