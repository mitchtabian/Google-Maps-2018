package com.codingwithmitch.googlemaps2018.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.codingwithmitch.googlemaps2018.R;
import com.codingwithmitch.googlemaps2018.models.Chatroom;

import java.util.ArrayList;

public class ChatroomRecyclerAdapter extends RecyclerView.Adapter<ChatroomRecyclerAdapter.ViewHolder>{

    private ArrayList<Chatroom> mChatrooms = new ArrayList<>();
    private ChatroomRecyclerClickListener mChatroomRecyclerClickListener;

    public ChatroomRecyclerAdapter(ArrayList<Chatroom> chatrooms, ChatroomRecyclerClickListener chatroomRecyclerClickListener) {
        this.mChatrooms = chatrooms;
        mChatroomRecyclerClickListener = chatroomRecyclerClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_chatroom_list_item, parent, false);
        final ViewHolder holder = new ViewHolder(view, mChatroomRecyclerClickListener);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ((ViewHolder)holder).chatroomTitle.setText(mChatrooms.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mChatrooms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener
    {
        TextView chatroomTitle;
        ChatroomRecyclerClickListener clickListener;

        public ViewHolder(View itemView, ChatroomRecyclerClickListener clickListener) {
            super(itemView);
            chatroomTitle = itemView.findViewById(R.id.chatroom_title);
            this.clickListener = clickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onChatroomSelected(getAdapterPosition());
        }
    }

    public interface ChatroomRecyclerClickListener {
        public void onChatroomSelected(int position);
    }
}
















