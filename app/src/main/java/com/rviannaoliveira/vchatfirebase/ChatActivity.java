package com.rviannaoliveira.vchatfirebase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Criado por rodrigo on 28/05/17.
 */

public class ChatActivity extends AppCompatActivity implements ValueEventListener,ChildEventListener {
    private MessageAdapter messageAdapter;
    private EditText messageEditText;
    private DatabaseReference databaseReference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        final ChatRoom chatRoom = getIntent().getParcelableExtra("room");
        messageAdapter = new MessageAdapter(chatRoom.getOperador());//operator

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.messageRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageAdapter);

        messageEditText = (EditText) findViewById(R.id.messageEditText);
        Button sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> values = new HashMap<>();
                String time = String.valueOf(new Date().getTime()).substring(0,11);

                values.put("CreateAt", time);
                values.put("Message",messageEditText.getText().toString());
                values.put("Type", chatRoom.getOperador());//operator

                databaseReference.child("messages").child(chatRoom.getRoomId()).child(time).setValue(values);
                messageEditText.setText("");
            }
        });


        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("messages")
                .child(chatRoom.getRoomId())
                .addChildEventListener(this);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        List<ChatMessage> list = new ArrayList<>();
        ChatMessage chatMessage;

        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            chatMessage = new ChatMessage();
            chatMessage.setKey(ds.getKey());
            chatMessage.setType(ds.child("Type").getValue().toString());
            chatMessage.setMessage(ds.child("Message").getValue().toString());
            list.add(chatMessage);
        }

        messageAdapter.setMessageAdapter(list);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setKey(dataSnapshot.getKey());
        chatMessage.setType(dataSnapshot.child("Type").getValue().toString());
        chatMessage.setMessage(dataSnapshot.child("Message").getValue().toString());
        messageAdapter.setMessageAdapter(chatMessage);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
    }
}
