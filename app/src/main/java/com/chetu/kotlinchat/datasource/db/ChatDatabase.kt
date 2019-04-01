package com.chetu.kotlinchat.datasource.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import com.chetu.kotlinchat.AppController
import com.chetu.kotlinchat.custom.constant.DbConstant
import com.chetu.kotlinchat.datasource.db.dao.ChatDao
import com.chetu.kotlinchat.datasource.db.dao.ContactDao
import com.chetu.kotlinchat.datasource.db.entity.Contact
import com.chetu.kotlinchat.datasource.db.entity.Message

@Database(entities = arrayOf(Contact::class, Message::class), version = 1)
abstract class ChatDatabase : RoomDatabase() {

    abstract fun getContactDao() : ContactDao

    abstract fun getChatDao() : ChatDao

    companion object {

        @Volatile private var INSTANCE: ChatDatabase? =null

        fun getInstance(): ChatDatabase{
            return INSTANCE?: Room.databaseBuilder(AppController.appContext, ChatDatabase::class.java, DbConstant.CHAT_DATABASE).build()
        }
    }
}