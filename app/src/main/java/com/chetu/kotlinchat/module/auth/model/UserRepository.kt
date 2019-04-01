package com.chetu.kotlinchat.module.auth.model

import com.chetu.kotlinchat.datasource.api.RemoteRepository
import com.chetu.kotlinchat.datasource.api.request.LoginRequest
import com.chetu.kotlinchat.datasource.api.response.AuthResponse
import io.reactivex.Observable

object UserRepository {

    var remoreRepository: RemoteRepository = RemoteRepository

    fun authenticate(username: String?, password: String?): Observable<AuthResponse>{
        return remoreRepository.authenticate(LoginRequest(username, password))
    }

}