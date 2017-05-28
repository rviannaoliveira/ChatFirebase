package com.rviannaoliveira.vchatfirebase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Criado por rodrigo on 28/05/17.
 */

public class MainActivity extends AppCompatActivity implements ValueEventListener {
    private ChatRoomAdapter chatRoomAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chatRoomAdapter = new ChatRoomAdapter();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.roomRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(chatRoomAdapter);

        FirebaseDatabase.getInstance()
                .getReference()
                .child("chats")
                .child("9V012RccjDd962mCb2iD09bOS4w1")//9V012RccjDd962mCb2iD09bOS4w1 // MT6Pk4mUvcgaPnyZHR39OY3YaFj2
                .addListenerForSingleValueEvent(this);


    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        List<ChatRoom> list = new ArrayList<>();
        ChatRoom chatRoom;

        for(DataSnapshot ds2 : dataSnapshot.getChildren()) {
            chatRoom = new ChatRoom();
            chatRoom.setKey(ds2.getKey());
            chatRoom.setCodImovel(ds2.child("CodImovel").getValue().toString());
            chatRoom.setTitle(ds2.child("Title").getValue().toString());
            chatRoom.setLastMessage(ds2.child("LastMessage").getValue().toString());
            chatRoom.setUser(ds2.child("User").getValue().toString());
            chatRoom.setOperador(ds2.child("Operator").getValue().toString());
            chatRoom.setRoomId(ds2.child("RoomId").getValue().toString());
            list.add(chatRoom);
        }

        chatRoomAdapter.setChatRoomAdapter(list);

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
