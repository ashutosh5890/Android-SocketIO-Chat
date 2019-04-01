package com.chetu.kotlinchat.module.contact.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.chetu.kotlinchat.base.BaseViewModel
import com.chetu.kotlinchat.custom.helper.SessionManager
import com.chetu.kotlinchat.datasource.db.entity.Contact
import com.chetu.kotlinchat.module.contact.model.ContactRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ContactListViewModel : BaseViewModel() {

    val TAG = "ContactListViewModel"
    var contactRepository = ContactRepository
    var contactsLiveData : MutableLiveData<List<Contact>> = MutableLiveData()

    init {
        loadContacts()
    }

    private fun loadContacts(){
        disposable?.add(contactRepository.loadContacts(SessionManager.getAuthToken()!!).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onContactListLoaded, this::handleError))
    }

    private fun onContactListLoaded(contacts : List<Contact>){
        Log.e(TAG, "Contact loaded..")
        contactsLiveData.postValue(contacts)
    }

    private fun handleError(error:Throwable){
        stopProgressEvent.sendEvent("")
    }
}