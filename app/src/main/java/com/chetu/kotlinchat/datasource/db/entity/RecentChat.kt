package com.chetu.kotlinchat.datasource.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.PrimaryKey


class RecentChat {

    var id: Int? = null

    @ColumnInfo(name = "first_name") var firstName: String? = null

    @ColumnInfo(name = "last_name") var lastName: String? = null

    @ColumnInfo(name = "full_name") var fullName: String? = null

    var email: String? = null

    var pic: String? = null

    var messageId: Long? = null

    @ColumnInfo(name = "chat_id") var chatId: Long? = null

    @ColumnInfo(name = "sender_id") var senderId: Int? = null

    @ColumnInfo(name = "receiver_id") var receiverId: Int? = null

    @ColumnInfo(name = "msg_text") var messageText: String? = null

    @ColumnInfo(name = "media_path") var mediaPath :String? = null

    var status: String? = null

    var time: Long? = null

    var msgId: Long? = null

    /*@Embedded
    var contact: Contact? = null

    @Embedded
    var message: Message? = null*/

}