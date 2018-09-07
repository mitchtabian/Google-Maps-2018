package com.codingwithmitch.googlemaps2018.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codingwithmitch.googlemaps2018.R;
import com.codingwithmitch.googlemaps2018.models.ChatMessage;
import com.codingwithmitch.googlemaps2018.models.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ChatMessageRecyclerAdapter extends RecyclerView.Adapter<ChatMessageRecyclerAdapter.ViewHolder>{

    private ArrayList<ChatMessage> mMessages = new ArrayList<>();
    private ArrayList<User> mUsers = new ArrayList<>();
    private Context mContext;

    public ChatMessageRecyclerAdapter(ArrayList<ChatMessage> messages,
                                      ArrayList<User> users,
                                      Context context) {
        this.mMessages = messages;
        this.mUsers = users;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_chat_message_list_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


        if(FirebaseAuth.getInstance().getUid().equals(mMessages.get(position).getUser().getUser_id())){
            ((ViewHolder)holder).username.setTextColor(ContextCompat.getColor(mContext, R.color.green1));
        }
        else{
            ((ViewHolder)holder).username.setTextColor(ContextCompat.getColor(mContext, R.color.blue2));
        }

        ((ViewHolder)holder).username.setText(mMessages.get(position).getUser().getUsername());
        ((ViewHolder)holder).message.setText(mMessages.get(position).getMessage());
    }



    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView message, username;

        public ViewHolder(View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.chat_message_message);
            username = itemView.findViewById(R.id.chat_message_username);
        }
    }


}
















