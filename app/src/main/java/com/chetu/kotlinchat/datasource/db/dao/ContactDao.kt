package com.chetu.kotlinchat.datasource.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.chetu.kotlinchat.custom.constant.DbConstant.Companion.TABLE_CONTACT
import com.chetu.kotlinchat.datasource.db.entity.Contact
import io.reactivex.Observable

@Dao
interface ContactDao {

    @Query("SELECT * FROM $TABLE_CONTACT")
    fun getAll(): List<Contact>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(contact: Contact)

    @Update
    fun update(contact: Contact)

    @Delete
    fun delete(contact: Contact)

    @Delete
    fun delete(vararg contacts: Contact)
}