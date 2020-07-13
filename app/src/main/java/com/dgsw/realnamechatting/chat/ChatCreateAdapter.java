package com.dgsw.realnamechatting.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dgsw.realnamechatting.R;
import com.dgsw.realnamechatting.data.CreateUser;
import com.dgsw.realnamechatting.data.User;

import java.util.List;

public class ChatCreateAdapter extends RecyclerView.Adapter<ChatCreateAdapter.ChatCreateViewHolder> {

    private List<CreateUser> friends;
    private OnClickChatCreateListener onClickChatCreateListener;

    public ChatCreateAdapter(List<CreateUser> friends, OnClickChatCreateListener onClickChatCreateListener) {
        this.friends = friends;
        this.onClickChatCreateListener = onClickChatCreateListener;
    }

    @NonNull
    @Override
    public ChatCreateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend_create, parent, false);
        return new ChatCreateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatCreateViewHolder holder, int position) {
        User user = friends.get(position);

        holder.name.setText(user.getName());
        holder.msg.setText(user.getProfileMessage());
    }

    @Override
    public int getItemCount() {
        return friends == null ? 0 : friends.size();
    }

    class ChatCreateViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView msg;
        public CheckBox checkBox;

        public ChatCreateViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.create_textViewName);
            msg = itemView.findViewById(R.id.create_textViewEmail);
            checkBox = itemView.findViewById(R.id.create_checkBox);
        }
    }

    public interface OnClickChatCreateListener {
        public void onItemClick(User user);
        public void onItemLongClick(User user);
    }
}
