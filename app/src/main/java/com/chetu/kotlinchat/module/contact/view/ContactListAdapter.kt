package com.chetu.kotlinchat.module.contact.view

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chetu.kotlinchat.databinding.ContactItemBinding
import com.chetu.kotlinchat.datasource.db.entity.Contact
import com.chetu.kotlinchat.module.contact.viewmodel.ContactItemViewModel

class ContactListAdapter(var lifecycleOwner: LifecycleOwner, var observer: Observer<Contact?>) : RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>() {

    var contactList: ArrayList<Contact>
    var layoutInflater: LayoutInflater? =null

    init {
        contactList = arrayListOf()
    }

    fun setContacts(contacts : List<Contact>){
        contactList.clear()
        contactList.addAll(contacts)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, itemViewType: Int): ContactViewHolder {
        layoutInflater = layoutInflater?:LayoutInflater.from(parent.context)
        return ContactViewHolder(ContactItemBinding.inflate(layoutInflater!!, parent, false))
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bindView()
    }

    inner class ContactViewHolder(var binding: ContactItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(){
            val viewModel = ContactItemViewModel(contactList[adapterPosition])
            binding.contactItemViewModel = viewModel
            viewModel.contactClickAction.observe(lifecycleOwner, observer)
        }
    }
}