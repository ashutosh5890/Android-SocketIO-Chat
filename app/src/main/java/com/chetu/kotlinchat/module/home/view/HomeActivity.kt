package com.chetu.kotlinchat.module.home.view

import android.arch.lifecycle.ViewModelProviders
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.databinding.DataBindingUtil
import android.os.IBinder
import android.support.design.widget.TabLayout
import android.util.Log
import android.view.Menu
import com.chetu.kotlinchat.R
import com.chetu.kotlinchat.base.BaseActivity
import com.chetu.kotlinchat.databinding.HomeActivityBinding
import com.chetu.kotlinchat.databinding.ToolbarBinding
import com.chetu.kotlinchat.module.call.view.CallListFragment
import com.chetu.kotlinchat.module.chat.view.ChatListFragment
import com.chetu.kotlinchat.module.chat.view.ChatService
import com.chetu.kotlinchat.module.contact.view.ContactListFragment
import com.chetu.kotlinchat.module.home.view.adapter.HomePagerAdapter
import com.chetu.kotlinchat.module.home.viewmodel.HomeViewModel
import com.chetu.kotlinchat.module.home.viewmodel.ToolbarViewModel

class HomeActivity : BaseActivity<HomeActivityBinding, HomeViewModel>(), ServiceConnection {

    val TAG = "HomeActivity"
    lateinit var toolbarViewModel: ToolbarViewModel

    override fun onCreateBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        toolbarViewModel = ViewModelProviders.of(this).get(ToolbarViewModel::class.java)
        binding.homeViewModel = viewModel
        binding.toolbarViewModel = toolbarViewModel
    }

    override fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        toolbarViewModel.title = "Home"
    }

    override fun initMembers() {
        super.initMembers()
        setupViewPager();
    }

    private fun setupViewPager() {
        val homePagerAdapter = HomePagerAdapter(supportFragmentManager)
        homePagerAdapter.addFragment(ChatListFragment.getInstance(), "Chats")
        homePagerAdapter.addFragment(CallListFragment.getInstance(), "Calls")
        homePagerAdapter.addFragment(ContactListFragment.getInstance(), "Contacts")
        binding.homePager.adapter = homePagerAdapter
        binding.homePager.offscreenPageLimit = 3
        binding.slidingTabs.setupWithViewPager(binding.homePager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    override fun onStart() {
        super.onStart()
        Log.e(TAG, "onStart")
        val serviceIntent = Intent(this, ChatService::class.java)
        bindService(serviceIntent, this, BIND_AUTO_CREATE)
        //startService(serviceIntent)
    }

    override fun onStop() {
        super.onStop()
        Log.e(TAG, "onStop")
        unbindService(this)
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        Log.e(TAG, "onServiceDisconnected")
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        Log.e(TAG, "onServiceConnected")
    }

}
