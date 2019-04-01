package com.chetu.kotlinchat.base

import android.arch.lifecycle.Observer
import android.databinding.ViewDataBinding
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.chetu.kotlinchat.custom.helper.SessionManager
import com.chetu.kotlinchat.custom.util.DialogUtils

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel> : AppCompatActivity() {

    protected lateinit var binding : T
    protected lateinit var viewModel: V
    protected var sessionManager = SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateBinding()
        setupToolbar()
        initMembers()
    }

    protected abstract fun onCreateBinding()

    protected abstract fun setupToolbar()

    protected open fun initMembers(){
        viewModel.startProgressEvent.observe(this, Observer {
            DialogUtils.showProgressDialog(this, it!!)
        })

        viewModel.stopProgressEvent.observe(this, Observer {
            DialogUtils.hideProgressDialog()
        })
    }
}
