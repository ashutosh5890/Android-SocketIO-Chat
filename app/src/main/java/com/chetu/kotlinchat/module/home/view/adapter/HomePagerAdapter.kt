package com.chetu.kotlinchat.module.home.view.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.widget.DialogTitle
import android.util.Log

class HomePagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

    var tabTitles = arrayListOf<String>()
    var fragments = arrayListOf<Fragment>()

    fun addFragment(fragment: Fragment, title: String){
        fragments.add(fragment)
        tabTitles.add(title)
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        Log.e("TAB", tabTitles[position])
        return tabTitles[position]
    }
}