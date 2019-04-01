package com.chetu.kotlinchat.custom.helper

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.support.annotation.MainThread

class EventLiveData<T> : MutableLiveData<T>() {

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<T?>) {
        // Being strict about the observer numbers is up to you
        // I thought it made sense to only allow one to handle the event
        if (hasObservers()) {
            throw Throwable("Only one observer at a time may subscribe to a ActionLiveData")
        }

        super.observe(owner, Observer { data ->
            // We ignore any null values and early return
            if (data != null) {
                observer.onChanged(data)
                // We set the value to null straight after emitting the change to the observer
                value = null
            }
        })
    }

    @MainThread
    fun sendEvent(data: T) {
        value = data
    }

    fun postEvent(data: T) {
        super.postValue(data)
    }
}