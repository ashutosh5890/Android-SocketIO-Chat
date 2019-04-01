package com.chetu.kotlinchat.module.chat.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.databinding.DataBindingUtil
import android.graphics.Rect
import android.os.IBinder
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.chetu.kotlinchat.R
import com.chetu.kotlinchat.base.BaseActivity
import com.chetu.kotlinchat.custom.helper.SessionManager
import com.chetu.kotlinchat.databinding.ChatActivityBinding
import com.chetu.kotlinchat.datasource.db.entity.Contact
import com.chetu.kotlinchat.module.chat.viewmodel.ChatViewModel
import com.chetu.kotlinchat.module.home.viewmodel.ToolbarViewModel
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getHeight
import android.view.*
import android.view.Gravity
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.widget.*


class ChatActivity : BaseActivity<ChatActivityBinding, ChatViewModel>(), ServiceConnection {

    val TAG = "ChatActivity"
    lateinit var toolbarViewModel: ToolbarViewModel
    var contact: Contact? = null
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: ChatAdapter
    var chatService: ChatService? = null
    var loginUserId = SessionManager.getUserId()

    private var isPopupVisible: Boolean = false
    private var isKeyBoardVisible: Boolean = false
    private var popupWindow: PopupWindow? = null
    private var popUp: PopupWindow? = null
    private var keypadHeight = 0

    override fun onCreateBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat)
        viewModel = ViewModelProviders.of(this).get(ChatViewModel::class.java)
        toolbarViewModel = ToolbarViewModel()
        binding.chatViewModel = viewModel
        binding.toolbarViewModel = toolbarViewModel
        setupUIElements()
    }

    override fun setupToolbar() {
        setSupportActionBar(binding.chatToolbar?.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbarViewModel.title
    }

    fun setupUIElements() {
        binding.root.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()

            Log.e(TAG, "Rect before =  : " + r)
            binding.root.getWindowVisibleDisplayFrame(r)
            Log.e(TAG, "Rect after =  : " + r)
            val screenHeight = binding.root.rootView.height
            Log.e(TAG, "Screen height =  : " + screenHeight)

            // r.bottom is the position above soft keypad or device button.
            // if keypad is shown, the r.bottom is smaller than that before.
            keypadHeight = screenHeight - binding.root.height
            Log.e(TAG, "Keypad height =  : " + keypadHeight)
            isKeyBoardVisible = keypadHeight > screenHeight * 0.15
            Log.e(TAG, "Checking keyboard....Visible =  : " + isKeyBoardVisible)

            val heightDiff = binding.root.rootView.height - (r.bottom - r.top)
            if (heightDiff > 100) {
                //enter your code here
                if (isPopupVisible) {
                    keepKeyboard()
                    isPopupVisible = false
                    popupWindow?.dismiss()
                }
            } else {
                //enter code for hid
            }
        }

        binding.edtMessage.setOnFocusChangeListener({ v, hasFocus ->

        })
    }

    override fun initMembers() {
        super.initMembers()
        contact = intent.getParcelableExtra("contact")
        viewModel.chatUserId = contact?.id
        toolbarViewModel.title = contact?.firstName
        layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        adapter = ChatAdapter(this)
        binding.rvChatList.itemAnimator = DefaultItemAnimator()
        binding.rvChatList.layoutManager = layoutManager
        binding.rvChatList.adapter = adapter

        viewModel.messageListLiveData.observe(this, Observer {
            adapter.setMessageList(it!!)
            binding.rvChatList.scrollToPosition(adapter.itemCount)
        })

        viewModel.sendMessageAction.observe(this, Observer {
            adapter.addNewMessage(it!!)
            binding.rvChatList.smoothScrollToPosition(adapter.itemCount)
            chatService?.sendMessage(it!!)
        })

        viewModel.attachmentAction.observe(this, Observer {
            openAttachmentView()
        })

        viewModel.newMessageEvent.observe(this, Observer {
            Log.e(TAG, "New Message Observed : " + it?.messageText)
            adapter.addNewMessage(it!!)
            binding.rvChatList.smoothScrollToPosition(adapter.itemCount)
        })

        viewModel.msgStatusChangedEvent.observe(this, Observer {
            adapter.updateMessageStatus(it!!)
        })
    }

    override fun onStart() {
        super.onStart()
        Log.e(TAG, "onStart")
        val serviceIntent = Intent(this, ChatService::class.java)
        bindService(serviceIntent, this, BIND_AUTO_CREATE)
        viewModel.loadConversation(loginUserId, contact?.id!!)
    }

    override fun onStop() {
        super.onStop()
        Log.e(TAG, "onStop")
        unbindService(this)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        Log.e(TAG, "onServiceDisconnected")
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        Log.e(TAG, "onServiceConnected")
        chatService = (service as ChatService.ChatBinder).getService()
        chatService?.chatCallback = viewModel
    }

    private fun keepKeyboard() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }

    private fun openAttachmentView() {
        if (!isPopupVisible) {
            if (isKeyBoardVisible) {
                showPopupKeyBoard();
            } else {
                showPopup();
            }
        } else {
            if (isKeyBoardVisible) {
                isPopupVisible = false;
                popupWindow?.dismiss();
            } else {
                isPopupVisible = false;
                popUp?.dismiss();
            }
        }
    }

    private fun showPopupKeyBoard() {
        isPopupVisible = true
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val customView = inflater.inflate(R.layout.layout_popup_keyboard, null)
        popupWindow = PopupWindow(
                customView,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        )

        //setSizeForSoftKeyboard()
        val llAtachView = customView.findViewById(R.id.ll_attach_option) as LinearLayout
        val param = llAtachView.layoutParams as RelativeLayout.LayoutParams
        param.height = keypadHeight
        popupWindow?.showAtLocation(binding.root, Gravity.BOTTOM, 0, 0)
    }

    private fun showPopup() {
        isPopupVisible = true
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.layout_popup_keyboard, null)

        popUp = PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        popUp?.showAtLocation(binding.root, Gravity.NO_GRAVITY, 0, binding.rlBottom.top - 800)

        popupView.setBackgroundResource(R.drawable.rounded_white_background)
        val param = popupView.layoutParams as FrameLayout.LayoutParams
        param.leftMargin = resources.getDimensionPixelSize(R.dimen.dp_10)
        param.rightMargin = resources.getDimensionPixelSize(R.dimen.dp_10)
        param.topMargin = resources.getDimensionPixelSize(R.dimen.dp_10)
        param.bottomMargin = resources.getDimensionPixelSize(R.dimen.dp_10)
    }

}
