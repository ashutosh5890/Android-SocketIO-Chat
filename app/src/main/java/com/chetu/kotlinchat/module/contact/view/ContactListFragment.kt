package com.chetu.kotlinchat.module.contact.view


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.ViewGroup

import com.chetu.kotlinchat.R
import com.chetu.kotlinchat.base.BaseFragment
import com.chetu.kotlinchat.databinding.ContactListFragmentBinding
import com.chetu.kotlinchat.datasource.db.entity.Contact
import com.chetu.kotlinchat.module.chat.view.ChatActivity
import com.chetu.kotlinchat.module.contact.viewmodel.ContactListViewModel


class ContactListFragment : BaseFragment<ContactListFragmentBinding, ContactListViewModel>() {

    lateinit var contactListAdapter : ContactListAdapter
    lateinit var layoutManager: LinearLayoutManager

    companion object {
        fun getInstance(): ContactListFragment{
            return ContactListFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?): ContactListFragmentBinding {
        val binding = DataBindingUtil.inflate<ContactListFragmentBinding>(inflater,R.layout.fragment_contact, container, false)
        viewModel = ViewModelProviders.of(this).get(ContactListViewModel::class.java)
        binding.contactViewModel = viewModel
        return binding
    }

    override fun initMembers() {
        super.initMembers()
        contactListAdapter = ContactListAdapter(this, contactClickObserver)
        layoutManager = LinearLayoutManager(activity as Context)
        binding.rvContactList.layoutManager = layoutManager
        binding.rvContactList.addItemDecoration(DividerItemDecoration(activity as Context, layoutManager.orientation))
        binding.rvContactList.adapter = contactListAdapter
        viewModel.contactsLiveData.observe(this, Observer {
            contactListAdapter.setContacts(it!!)
        })
    }

    var contactClickObserver = Observer<Contact?> {
        val chatIntent = Intent(activity, ChatActivity::class.java)
        chatIntent.putExtra("contact", it)
        startActivity(chatIntent)
    }
}
