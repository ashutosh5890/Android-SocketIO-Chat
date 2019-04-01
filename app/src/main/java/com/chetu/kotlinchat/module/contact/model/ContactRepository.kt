package com.chetu.kotlinchat.module.contact.model

import android.util.Log
import com.chetu.kotlinchat.datasource.api.RemoteRepository
import com.chetu.kotlinchat.datasource.db.ChatDatabase
import com.chetu.kotlinchat.datasource.db.entity.Contact
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

object ContactRepository {

    const val TAG = "ContactRepository"

    var remoreRepository: RemoteRepository = RemoteRepository
    var contactDao = ChatDatabase.getInstance().getContactDao()

    fun loadContacts(accessToken: String): Observable<List<Contact>> {
        Log.e(TAG, "Loading all contacts")
        return Observable.concat(getLocalContacts()?.subscribeOn(Schedulers.io()),
                remoreRepository.getContacts(accessToken).doOnNext(Consumer {
                    Log.e(TAG, "Got contacts from API")
                    saveContacts(it)
                }).onErrorResumeNext(Function {
                    Observable.empty()
                }).subscribeOn(Schedulers.io()))
    }

    fun getLocalContacts(): Observable<List<Contact>>? {
        Log.e(TAG, "Getting local contacts")
        return Observable.fromCallable {
            val localContacts = contactDao.getAll()
            Log.e(TAG, "Local contacts "+localContacts.size)
            localContacts
        }
    }

    fun saveContacts(contactList: List<Contact>){
        Log.e(TAG, "Saving contacts to local "+contactList.size)
        for (contact :Contact in contactList){
            contactDao.insert(contact)
        }
    }
}