package com.chetu.kotlinchat.module.chat.viewmodel

import android.databinding.Bindable
import android.databinding.BindingAdapter
import android.view.View
import com.chetu.kotlinchat.BR
import com.chetu.kotlinchat.R
import com.chetu.kotlinchat.base.BaseViewModel
import com.chetu.kotlinchat.custom.helper.ActionLiveData
import com.chetu.kotlinchat.datasource.db.entity.RecentChat
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class RecentChatItemViewModel : BaseViewModel() {

    var recentChat: RecentChat? = null
        set(value) {
            field = value
            pic = value?.pic
            fullName = value?.fullName
            lastMessage = value?.messageText
        }


    var pic: String? = null
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.pic)
        }

    var fullName: String? = null
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.fullName)
        }

    var lastMessage: String? = null
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.lastMessage)
        }

    var chatClickAction = ActionLiveData<RecentChat>()

    fun onRecentChatClick(v:View){
        chatClickAction.sendAction(recentChat!!)
    }

    companion object {

        @BindingAdapter("profilePic")
        @JvmStatic
        fun setProfilePic(circleImageView: CircleImageView, imagePath:String){
            Picasso.with(circleImageView.context).load(imagePath)
                    .placeholder(R.drawable.temp_profile1)
                    .into(circleImageView)
        }
    }
}