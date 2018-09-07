package com.codingwithmitch.googlemaps2018.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.codingwithmitch.googlemaps2018.R;
import com.codingwithmitch.googlemaps2018.adapters.UserRecyclerAdapter;
import com.codingwithmitch.googlemaps2018.models.User;

import java.util.ArrayList;

public class UserListFragment extends Fragment {

    private static final String TAG = "UserListFragment";

    //widgets
    private RecyclerView mUserListRecyclerView;


    //vars
    private ArrayList<User> mUserList = new ArrayList<>();
    private UserRecyclerAdapter mUserRecyclerAdapter;


    public static UserListFragment newInstance(){
        return new UserListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            mUserList = getArguments().getParcelableArrayList(getString(R.string.intent_user_list));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_user_list, container, false);
        mUserListRecyclerView = view.findViewById(R.id.user_list_recycler_view);

        initUserListRecyclerView();
        return view;
    }


    private void initUserListRecyclerView(){
        mUserRecyclerAdapter = new UserRecyclerAdapter(mUserList);
        mUserListRecyclerView.setAdapter(mUserRecyclerAdapter);
        mUserListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}



















