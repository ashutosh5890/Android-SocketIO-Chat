package com.chetu.kotlinchat.module.auth.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import com.chetu.kotlinchat.R
import com.chetu.kotlinchat.base.BaseActivity
import com.chetu.kotlinchat.custom.util.DialogUtils
import com.chetu.kotlinchat.databinding.LoginActivityBinding
import com.chetu.kotlinchat.module.auth.viewmodel.LoginViewModel
import com.chetu.kotlinchat.module.home.view.HomeActivity

class LoginActivity : BaseActivity<LoginActivityBinding, LoginViewModel>() {

    override fun onCreateBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        binding.loginViewModel = viewModel
    }

    override fun setupToolbar() {}

    override fun initMembers() {
        super.initMembers()
        checkLogin()
        viewModel.loginSuccessEvent.observe(this, Observer {
            sessionManager.saveLogin(it)
            val homeIntent = Intent(this, HomeActivity::class.java)
            startActivity(homeIntent)
            finish()
        })
        viewModel.loginFailedEvent.observe(this, Observer {
            DialogUtils.showDialogWithMessage(this, it!!);
        })
    }

    private fun checkLogin() {
        if(sessionManager.isAuthenticated()){
            val homeIntent = Intent(this, HomeActivity::class.java)
            startActivity(homeIntent)
            finish()
        }
    }
}
