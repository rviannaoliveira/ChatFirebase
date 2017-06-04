package com.rviannaoliveira.vchatfirebase

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.util.*

/**
 * Criado por rodrigo on 28/05/17.
 */

class ChatActivity : AppCompatActivity(), ChildEventListener {
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageEditText: EditText
    private lateinit var databaseReference: DatabaseReference
    private var firebaseUser: FirebaseUser? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        firebaseUser = FirebaseAuth.getInstance().currentUser

        val chatRoom = intent.getParcelableExtra<ChatRoom>(Key.ROOM)
        messageAdapter = MessageAdapter(firebaseUser?.uid)

        val recyclerView = findViewById(R.id.messageRecyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = messageAdapter

        messageEditText = findViewById(R.id.messageEditText) as EditText
        val sendButton = findViewById(R.id.sendButton) as Button

        sendButton.setOnClickListener {sendMessage(chatRoom)}

        databaseReference = FirebaseDatabase.getInstance().reference
        databaseReference.child(Key.MESSAGES)
                .child(chatRoom.roomId)
                .addChildEventListener(this)
    }

    private fun sendMessage(chatRoom: ChatRoom) {
        firebaseUser?.uid?.let {
            val values = HashMap<String, Any>()
            val valuesRoom = HashMap<String, Any>()
            val time = Date().time.toString().substring(0, 11)

            values.put(ChatMessage.Column.CREATE_AT.value, time)
            values.put(ChatMessage.Column.MESSAGE.value, messageEditText.text.toString())
            values.put(ChatMessage.Column.TYPE.value, it)
            valuesRoom.put(ChatRoom.Column.LAST_MESSAGE.value, messageEditText.text.toString())
            databaseReference.child(Key.MESSAGES).child(chatRoom.roomId).child(time).setValue(values)

            databaseReference.child(Key.CHAT).child(chatRoom.user).child(chatRoom.roomId).updateChildren(valuesRoom)
            databaseReference.child(Key.CHAT).child(chatRoom.operador).child(chatRoom.roomId).updateChildren(valuesRoom)
            messageEditText.setText("")
        }

    }

    override fun onChildAdded(dataSnapshot: DataSnapshot, ch: String?) {
        val chatMessage = ChatMessage()
        chatMessage.key = dataSnapshot.key
        chatMessage.type = dataSnapshot.child(ChatMessage.Column.TYPE.value).value.toString()
        chatMessage.message = dataSnapshot.child(ChatMessage.Column.MESSAGE.value).value.toString()
        messageAdapter.setMessageAdapter(chatMessage)
    }

    override fun onChildChanged(dataSnapshot: DataSnapshot, c: String?) {}

    override fun onChildRemoved(dataSnapshot: DataSnapshot) {}

    override fun onChildMoved(dataSnapshot: DataSnapshot, d: String?) {}

    override fun onCancelled(databaseError: DatabaseError) {}
}
