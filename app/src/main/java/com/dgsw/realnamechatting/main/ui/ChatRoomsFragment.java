package com.dgsw.realnamechatting.main.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dgsw.realnamechatting.chat.ChatActivity;
import com.dgsw.realnamechatting.chat.ChatCreateRoomActivity;
import com.dgsw.realnamechatting.chat.ChatRoomAdapter;
import com.dgsw.realnamechatting.data.ChatRoom;
import com.dgsw.realnamechatting.databinding.FragmentChatroomsBinding;
import com.dgsw.realnamechatting.main.MainViewModel;
import com.dgsw.realnamechatting.data.OnValueChangedCallBack;

import java.util.ArrayList;

public class ChatRoomsFragment extends Fragment {

    private MainViewModel mainViewModel;
    private FragmentChatroomsBinding binding;
    private ChatRoomAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainViewModel.setOnValueChangedCallBack(onValueChangedCallBack);
    }

    private OnValueChangedCallBack onValueChangedCallBack = () -> {
        adapter.notifyDataSetChanged();
    };

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        binding = FragmentChatroomsBinding.inflate(inflater);

        adapter = new ChatRoomAdapter(mainViewModel.getRooms(), new ChatRoomAdapter.OnClickRoomListener() {
            @Override
            public void onItemClick(ChatRoom chatRoom) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("id", chatRoom.getId());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(ChatRoom chatRoom) {

            }
        });
        binding.recyclerViewRooms.setAdapter(adapter);
        binding.recyclerViewRooms.setLayoutManager(new LinearLayoutManager(getActivity()));

        return binding.getRoot();
    }

    public void loadCreateRoom() {
        Intent intent = new Intent(getActivity(), ChatCreateRoomActivity.class);
        intent.putParcelableArrayListExtra("friends", (ArrayList)mainViewModel.getFriends());
        startActivity(intent);
    }
}