package com.chetu.kotlinchat.module.home.viewmodel

import android.databinding.Bindable
import com.chetu.kotlinchat.BR
import com.chetu.kotlinchat.base.BaseViewModel

class ToolbarViewModel : BaseViewModel() {

    var title: String? = null
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.title)
        }
}