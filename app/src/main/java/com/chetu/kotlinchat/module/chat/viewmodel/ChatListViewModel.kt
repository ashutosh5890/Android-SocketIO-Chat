package com.chetu.kotlinchat.module.chat.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.chetu.kotlinchat.base.BaseViewModel
import com.chetu.kotlinchat.custom.helper.SessionManager
import com.chetu.kotlinchat.datasource.db.entity.RecentChat
import com.chetu.kotlinchat.module.chat.model.ChatRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ChatListViewModel: BaseViewModel() {

    val TAG = "ChatListViewModel"
    var chatRepository = ChatRepository
    var recentChatLiveData = MutableLiveData<List<RecentChat>>()
    var loginUserId = SessionManager.getUserId()


    fun loadRecentChats(){
        disposable?.add(chatRepository.loadRecentChats(loginUserId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRecentChatLoaded, this::handleError))
    }

    private fun onRecentChatLoaded(recentChats : List<RecentChat>){
        Log.e(TAG, "Recent Chats loaded.."+recentChats.size)
        recentChatLiveData.postValue(recentChats)
    }

    private fun handleError(error:Throwable){
        stopProgressEvent.sendEvent("")
        Log.e(TAG, "Recent Chats error : "+error.localizedMessage+" "+error.message)

    }
}