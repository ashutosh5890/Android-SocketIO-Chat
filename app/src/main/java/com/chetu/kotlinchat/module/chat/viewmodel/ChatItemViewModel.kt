package com.chetu.kotlinchat.module.chat.viewmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.support.annotation.DrawableRes
import android.view.View
import com.android.databinding.library.baseAdapters.BR
import com.chetu.kotlinchat.R
import com.chetu.kotlinchat.custom.util.DateUtils
import com.chetu.kotlinchat.datasource.db.entity.Message


class ChatItemViewModel : BaseObservable() {

    var message: Message? = null
        set(value) {
            field = value
            messageText = value?.messageText
            statusIcon = value?.messageStatus?.icon?: R.drawable.ic_sending
            messageTime = DateUtils.convertTimeToHHMM(value?.time)
        }

    var messageText: String? = null
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.messageText)
        }

    @DrawableRes
    var statusIcon: Int? = R.drawable.ic_sending
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.statusIcon)
        }

    var messageTime:String? = null
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.messageTime)
        }

    var statusVisibility: Int = View.VISIBLE
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.statusVisibility)
        }
}