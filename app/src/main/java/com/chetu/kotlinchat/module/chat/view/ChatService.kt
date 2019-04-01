package com.chetu.kotlinchat.module.chat.view

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.chetu.kotlinchat.custom.helper.SessionManager
import com.chetu.kotlinchat.datasource.db.ChatDatabase
import com.chetu.kotlinchat.datasource.db.dao.ChatDao
import com.chetu.kotlinchat.datasource.db.entity.Message
import com.chetu.kotlinchat.datasource.db.entity.MsgStatus
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.net.URISyntaxException

class ChatService : Service() {

    val TAG = "ChatService";

    var binder: IBinder = ChatBinder()
    private var chatSocket : Socket? = null
    var chatCallback: ChatServiceCallback? = null
    lateinit var chatDao: ChatDao

    init {
        try {
            chatSocket = IO.socket("http://10.0.2.2:3000")
        }catch (e: URISyntaxException){}
        chatDao = ChatDatabase.getInstance().getChatDao()
    }

    override fun onCreate() {
        super.onCreate()
        Log.e(TAG, "onCreate")
        chatSocket?.connect()
        chatSocket?.on("connect", onConnectedListener)
        chatSocket?.on("disconnect", onDisconnectedListener)
        chatSocket?.on("error", onErrorListener)
        chatSocket?.on("newMsg", onNewMessageListener)
        chatSocket?.on("msgStatusChanged", onMessageStatusChangeListener)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e(TAG, "onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.e(TAG, "onBind")
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.e(TAG, "onUnbind")
        chatCallback = null
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "onDestroy")
        chatSocket?.disconnect()
    }

    fun sendMessage(message: Message){
        Observable.just(chatDao).subscribeOn(Schedulers.io()).subscribe(Consumer {
            it.insert(message)
        })
        val jsonString = Gson().toJson(message)
        chatSocket?.emit("postMsg", jsonString)
    }

    fun updateMessageStatus(msgStatus: MsgStatus){
        chatSocket?.emit("updateMessageStatus", Gson().toJson(msgStatus))
        Observable.just(chatDao).subscribeOn(Schedulers.io()).subscribe(Consumer {
            it.updateMessageStatus(msgStatus.messageId!!, msgStatus.status!!)
        })
    }

    inner class ChatBinder : Binder() {
        fun getService(): ChatService {
            return this@ChatService
        }
    }

    private var onConnectedListener = Emitter.Listener() {
        Log.e(TAG, "onChatConnected")
        chatSocket?.emit("login", SessionManager.getUserId())
        chatCallback?.onChatConnected()
    }

    private var onDisconnectedListener = Emitter.Listener() {
        Log.e(TAG, "onChatDisConnected")
        chatSocket?.off()
        chatSocket = null
        chatCallback?.onChatDisconnected()
    }

    private var onErrorListener = Emitter.Listener() {
        Log.e(TAG, "onChatError")
        chatCallback?.onChatError()
    }

    private var onNewMessageListener = Emitter.Listener() {
        Log.e(TAG, "onNewMessage")
        Log.e(TAG, "onNewMessage : "+it[0].toString())
        val newMessage = Gson().fromJson(it[0].toString(), Message::class.java)
        newMessage.time = System.currentTimeMillis()
        newMessage.messageId = String.format("%d%d%d", newMessage.time, Math.min(newMessage.senderId!!, newMessage.receiverId!!), Math.max(newMessage.senderId!!, newMessage.receiverId!!)).toLong()
        newMessage.chatId = String.format("%d%d", Math.min(newMessage.senderId!!, newMessage.receiverId!!), Math.max(newMessage.senderId!!, newMessage.receiverId!!)).toLong()
        Observable.just(chatDao).subscribeOn(Schedulers.io()).subscribe(Consumer {
            it.insert(newMessage)
        })
        chatCallback?.onNewMessage(newMessage)

        val msgStatus = MsgStatus(newMessage.messageId, newMessage.senderId, newMessage.receiverId, Message.MessageStatus.DELIVERED.toString())
        updateMessageStatus(msgStatus)
    }

    private var onMessageStatusChangeListener = Emitter.Listener() {
        Log.e(TAG, "onMessageStatusChanged")
        Log.e(TAG, "onMessageStatusChanged : "+it[0].toString())
        val msgStatus = Gson().fromJson(it[0].toString(), MsgStatus::class.java)
        Observable.just(chatDao).subscribeOn(Schedulers.io()).subscribe(Consumer {
            it.updateMessageStatus(msgStatus.messageId!!, msgStatus.status!!)
        })
        chatCallback?.onMessageStatusChanged(msgStatus)
    }

    interface ChatServiceCallback{

        fun onChatConnected()

        fun onChatError()

        fun onChatDisconnected()

        fun onNewMessage(message: Message)

        fun onMessageStatusChanged(msgStatus: MsgStatus)
    }
}
