package com.dgsw.realnamechatting.main.ui;

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

import com.dgsw.realnamechatting.databinding.FragmentFriendBinding;
import com.dgsw.realnamechatting.databinding.FragmentSettingBinding;
import com.dgsw.realnamechatting.main.MainViewModel;

public class SettingFragment extends Fragment {

    private MainViewModel mainViewModel;
    private FragmentSettingBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        binding = FragmentSettingBinding.inflate(inflater);

        final TextView textView = binding.textHome;

        mainViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return binding.getRoot();
    }
}