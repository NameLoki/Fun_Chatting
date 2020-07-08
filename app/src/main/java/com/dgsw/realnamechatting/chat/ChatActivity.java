package com.dgsw.realnamechatting.chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dgsw.realnamechatting.data.Chatting;
import com.dgsw.realnamechatting.data.User;
import com.dgsw.realnamechatting.databinding.ActivityChatBinding;
import com.dgsw.realnamechatting.login.LoginActivity;
import com.dgsw.realnamechatting.main.MainViewModel;
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

    private List<Chatting> chattings;

    private String roomId;

    private User user;
    private FirebaseUser firebaseUser;

    private ChatAdapter adapter;
    private DatabaseReference roomRef;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        init();
    }

    private void init() {

//        user = MainViewModel.getUser().getValue();

        this.roomId = getIntent().getStringExtra("id");
        chattings = new ArrayList<>();

        adapter = new ChatAdapter(chattings, null);

        binding.chatRecyclerView.setAdapter(adapter);
        binding.chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        database = FirebaseDatabase.getInstance();
        DatabaseReference myInfo = database.getReference("users/" + firebaseUser.getUid() + "/info");

        roomRef = database.getReference("rooms/" + roomId + "/chattings");

        binding.chatButtonSend.setOnClickListener(v -> {
            String msg = binding.chatEditTextMessage.getText().toString();

            if(!msg.isEmpty()) {
                Chatting chatting = new Chatting();
                chatting.setMessage(msg);
                chatting.setUid(firebaseUser.getUid());
//                roomRef.child(LocalDateTime.now().toString()).setValue(chatting);
                roomRef.push().setValue(chatting);
                binding.chatEditTextMessage.setText("");
            }
        });

        myInfo.addValueEventListener(new ValueEventListener() {
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