package com.chetu.kotlinchat.datasource.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.DrawableRes
import com.chetu.kotlinchat.R
import com.chetu.kotlinchat.custom.constant.DbConstant.Companion.TABLE_CHAT

@Entity(tableName = TABLE_CHAT)
class Message {

    @PrimaryKey
    var messageId: Long? = null

    @ColumnInfo(name = "chat_id") var chatId: Long? = null

    @ColumnInfo(name = "sender_id") var senderId: Int? = null

    @ColumnInfo(name = "receiver_id") var receiverId: Int? = null

    @ColumnInfo(name = "msg_text") var messageText: String? = null

    @ColumnInfo(name = "media_path") var mediaPath :String? = null

    var time: Long? = null

    var status: String? = null
        set(value) {
            field = value
            messageStatus = MessageStatus.valueOf(value?: MessageStatus.SENDING.toString())
        }

    @Ignore
    var messageStatus: MessageStatus? = MessageStatus.SENDING

    enum class MessageStatus(var text: String, var code: String, @DrawableRes var icon: Int) {
        SENDING("Sending", "sending", R.drawable.ic_sending),
        SENT("Sent", "sent", R.drawable.ic_sent),
        DELIVERED("Delivered", "delivered", R.drawable.ic_delivered),
        READ("Read", "read", R.drawable.ic_read)
    }
}