package com.chetu.kotlinchat.datasource.api

import com.chetu.kotlinchat.datasource.api.request.LoginRequest
import com.chetu.kotlinchat.datasource.api.response.AuthResponse
import com.chetu.kotlinchat.datasource.db.entity.Contact
import io.reactivex.Observable

object RemoteRepository {

    var apiInterface: ApiInterface

    init {
        apiInterface = RetrofitClient.apiInterface
    }

    fun authenticate(loginRequest: LoginRequest) : Observable<AuthResponse>{
        return apiInterface.login(loginRequest)
    }

    fun getContacts(accessToken: String) : Observable<List<Contact>>{
        return apiInterface.getContacts(accessToken)
    }
}