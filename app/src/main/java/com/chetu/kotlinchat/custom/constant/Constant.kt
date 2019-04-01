package com.chetu.kotlinchat.custom.constant

interface Constant {

    companion object {

        const val API_BASE_URL: String = "http://10.0.2.2:3000/";

        //Shared preferences
        const val SF_NAME: String = "MySharedPref";
        const val SF_KEY_IS_AUTHENTICATED: String = "is_authenticated";
        const val SF_KEY_AUTH_TOKEN: String = "auth_token";
        const val SF_KEY_USER_ID: String = "user_id";

    }

}