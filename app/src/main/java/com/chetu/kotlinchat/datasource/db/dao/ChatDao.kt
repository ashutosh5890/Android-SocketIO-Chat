package com.chetu.kotlinchat.datasource.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.chetu.kotlinchat.custom.constant.DbConstant.Companion.TABLE_CHAT
import com.chetu.kotlinchat.custom.constant.DbConstant.Companion.TABLE_CONTACT
import com.chetu.kotlinchat.datasource.db.entity.Message
import com.chetu.kotlinchat.datasource.db.entity.RecentChat

@Dao
interface ChatDao {

    @Query("SELECT MAX(M.messageId) AS msgId, M.*, CASE WHEN U1.id IS NULL THEN U2.id ELSE U1.id END AS id " +
            ", CASE WHEN U1.id IS NULL THEN U2.first_name ELSE U1.first_name END AS first_name " +
            ", CASE WHEN U1.id IS NULL THEN U2.last_name ELSE U1.last_name END AS last_name " +
            ", CASE WHEN U1.id IS NULL THEN U2.full_name ELSE U1.full_name END AS full_name " +
            ", CASE WHEN U1.id IS NULL THEN U2.email ELSE U1.email END AS email " +
            ", CASE WHEN U1.id IS NULL THEN U2.pic ELSE U1.pic END AS pic " +
            "FROM $TABLE_CHAT AS M " +
            "LEFT JOIN $TABLE_CONTACT AS U1 ON M.sender_id = U1.id " +
            "LEFT JOIN $TABLE_CONTACT AS U2 ON M.receiver_id = U2.id " +
            "WHERE M.sender_id = :userId OR M.receiver_id = :userId " +
            "GROUP BY M.chat_id ORDER BY M.time DESC")
    fun getRecentChats(userId: Int): List<RecentChat>

    @Query("SELECT * FROM $TABLE_CHAT WHERE (sender_id = :userId AND receiver_id = :otherUserId) OR (sender_id = :otherUserId AND receiver_id = :userId) ORDER BY time ASC")
    fun getConversation(userId: Int, otherUserId: Int): List<Message>

    @Query("UPDATE $TABLE_CHAT SET status = :status WHERE messageId = :messageId")
    fun updateMessageStatus(messageId: Long, status: String)

    @Insert
    fun insert(message: Message)

    @Delete
    fun delete(message: Message)

    @Delete
    fun delete(vararg messages: Message)
}