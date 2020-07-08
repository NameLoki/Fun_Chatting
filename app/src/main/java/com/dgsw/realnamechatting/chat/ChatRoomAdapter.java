package com.dgsw.realnamechatting.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dgsw.realnamechatting.R;
import com.dgsw.realnamechatting.data.ChatRoom;

import java.util.List;

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.ChatRoomViewHolder> {

    private OnClickRoomListener listener;
    private List<ChatRoom> rooms;

    public ChatRoomAdapter(List<ChatRoom> rooms, OnClickRoomListener listener) {
        this.rooms = rooms;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ChatRoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
        return new ChatRoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRoomViewHolder holder, int position) {
        ChatRoom room = rooms.get(position);

        String names = null;

//        for(String name : room.getUsers()) {
//            names += name + ", ";
//        }

        holder.name.setText(room.getId());
//        holder.lastChat.setText(room.getLastChat());

        holder.itemView.setOnClickListener(v -> {
            listener.onItemClick(room);
        });

        holder.itemView.setOnLongClickListener(v -> {
            listener.onItemLongClick(room);
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return rooms == null ? 0 : rooms.size();
    }

    public void setRooms(List<ChatRoom> rooms) {
        this.rooms = rooms;
    }

    class ChatRoomViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView lastChat;

        public ChatRoomViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.room_textViewName);
            lastChat = itemView.findViewById(R.id.room_textViewLastChat);
        }
    }

    public interface OnClickRoomListener {
        public void onItemClick(ChatRoom chatRoom);
        public void onItemLongClick(ChatRoom chatRoom);
    }
}