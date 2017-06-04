package com.rviannaoliveira.vchatfirebase

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.util.*

/**
 * Criado por rodrigo on 28/05/17.
 */

class MainActivity : AppCompatActivity(), ChildEventListener {
    private lateinit var chatRoomAdapter: ChatRoomAdapter
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private var firebaseUser: FirebaseUser? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseUser = firebaseAuth.currentUser

        if (firebaseUser == null) {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
            return
        } else {
            val name = findViewById(R.id.name_user) as TextView
            name.text = firebaseUser?.email
        }


        chatRoomAdapter = ChatRoomAdapter()
        val recyclerView = findViewById(R.id.roomRecyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.adapter = chatRoomAdapter

        databaseReference = FirebaseDatabase.getInstance().reference
        databaseReference.child(Key.CHAT)
                .child(firebaseUser?.uid)//9V012RccjDd962mCb2iD09bOS4w1(O) // MT6Pk4mUvcgaPnyZHR39OY3YaFj2(U)
                .addChildEventListener(this)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exit -> {
                firebaseAuth.signOut()
                startActivity(Intent(this, SignInActivity::class.java))
                finish()
                return true
            }
            R.id.chat -> {
                createChat()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun createChat() {
        firebaseUser?.uid?.let {
            val chatRoom = createChatData(it)
            showChatActivity(chatRoom)
        }
    }

    private fun createChatData(it: String): ChatRoom {
        val uuid = UUID.randomUUID()
        val values = HashMap<String, Any>()
        val chatRoom = ChatRoom()
        chatRoom.codImovel = "2345"
        chatRoom.createAt = Date().time.toString().substring(0, 11)
        chatRoom.operador = "9V012RccjDd962mCb2iD09bOS4w1"
        chatRoom.roomId = uuid.toString()
        chatRoom.subtitle = "Avenida Ana Costa, 255"
        chatRoom.title = "Imobiliaria18"
        chatRoom.roomId = uuid.toString()

        values.put(ChatRoom.Column.COD_IMOVEL.value, chatRoom.codImovel.toString())
        values.put(ChatRoom.Column.CREATE_AT.value, chatRoom.createAt.toString())
        values.put(ChatRoom.Column.LAST_MESSAGE.value, "")
        values.put(ChatRoom.Column.OPERATOR.value, chatRoom.operador.toString())
        values.put(ChatRoom.Column.ROOM_ID.value, chatRoom.roomId.toString())
        values.put(ChatRoom.Column.SUB_TITLE.value, chatRoom.subtitle.toString())
        values.put(ChatRoom.Column.TITLE.value, chatRoom.title.toString())
        values.put(ChatRoom.Column.User.value, it)
        databaseReference.child(Key.CHAT).child(it).child(uuid.toString()).setValue(values)
        databaseReference.child(Key.CHAT).child(chatRoom.operador.toString()).child(uuid.toString()).setValue(values)
        return chatRoom
    }

    private fun showChatActivity(chatRoom: ChatRoom) {
        val intent = Intent(this, ChatActivity::class.java)
        intent.putExtra(Key.ROOM, chatRoom)
        startActivity(Intent(this, ChatActivity::class.java))
    }

    override fun onChildAdded(dataSnapshot: DataSnapshot, s: String) {
        val chatRoom = ChatRoom()
        chatRoom.key = dataSnapshot.key
        chatRoom.codImovel = dataSnapshot.child(ChatRoom.Column.COD_IMOVEL.value).value.toString()
        chatRoom.title = dataSnapshot.child(ChatRoom.Column.TITLE.value).value.toString()
        chatRoom.lastMessage = dataSnapshot.child(ChatRoom.Column.LAST_MESSAGE.value).value.toString()
        chatRoom.user = dataSnapshot.child(ChatRoom.Column.User.value).value.toString()
        chatRoom.operador = dataSnapshot.child(ChatRoom.Column.OPERATOR.value).value.toString()
        chatRoom.roomId = dataSnapshot.child(ChatRoom.Column.ROOM_ID.value).value.toString()

        chatRoomAdapter.setChatRoomAdapter(chatRoom)
    }

    override fun onChildChanged(dataSnapshot: DataSnapshot, s: String) {}

    override fun onChildRemoved(dataSnapshot: DataSnapshot) {}

    override fun onChildMoved(dataSnapshot: DataSnapshot, s: String) {}

    override fun onCancelled(databaseError: DatabaseError) {}
}
