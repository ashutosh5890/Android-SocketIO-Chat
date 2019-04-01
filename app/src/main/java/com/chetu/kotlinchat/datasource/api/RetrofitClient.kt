package com.chetu.kotlinchat.datasource.api

import com.chetu.kotlinchat.custom.constant.Constant.Companion.API_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    lateinit var apiInterface: ApiInterface

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        val httpClient = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(loggingInterceptor)
        val retrofitClient = retrofit.client(httpClient.build()).build()
        apiInterface = retrofitClient.create(ApiInterface::class.java)
    }

}