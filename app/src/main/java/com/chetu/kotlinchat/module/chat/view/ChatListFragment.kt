package com.chetu.kotlinchat.module.chat.view


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chetu.kotlinchat.R
import com.chetu.kotlinchat.base.BaseFragment
import com.chetu.kotlinchat.databinding.ChatListFragmentBinding
import com.chetu.kotlinchat.datasource.db.entity.Contact
import com.chetu.kotlinchat.datasource.db.entity.RecentChat
import com.chetu.kotlinchat.module.chat.viewmodel.ChatListViewModel


class ChatListFragment : BaseFragment<ChatListFragmentBinding, ChatListViewModel>() {

    lateinit var recentChatAdapter: RecentChatAdapter
    lateinit var layoutManager: LinearLayoutManager

    companion object {
        fun getInstance(): ChatListFragment {
            return ChatListFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?): ChatListFragmentBinding {
        val binding = DataBindingUtil.inflate<ChatListFragmentBinding>(inflater,R.layout.fragment_chat_list, container, false)
        viewModel = ViewModelProviders.of(this).get(ChatListViewModel::class.java)
        binding.chatListViewModel = viewModel
        return binding
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadRecentChats()
    }

    override fun initMembers() {
        super.initMembers()
        recentChatAdapter = RecentChatAdapter(this, chatClickObserver)
        layoutManager = LinearLayoutManager(activity as Context)
        binding.rvRecentChat.layoutManager = layoutManager
        binding.rvRecentChat.addItemDecoration(DividerItemDecoration(activity as Context, layoutManager.orientation))
        binding.rvRecentChat.adapter = recentChatAdapter
        viewModel.recentChatLiveData.observe(this, Observer {
            recentChatAdapter.setRecentChat(it!!)
        })
    }

    var chatClickObserver = Observer<RecentChat?> {
        val chatIntent = Intent(activity, ChatActivity::class.java)
        val contact = Contact()
        contact.id = it?.id
        contact.firstName = it?.firstName
        contact.lastName = it?.lastName
        contact.fullName = it?.fullName
        contact.email = it?.email
        contact.pic = it?.pic
        chatIntent.putExtra("contact", contact)
        startActivity(chatIntent)
    }

}
