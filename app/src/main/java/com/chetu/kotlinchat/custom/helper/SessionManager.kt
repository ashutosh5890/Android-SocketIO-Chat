package com.chetu.kotlinchat.custom.helper

import android.content.Context
import com.chetu.kotlinchat.AppController
import com.chetu.kotlinchat.custom.constant.Constant.Companion.SF_KEY_AUTH_TOKEN
import com.chetu.kotlinchat.custom.constant.Constant.Companion.SF_KEY_IS_AUTHENTICATED
import com.chetu.kotlinchat.custom.constant.Constant.Companion.SF_KEY_USER_ID
import com.chetu.kotlinchat.datasource.api.response.AuthResponse

object SessionManager {

    var prefManager : PrefManager = PrefManager

    fun isAuthenticated() : Boolean{
        return prefManager.getBoolean(SF_KEY_IS_AUTHENTICATED, false)
    }

    fun getAuthToken() : String?{
        return prefManager.getString(SF_KEY_AUTH_TOKEN, null)
    }

    fun getUserId() : Int{
        return prefManager.getInt(SF_KEY_USER_ID, 0)
    }

    fun saveLogin(authResponse: AuthResponse?) {
        prefManager.putBoolean(SF_KEY_IS_AUTHENTICATED, authResponse?.authenticated?:false)
        prefManager.putString(SF_KEY_AUTH_TOKEN, authResponse?.accessToken)
        prefManager.putInt(SF_KEY_USER_ID, authResponse?.userId!!)
    }
}