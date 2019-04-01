package com.chetu.kotlinchat.datasource.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.chetu.kotlinchat.custom.constant.DbConstant.Companion.TABLE_CONTACT
import com.google.gson.annotations.SerializedName

@Entity(tableName = TABLE_CONTACT)
class Contact(): Parcelable{

    @PrimaryKey
    @SerializedName("_id")
    var id: Int? = null
    @ColumnInfo(name = "first_name") var firstName: String? = null
    @ColumnInfo(name = "last_name") var lastName: String? = null
    @ColumnInfo(name = "full_name") var fullName : String? = null
    var email: String? = null
    var pic: String? = null

    constructor(parcel: Parcel) : this(){
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        firstName = parcel.readString()
        lastName = parcel.readString()
        email = parcel.readString()
        fullName = parcel.readString()
        pic = parcel.readString()
    }

    companion object CREATOR : Parcelable.Creator<Contact> {
        override fun createFromParcel(parcel: Parcel): Contact {
            return Contact(parcel)
        }

        override fun newArray(size: Int): Array<Contact?> {
            return arrayOfNulls(size)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(email)
        parcel.writeString(fullName)
        parcel.writeString(pic)
    }

    override fun describeContents(): Int {
        return 0
    }

}