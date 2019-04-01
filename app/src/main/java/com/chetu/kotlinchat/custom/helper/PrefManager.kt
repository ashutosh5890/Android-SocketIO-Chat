package com.chetu.kotlinchat.custom.helper

import android.content.Context
import android.content.SharedPreferences
import com.chetu.kotlinchat.AppController
import com.chetu.kotlinchat.custom.constant.Constant.Companion.SF_NAME

object PrefManager {

    var sharedPreferences :  SharedPreferences

    init {
        val context = AppController.appContext
        sharedPreferences = context.getSharedPreferences(SF_NAME, Context.MODE_PRIVATE);
    }

    fun getBoolean(key:String, default:Boolean) : Boolean{
        return sharedPreferences.getBoolean(key, default)
    }

    fun getInt(key:String, default:Int) : Int{
        return sharedPreferences.getInt(key, default)
    }

    fun getString(key:String, default:String?) : String?{
        return sharedPreferences.getString(key, default)
    }

    fun putBoolean(key:String, value:Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun putString(key:String, value:String?){
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun putInt(key:String, value:Int){
        sharedPreferences.edit().putInt(key, value).apply()
    }
}