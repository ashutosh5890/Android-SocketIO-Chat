package com.chetu.kotlinchat.module.chat.view

import android.content.Context
import android.support.v4.util.ArrayMap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chetu.kotlinchat.custom.helper.SessionManager
import com.chetu.kotlinchat.databinding.ImageChatBinding
import com.chetu.kotlinchat.databinding.LeftChatBinding
import com.chetu.kotlinchat.databinding.RightChatBinding
import com.chetu.kotlinchat.databinding.TextChatBinding
import com.chetu.kotlinchat.datasource.db.entity.Message
import com.chetu.kotlinchat.datasource.db.entity.MsgStatus
import com.chetu.kotlinchat.module.chat.viewmodel.ChatItemViewModel

class ChatAdapter(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    val TYPE_LEFT_TEXT = 0
    val TYPE_RIGHT_TEXT = 1
    val TYPE_LEFT_IMAGE = 2
    val TYPE_RIGHT_IMAGE = 3

    var messageList = arrayListOf<Message>()
    var messageMap = ArrayMap<Long, Message>()
    var inflater = LayoutInflater.from(context)
    var loginUserId = SessionManager.getUserId()

    fun setMessageList(messages: List<Message>) {
        messageList.clear()
        messageMap.clear()
        for(message: Message in messages){
            messageList.add(message)
            messageMap.put(message.messageId, message)
        }
        notifyDataSetChanged()
    }

    fun addNewMessage(message: Message) {
        messageList.add(message)
        messageMap.put(message.messageId, message)
        notifyItemInserted(messageList.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_LEFT_TEXT -> LeftTextViewHolder(LeftChatBinding.inflate(inflater, parent, false))
            TYPE_RIGHT_TEXT -> RightTextViewHolder(RightChatBinding.inflate(inflater, parent, false))
            TYPE_LEFT_IMAGE -> LeftImageViewHolder(LeftChatBinding.inflate(inflater, parent, false))
            TYPE_RIGHT_IMAGE -> RightImageViewHolder(RightChatBinding.inflate(inflater, parent, false))
            else -> RightTextViewHolder(RightChatBinding.inflate(inflater, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (messageList[position].senderId == loginUserId) {
            true -> TYPE_RIGHT_TEXT
            else -> TYPE_LEFT_TEXT
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_LEFT_TEXT -> (holder as LeftTextViewHolder).bindView()
            TYPE_RIGHT_TEXT -> (holder as RightTextViewHolder).bindView()
            TYPE_LEFT_IMAGE -> (holder as LeftImageViewHolder).bindView()
            TYPE_RIGHT_IMAGE -> (holder as RightImageViewHolder).bindView()
        }
    }

    fun updateMessageStatus(msgStatus: MsgStatus){
        val message = messageMap[msgStatus.messageId]
        message?.status = msgStatus.status
        notifyItemChanged(messageList.indexOf(message))
    }

    inner class LeftTextViewHolder(binding: LeftChatBinding) : RecyclerView.ViewHolder(binding.root) {

        var textChatBinding = TextChatBinding.inflate(inflater)

        init {
            binding.rlLeftChat.addView(textChatBinding.root)
        }

        fun bindView() {
            textChatBinding.messageViewModel = textChatBinding.messageViewModel ?: ChatItemViewModel()
            textChatBinding.messageViewModel?.statusVisibility = View.INVISIBLE
            textChatBinding.messageViewModel?.message = messageList[adapterPosition]
        }
    }

    inner class RightTextViewHolder(binding: RightChatBinding) : RecyclerView.ViewHolder(binding.root) {

        var textChatBinding = TextChatBinding.inflate(inflater)

        init {
            binding.rlRightChat.addView(textChatBinding.root)
        }

        fun bindView() {
            textChatBinding.messageViewModel = textChatBinding.messageViewModel ?: ChatItemViewModel()
            textChatBinding.messageViewModel?.message = messageList[adapterPosition]
        }
    }

    inner class LeftImageViewHolder(binding: LeftChatBinding) : RecyclerView.ViewHolder(binding.root) {

        var imageChatBinding = ImageChatBinding.inflate(inflater)

        init {
            binding.rlLeftChat.addView(imageChatBinding.root)
        }

        fun bindView() {

        }
    }

    inner class RightImageViewHolder(binding: RightChatBinding) : RecyclerView.ViewHolder(binding.root) {

        var imageChatBinding = ImageChatBinding.inflate(inflater)

        init {
            binding.rlRightChat.addView(imageChatBinding.root)
        }

        fun bindView() {

        }
    }
}