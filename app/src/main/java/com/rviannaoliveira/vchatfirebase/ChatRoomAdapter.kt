package com.rviannaoliveira.vchatfirebase

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import java.util.*

/**
 * Criado por rodrigo on 28/05/17.
 */

class ChatRoomAdapter : RecyclerView.Adapter<ChatRoomAdapter.RoomViewHolder>() {
    private val list = ArrayList<ChatRoom>()
    private lateinit var context: Context

    fun setChatRoomAdapter(chatRoom : ChatRoom) {
        this.list.add(chatRoom)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        this.context = parent.context
        return RoomViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_room, parent, false))
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.title.text = list[position].title
        holder.lastMessage.text = list[position].lastMessage
        holder.block.tag = list[position]

        holder.block.setOnClickListener { v ->
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(Key.ROOM, v.tag as ChatRoom)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class RoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title) as TextView
        val lastMessage: TextView = itemView.findViewById(R.id.lastMessage) as TextView
        val block: LinearLayout = itemView.findViewById(R.id.block) as LinearLayout

    }
}
