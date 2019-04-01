package com.chetu.kotlinchat.module.contact.viewmodel

import android.databinding.Bindable
import android.databinding.BindingAdapter
import android.view.View
import com.chetu.kotlinchat.BR
import com.chetu.kotlinchat.R
import com.chetu.kotlinchat.base.BaseViewModel
import com.chetu.kotlinchat.custom.helper.ActionLiveData
import com.chetu.kotlinchat.datasource.db.entity.Contact
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class ContactItemViewModel(var contact: Contact) : BaseViewModel() {

    var firstName: String? = contact.firstName
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.firstName)
        }

    var lastName: String? = contact.lastName
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.lastName)
        }

    var fullName: String? = contact.fullName
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.fullName)
        }

    var email: String? = contact.email
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.email)
        }

    var pic: String? = contact.pic
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.pic)
        }

    var contactClickAction = ActionLiveData<Contact>()

    fun onContactItemClick(v: View) {
        contactClickAction.sendAction(contact)
    }

    companion object {

        @BindingAdapter("profilePic")
        @JvmStatic
        fun setProfilePic(imageView: CircleImageView, imagePath: String?){
            Picasso.with(imageView.context).load(imagePath)
                    .placeholder(R.drawable.temp_profile1).into(imageView)
        }
    }
}