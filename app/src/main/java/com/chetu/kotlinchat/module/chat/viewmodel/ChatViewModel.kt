package com.chetu.kotlinchat.module.chat.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.databinding.Bindable
import android.view.View
import android.widget.Toast
import com.android.databinding.library.baseAdapters.BR
import com.chetu.kotlinchat.base.BaseViewModel
import com.chetu.kotlinchat.custom.helper.ActionLiveData
import com.chetu.kotlinchat.custom.helper.EventLiveData
import com.chetu.kotlinchat.custom.helper.SessionManager
import com.chetu.kotlinchat.datasource.db.entity.Message
import com.chetu.kotlinchat.datasource.db.entity.MsgStatus
import com.chetu.kotlinchat.module.chat.model.ChatRepository
import com.chetu.kotlinchat.module.chat.view.ChatService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class ChatViewModel : BaseViewModel(), ChatService.ChatServiceCallback {

    var chatRepository = ChatRepository
    var messageListLiveData = MutableLiveData<List<Message>>()
    var sendMessageAction = ActionLiveData<Message>()
    var attachmentAction = ActionLiveData<String>()
    var newMessageEvent = EventLiveData<Message>()
    var msgStatusChangedEvent = EventLiveData<MsgStatus>()
    var chatUserId: Int? = null
    var loginUserId = SessionManager.getUserId()

    var messageText: String? = null
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.messageText)
        }

    fun onMessageSend(v: View){
        val newMessage = Message()
        newMessage.time = System.currentTimeMillis()
        newMessage.messageText = messageText
        newMessage.senderId = loginUserId
        newMessage.receiverId = chatUserId
        newMessage.messageId = String.format("%d%d%d", newMessage.time, Math.min(newMessage.senderId!!, newMessage.receiverId!!), Math.max(newMessage.senderId!!, newMessage.receiverId!!)).toLong()
        newMessage.chatId = String.format("%d%d", Math.min(newMessage.senderId!!, newMessage.receiverId!!), Math.max(newMessage.senderId!!, newMessage.receiverId!!)).toLong()
        newMessage.status = Message.MessageStatus.SENDING.toString()
        sendMessageAction.sendAction(newMessage)
        messageText = ""
    }

    fun onAttachmentClick(v: View){
        Toast.makeText(v.context, "Attachment", Toast.LENGTH_SHORT).show()
        attachmentAction.value = "Attachment"
    }

    fun loadConversation(userId:Int, otherUserId:Int){
        disposable?.add(chatRepository.loadConversation(userId, otherUserId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer {
                    messageListLiveData.value = it
                }))
    }

    override fun onChatConnected() {

    }

    override fun onChatError() {

    }

    override fun onChatDisconnected() {

    }

    override fun onNewMessage(message: Message) {
        newMessageEvent.postEvent(message)
    }

    override fun onMessageStatusChanged(msgStatus: MsgStatus) {
        msgStatusChangedEvent.postEvent(msgStatus)
    }
}