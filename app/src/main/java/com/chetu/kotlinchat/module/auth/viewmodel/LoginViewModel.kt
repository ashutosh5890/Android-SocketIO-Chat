package com.chetu.kotlinchat.module.auth.viewmodel

import android.databinding.Bindable
import android.view.View
import android.widget.Toast
import com.chetu.kotlinchat.AppController
import com.chetu.kotlinchat.BR
import com.chetu.kotlinchat.R
import com.chetu.kotlinchat.base.BaseViewModel
import com.chetu.kotlinchat.custom.helper.EventLiveData
import com.chetu.kotlinchat.datasource.api.response.AuthResponse
import com.chetu.kotlinchat.module.auth.model.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginViewModel : BaseViewModel() {

    var userRepository: UserRepository = UserRepository

    var username : String? = null
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.username)
        }

    var password : String? = null
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.password)
        }

    val loginSuccessEvent = EventLiveData<AuthResponse>()
    val loginFailedEvent = EventLiveData<String>()

    fun onLoginClick(view : View){
        Toast.makeText(view.context, "Clicked", Toast.LENGTH_SHORT).show()
        startProgressEvent.sendEvent("Loading..")
        if(isValidLogin()){
            disposable?.add(userRepository.authenticate(username, password).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onAuthResponse, this::handleError))
        }
    }

    fun isValidLogin() : Boolean{
        return true
    }

    private fun onAuthResponse(authResponse: AuthResponse){
        stopProgressEvent.sendEvent("")
        if(authResponse.authenticated){
            loginSuccessEvent?.sendEvent(authResponse)
        }else{
            loginFailedEvent.sendEvent(AppController.appContext.getString(R.string.error_authentication_failed))
        }
    }

    private fun handleError(error:Throwable){
        stopProgressEvent.sendEvent("")
        loginFailedEvent.sendEvent(AppController.appContext.getString(R.string.error_authentication_issue))
    }
}