package com.chetu.kotlinchat.base

import android.arch.lifecycle.ViewModel
import android.arch.persistence.room.Room
import android.content.Context
import android.databinding.Observable
import android.databinding.PropertyChangeRegistry
import com.chetu.kotlinchat.AppController
import com.chetu.kotlinchat.custom.constant.DbConstant.Companion.CHAT_DATABASE
import com.chetu.kotlinchat.custom.helper.EventLiveData
import com.chetu.kotlinchat.datasource.db.ChatDatabase
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel(), Observable {

    private val callbacks: PropertyChangeRegistry = PropertyChangeRegistry()
    protected var disposable: CompositeDisposable? = null
    val startProgressEvent = EventLiveData<String>()
    val stopProgressEvent = EventLiveData<String>()

    init {
        disposable = CompositeDisposable()
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }

    /**
     * Notifies observers that all properties of this instance have changed.
     */
    fun notifyChange() {
        callbacks.notifyCallbacks(this, 0, null)
    }

    /**
     * Notifies observers that a specific property has changed. The getter for the
     * property that changes should be marked with the @Bindable annotation to
     * generate a field in the BR class to be used as the fieldId parameter.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    fun notifyPropertyChanged(fieldId: Int) {
        callbacks.notifyCallbacks(this, fieldId, null)
    }

}