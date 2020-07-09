package com.dgsw.realnamechatting.main.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dgsw.realnamechatting.R;
import com.dgsw.realnamechatting.data.User;
import com.dgsw.realnamechatting.databinding.FragmentFriendBinding;
import com.dgsw.realnamechatting.friend.FindUserActivity;
import com.dgsw.realnamechatting.friend.FriendListAdapter;
import com.dgsw.realnamechatting.main.MainViewModel;
import com.dgsw.realnamechatting.data.OnValueChangedCallBack;
import com.dgsw.realnamechatting.manager.FirebaseManager;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class FriendFragment extends Fragment {

    private MainViewModel mainViewModel;
    private FragmentFriendBinding binding;

    private FriendListAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        binding = FragmentFriendBinding.inflate(inflater);

        setHasOptionsMenu(true);

        mainViewModel.getUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                binding.textHome.setText(user.getName());
            }
        });
        mainViewModel.getFriendCount().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                adapter.notifyDataSetChanged();
            }
        });

        adapter = new FriendListAdapter(mainViewModel.getFriends(), new FriendListAdapter.OnClickFriendListener() {
            @Override
            public void onFriendClick(User user) {
                FirebaseManager firebaseManager = mainViewModel.getFirebaseManager();

                DatabaseReference chatRef =  firebaseManager.getDBReference("rooms");
                String key = chatRef.push().getKey().toString();
                DatabaseReference myRef = firebaseManager.getDBReference("users/" + mainViewModel.getUser().getValue().getUid() + "/rooms/" + key);
                myRef.setValue("sole");
                DatabaseReference friendRef = firebaseManager.getDBReference("users/" + user.getUid() + "/rooms/" + key);
                friendRef.setValue("sole");
            }
        });

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mainViewModel.setOnValueChangedCallBack(onValueChangedCallBack);

        return binding.getRoot();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.btn_add_friend) {
            Intent intent = new Intent(getActivity(), FindUserActivity.class);
            getActivity().startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.friend_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private OnValueChangedCallBack onValueChangedCallBack = () -> {
        adapter.notifyDataSetChanged();
    };
}