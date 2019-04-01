package com.chetu.kotlinchat.module.chat.view

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.content.Context
import android.support.v4.util.ArrayMap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chetu.kotlinchat.custom.helper.SessionManager
import com.chetu.kotlinchat.databinding.RecentChatItemBinding
import com.chetu.kotlinchat.datasource.db.entity.Contact
import com.chetu.kotlinchat.datasource.db.entity.RecentChat
import com.chetu.kotlinchat.module.chat.viewmodel.RecentChatItemViewModel

class RecentChatAdapter(var lifecycleOwner: LifecycleOwner, var observer: Observer<RecentChat?>) : RecyclerView.Adapter<RecentChatAdapter.RecentChatViewHolder>() {

    var recentChatList = arrayListOf<RecentChat>()
    var recentChatMap = ArrayMap<Int, RecentChat>()
    var layoutInflater: LayoutInflater? =null
    var loginUserId = SessionManager.getUserId()

    fun setRecentChat(contacts : List<RecentChat>){
        recentChatList.clear()
        recentChatList.addAll(contacts)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentChatViewHolder {
        layoutInflater = layoutInflater?: LayoutInflater.from(parent.context)
        return RecentChatViewHolder(RecentChatItemBinding.inflate(layoutInflater!!, parent, false))
    }

    override fun getItemCount(): Int {
        return recentChatList.size
    }

    override fun onBindViewHolder(holder: RecentChatViewHolder, position: Int) {
        holder.bindView()
    }

    inner class RecentChatViewHolder(var binding: RecentChatItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(){
            val viewModel = binding.recentChatItemViewModel?: RecentChatItemViewModel()
            viewModel.recentChat = recentChatList[adapterPosition]
            viewModel.chatClickAction.removeObservers(lifecycleOwner)
            viewModel.chatClickAction.observe(lifecycleOwner, observer)
            binding.recentChatItemViewModel = viewModel
        }
    }
}