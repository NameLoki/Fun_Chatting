package com.dgsw.realnamechatting.main.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dgsw.realnamechatting.chat.ChatActivity;
import com.dgsw.realnamechatting.databinding.FragmentChatroomsBinding;
import com.dgsw.realnamechatting.databinding.FragmentFriendBinding;
import com.dgsw.realnamechatting.main.MainActivity;
import com.dgsw.realnamechatting.main.MainViewModel;

public class ChatRoomsFragment extends Fragment {

    private MainViewModel mainViewModel;
    private FragmentChatroomsBinding binding;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonTest.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            startActivity(intent);
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        binding = FragmentChatroomsBinding.inflate(inflater);

        return binding.getRoot();
    }
}