package com.rviannaoliveira.vchatfirebase

import android.os.Parcel
import android.os.Parcelable

/**
 * Criado por rodrigo on 28/05/17.
 */

open class ChatRoom : Parcelable {
    var key: String? = null
    var codImovel: String? = null
    var createAt: String? = null
    var lastMessage: String? = null
    var operador: String? = null
    var roomId: String? = null
    var subtitle: String? = null
    var title: String? = null
    var user: String? = null

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.key)
        dest.writeString(this.codImovel)
        dest.writeString(this.createAt)
        dest.writeString(this.lastMessage)
        dest.writeString(this.operador)
        dest.writeString(this.roomId)
        dest.writeString(this.subtitle)
        dest.writeString(this.title)
        dest.writeString(this.user)
    }

    constructor()

    protected constructor(`in`: Parcel) {
        this.key = `in`.readString()
        this.codImovel = `in`.readString()
        this.createAt = `in`.readString()
        this.lastMessage = `in`.readString()
        this.operador = `in`.readString()
        this.roomId = `in`.readString()
        this.subtitle = `in`.readString()
        this.title = `in`.readString()
        this.user = `in`.readString()
    }

    companion object {

        val CREATOR: Parcelable.Creator<ChatRoom> = object : Parcelable.Creator<ChatRoom> {
            override fun createFromParcel(source: Parcel): ChatRoom {
                return ChatRoom(source)
            }

            override fun newArray(size: Int): Array<ChatRoom?> {
                return arrayOfNulls(size)
            }
        }
    }

    enum class Column(val value : String){
        COD_IMOVEL("CodImovel"),
        CREATE_AT("CreateAt"),
        LAST_MESSAGE("LastMessage"),
        OPERATOR("Operator"),
        ROOM_ID("RoomId"),
        SUB_TITLE("SubTitle"),
        TITLE("Title"),
        User("User");
    }
}
