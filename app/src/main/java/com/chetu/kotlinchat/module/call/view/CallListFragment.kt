package com.chetu.kotlinchat.module.call.view


import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chetu.kotlinchat.R
import com.chetu.kotlinchat.base.BaseFragment
import com.chetu.kotlinchat.databinding.CallListFragmentBinding
import com.chetu.kotlinchat.module.call.viewmodel.CallListViewModel


class CallListFragment : BaseFragment<CallListFragmentBinding, CallListViewModel>() {

    companion object {
        fun getInstance(): CallListFragment {
            return CallListFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?): CallListFragmentBinding {
        val binding = DataBindingUtil.inflate<CallListFragmentBinding>(inflater,R.layout.fragment_call_list, container, false)
        viewModel = ViewModelProviders.of(this).get(CallListViewModel::class.java)
        binding.callListViewModel = viewModel
        return binding
    }

}
