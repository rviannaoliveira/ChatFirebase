package com.rviannaoliveira.vchatfirebase;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Criado por rodrigo on 28/05/17.
 */

public class ChatRoom implements Parcelable {
    private String key;
    private String codImovel;
    private String createAt;
    private String lastMessage;
    private String operador;
    private String roomId;
    private String subtitle;
    private String title;
    private String user;

    public String getCodImovel() {
        return codImovel;
    }

    public void setCodImovel(String codImovel) {
        this.codImovel = codImovel;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeString(this.codImovel);
        dest.writeString(this.createAt);
        dest.writeString(this.lastMessage);
        dest.writeString(this.operador);
        dest.writeString(this.roomId);
        dest.writeString(this.subtitle);
        dest.writeString(this.title);
        dest.writeString(this.user);
    }

    public ChatRoom() {
    }

    protected ChatRoom(Parcel in) {
        this.key = in.readString();
        this.codImovel = in.readString();
        this.createAt = in.readString();
        this.lastMessage = in.readString();
        this.operador = in.readString();
        this.roomId = in.readString();
        this.subtitle = in.readString();
        this.title = in.readString();
        this.user = in.readString();
    }

    public static final Parcelable.Creator<ChatRoom> CREATOR = new Parcelable.Creator<ChatRoom>() {
        @Override
        public ChatRoom createFromParcel(Parcel source) {
            return new ChatRoom(source);
        }

        @Override
        public ChatRoom[] newArray(int size) {
            return new ChatRoom[size];
        }
    };
}
