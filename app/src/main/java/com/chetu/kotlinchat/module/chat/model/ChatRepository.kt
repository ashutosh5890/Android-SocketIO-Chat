package com.chetu.kotlinchat.module.chat.model

import android.arch.lifecycle.LiveData
import android.util.Log
import com.chetu.kotlinchat.datasource.api.RemoteRepository
import com.chetu.kotlinchat.datasource.db.ChatDatabase
import com.chetu.kotlinchat.datasource.db.entity.Contact
import com.chetu.kotlinchat.datasource.db.entity.Message
import com.chetu.kotlinchat.datasource.db.entity.RecentChat
import com.chetu.kotlinchat.module.contact.model.ContactRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

object ChatRepository {

    const val TAG = "ChatRepository"

    var chatDao = ChatDatabase.getInstance().getChatDao()

    fun loadRecentChats(userId: Int): Observable<List<RecentChat>> {
        Log.e(TAG, "Loading local chats")
        return Observable.fromCallable {
            val recentChat = chatDao.getRecentChats(userId)
            Log.e(TAG, "Local recent chats " + recentChat.size)
            recentChat
        }
    }

    fun loadConversation(userId: Int, otherUserid: Int) : Observable<List<Message>>{
        Log.e(TAG, "Loading conversation from db")
        return Observable.fromCallable {
            val conversationList = chatDao.getConversation(userId, otherUserid)
            Log.e(TAG, "Local conversation " + conversationList.size)
            conversationList
        }
    }
}