package com.chetu.kotlinchat.datasource.api

import com.chetu.kotlinchat.datasource.api.request.LoginRequest
import com.chetu.kotlinchat.datasource.api.response.AuthResponse
import com.chetu.kotlinchat.datasource.db.entity.Contact
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterface {

    @POST("/login")
    fun login(@Body loginRequest: LoginRequest) : Observable<AuthResponse>

    @GET("/users")
    fun getContacts(@Header("Authorization") accessToken : String) : Observable<List<Contact>>
}