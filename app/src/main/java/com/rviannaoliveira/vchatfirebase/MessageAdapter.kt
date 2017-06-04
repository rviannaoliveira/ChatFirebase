package com.rviannaoliveira.vchatfirebase

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

class MessageAdapter internal constructor(private val user: String) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    private val list = LinkedList<ChatMessage>()

    fun setMessageAdapter(chatMessage: ChatMessage) {
        if (list.contains(chatMessage)) {
            return
        }
        this.list.add(chatMessage)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false))
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        if (user == list[position].type) {
            holder.messageUser.text = list[position].message
            holder.blockIn.visibility = View.VISIBLE
        } else {
            holder.messageText.text = list[position].message
            holder.blockOut.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageUser: TextView = itemView.findViewById(R.id.message_user) as TextView
        val messageText: TextView = itemView.findViewById(R.id.message_text) as TextView
        val blockOut: LinearLayout = itemView.findViewById(R.id.block_out) as LinearLayout
        val blockIn: LinearLayout = itemView.findViewById(R.id.block_in) as LinearLayout

    }
}
