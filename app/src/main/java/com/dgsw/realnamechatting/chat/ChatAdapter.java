package com.dgsw.realnamechatting.chat;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatAdapter {

    public static class ChatViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public TextView textViewMessage;
        public View rootView;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
