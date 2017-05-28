package com.rviannaoliveira.vchatfirebase;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Criado por rodrigo on 28/05/17.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{

    private List<ChatMessage> list = new ArrayList<>();
    private String user;

    MessageAdapter(String user){
        this.user = user;
    }

    void setMessageAdapter(List<ChatMessage> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    void setMessageAdapter(ChatMessage chatMessage){
        if(list.contains(chatMessage)){
            return;
        }
        this.list.add(chatMessage);
        notifyDataSetChanged();
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MessageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,parent,false));
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        if(user.equals(list.get(position).getType())){
            holder.messageUser.setText(list.get(position).getMessage());
        }else{
            holder.messageText.setText(list.get(position).getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    void clear() {
        list.clear();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder{
        private TextView messageUser,messageText;
        private LinearLayout block;

        MessageViewHolder(View itemView) {
            super(itemView);
            messageUser = (TextView) itemView.findViewById(R.id.message_user);
            messageText = (TextView) itemView.findViewById(R.id.message_text);
        }

    }
}
