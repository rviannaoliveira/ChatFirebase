package com.rviannaoliveira.vchatfirebase


/**
 * Criado por rodrigo on 28/05/17.
 */
class ChatMessage {
    var key: String? = null
    var message: String? = null
    var type: String? = null
    var createAt: Long = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as ChatMessage

        if (key != other.key) return false

        return true
    }

    override fun hashCode(): Int {
        return key?.hashCode() ?: 0
    }


}