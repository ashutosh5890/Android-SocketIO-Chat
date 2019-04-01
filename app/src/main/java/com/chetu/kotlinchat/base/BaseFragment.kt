package com.chetu.kotlinchat.base


import android.app.Activity
import android.arch.lifecycle.Observer
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chetu.kotlinchat.custom.helper.SessionManager
import com.chetu.kotlinchat.custom.util.DialogUtils


abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel> : Fragment() {

    protected lateinit var binding : T
    protected lateinit var viewModel: V
    protected var sessionManager = SessionManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = onCreateBinding(inflater, container)
        initMembers()
        return binding.root
    }

    protected abstract fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?) : T

    protected open fun initMembers(){
        viewModel.startProgressEvent.observe(this, Observer {
            DialogUtils.showProgressDialog(activity as Activity, it!!)
        })

        viewModel.stopProgressEvent.observe(this, Observer {
            DialogUtils.hideProgressDialog()
        })
    }


}
