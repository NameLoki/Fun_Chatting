package com.dgsw.realnamechatting.friend;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dgsw.realnamechatting.R;
import com.dgsw.realnamechatting.chat.ChatAdapter;
import com.dgsw.realnamechatting.data.ChatRoom;
import com.dgsw.realnamechatting.data.User;

import java.util.List;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.FriendViewHolder> {

    private OnClickFriendListener onClickFriendListener;
    private List<User> friends;


    public void addFriend(User user) {
        friends.add(user);
        notifyDataSetChanged();
    }

    public FriendListAdapter(List<User> friends, OnClickFriendListener onClickFriendListener) {
        this.friends = friends;
        this.onClickFriendListener = onClickFriendListener;
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend, parent, false);
        return new FriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        User user = friends.get(position);
        holder.name.setText(user.getName());
        holder.email.setText(user.getEmail());
//        holder.button.setOnClickListener(v -> {
//            this.onClickFriendListener.onFriendClick(user);
//        });
    }

    @Override
    public int getItemCount() {
        return friends == null ? 0 : friends.size();
    }

    class FriendViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView email;
        private Button button;

        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.friend_textViewName);
            email = itemView.findViewById((R.id.friend_textViewEmail));
//            button = itemView.findViewById(R.id.friend_buttonChat);

        }
    }

    public interface OnClickFriendListener {
        public void onFriendClick(User user);
    }
}
