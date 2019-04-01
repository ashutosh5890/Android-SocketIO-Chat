package com.chetu.kotlinchat.datasource.api.response

import com.google.gson.annotations.SerializedName

class AuthResponse {

    @SerializedName("user_id")
    var userId : Int? = null

    var authenticated : Boolean = false

    @SerializedName("access_token")
    var accessToken : String? = null
}