package com.dgsw.realnamechatting.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.dgsw.realnamechatting.R;
import com.dgsw.realnamechatting.data.Chatting;
import com.dgsw.realnamechatting.data.User;
import com.dgsw.realnamechatting.manager.FirebaseManager;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<Chatting> chattings;

    private User user;
    private OnClickChatListener listener;
    private DatabaseReference roomRef;
    private FirebaseDatabase database;

    public ChatAdapter(List<Chatting> chattings, OnClickChatListener listener) {
        this.chattings = chattings;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new ChatAdapter.ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Chatting chatting = chattings.get(position);

        if(chatting.getUid().equals(FirebaseManager.getInstance().getLoginUser().getUid())) {
            setChatTextAlignment(holder, View.TEXT_ALIGNMENT_VIEW_END);
        }

        holder.name.setText(chatting.getUid());
        holder.msg.setText(chatting.getMessage());
    }

    private void setChatTextAlignment(ChatViewHolder holder, int textAlignment) {
        holder.name.setTextAlignment(textAlignment);
        holder.msg.setTextAlignment(textAlignment);
    }

    @Override
    public int getItemCount() {
        return chattings == null ? 0 : chattings.size();
    }

    public void addChatting(Chatting chatting) {
        chattings.add(chatting);
        notifyDataSetChanged();
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView msg;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.chat_textViewName);
            msg = itemView.findViewById(R.id.chat_textViewMsg);
        }
    }

    public interface OnClickChatListener {
        public void onChatClick(Chatting chatting);
    }
}
