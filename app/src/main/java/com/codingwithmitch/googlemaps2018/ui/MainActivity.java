package com.codingwithmitch.googlemaps2018.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.codingwithmitch.googlemaps2018.R;
import com.codingwithmitch.googlemaps2018.adapters.ChatroomRecyclerAdapter;
import com.codingwithmitch.googlemaps2018.models.Chatroom;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nullable;

import static com.codingwithmitch.googlemaps2018.Constants.ERROR_DIALOG_REQUEST;


public class MainActivity extends AppCompatActivity implements
        View.OnClickListener,
        ChatroomRecyclerAdapter.ChatroomRecyclerClickListener
{

    private static final String TAG = "MainActivity";

    //widgets
    private ProgressBar mProgressBar;

    //vars
    private ArrayList<Chatroom> mChatrooms = new ArrayList<>();
    private Set<String> mChatroomIds = new HashSet<>();
    private ChatroomRecyclerAdapter mChatroomRecyclerAdapter;
    private RecyclerView mChatroomRecyclerView;
    private ListenerRegistration mChatroomEventListener;
    private FirebaseFirestore mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = findViewById(R.id.progressBar);
        mChatroomRecyclerView = findViewById(R.id.chatrooms_recycler_view);

        findViewById(R.id.fab_create_chatroom).setOnClickListener(this);

        mDb = FirebaseFirestore.getInstance();

        initSupportActionBar();
        initChatroomRecyclerView();
    }

    private void initSupportActionBar(){
        setTitle("Chatrooms");
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.fab_create_chatroom:{
                newChatroomDialog();
            }
        }
    }

    private void initChatroomRecyclerView(){
        mChatroomRecyclerAdapter = new ChatroomRecyclerAdapter(mChatrooms, this);
        mChatroomRecyclerView.setAdapter(mChatroomRecyclerAdapter);
        mChatroomRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getChatrooms(){

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        mDb.setFirestoreSettings(settings);

        CollectionReference chatroomsCollection = mDb
                .collection(getString(R.string.collection_chatrooms));

        mChatroomEventListener = chatroomsCollection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                Log.d(TAG, "onEvent: called.");

                if (e != null) {
                    Log.e(TAG, "onEvent: Listen failed.", e);
                    return;
                }

                if(queryDocumentSnapshots != null){
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {

                        Chatroom chatroom = doc.toObject(Chatroom.class);
                        if(!mChatroomIds.contains(chatroom.getChatroom_id())){
                            mChatroomIds.add(chatroom.getChatroom_id());
                            mChatrooms.add(chatroom);
                        }
                    }
                    Log.d(TAG, "onEvent: number of chatrooms: " + mChatrooms.size());
                    mChatroomRecyclerAdapter.notifyDataSetChanged();
                }

            }
        });
    }

    private void buildNewChatroom(String chatroomName){

        final Chatroom chatroom = new Chatroom();
        chatroom.setTitle(chatroomName);

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        mDb.setFirestoreSettings(settings);

        DocumentReference newChatroomRef = mDb
                .collection(getString(R.string.collection_chatrooms))
                .document();

        chatroom.setChatroom_id(newChatroomRef.getId());

        newChatroomRef.set(chatroom).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                hideDialog();

                if(task.isSuccessful()){
                    navChatroomActivity(chatroom);
                }else{
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "Something went wrong.", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void navChatroomActivity(Chatroom chatroom){
        Intent intent = new Intent(MainActivity.this, ChatroomActivity.class);
        intent.putExtra(getString(R.string.intent_chatroom), chatroom);
        startActivity(intent);
    }

    private void newChatroomDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter a chatroom name");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("CREATE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!input.getText().toString().equals("")){
                    buildNewChatroom(input.getText().toString());
                }
                else {
                    Toast.makeText(MainActivity.this, "Enter a chatroom name", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mChatroomEventListener != null){
            mChatroomEventListener.remove();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getChatrooms();
    }

    @Override
    public void onChatroomSelected(int position) {
        navChatroomActivity(mChatrooms.get(position));
    }

    private void signOut(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_sign_out:{
                signOut();
                return true;
            }
            case R.id.action_profile:{
                startActivity(new Intent(this, ProfileActivity.class));
                return true;
            }
            default:{
                return super.onOptionsItemSelected(item);
            }
        }

    }

    private void showDialog(){
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideDialog(){
        mProgressBar.setVisibility(View.GONE);
    }


}
