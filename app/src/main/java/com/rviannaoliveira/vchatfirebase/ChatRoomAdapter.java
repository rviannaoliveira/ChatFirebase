package com.rviannaoliveira.vchatfirebase;

import android.content.Context;
import android.content.Intent;
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

public class ChatRoomAdapter  extends RecyclerView.Adapter<ChatRoomAdapter.RoomViewHolder>{

    private List<ChatRoom> list = new ArrayList<>();
    private Context context;

    void setChatRoomAdapter(List<ChatRoom> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public RoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        return new RoomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room,parent,false));
    }

    @Override
    public void onBindViewHolder(RoomViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.lastMessage.setText(list.get(position).getLastMessage());
        holder.block.setTag(list.get(position));

        holder.block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ChatActivity.class);
                intent.putExtra("room",(ChatRoom) v.getTag());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class RoomViewHolder extends RecyclerView.ViewHolder{
        private TextView title,lastMessage;
        private LinearLayout block;

        RoomViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            lastMessage = (TextView) itemView.findViewById(R.id.lastMessage);
            block = (LinearLayout) itemView.findViewById(R.id.block);

        }

    }
}
