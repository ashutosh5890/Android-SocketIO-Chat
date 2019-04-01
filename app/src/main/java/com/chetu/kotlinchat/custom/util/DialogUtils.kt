package com.chetu.kotlinchat.custom.util

import android.app.Activity
import android.app.ProgressDialog
import android.content.DialogInterface
import android.support.v7.app.AlertDialog

class DialogUtils {

    companion object {
        //progress dialog...
        private var sProgressDialog: ProgressDialog? = null
        private var alertDialog: AlertDialog? = null
        /**
         *@date: 11/22/2018
         *@desc: show progress dialog..
         *@param activity as context for create dialog instance
         *@param message current message into the dialog
         *@return
         */
        fun showProgressDialog(activity: Activity, message: String) {
            if (sProgressDialog == null) {
                sProgressDialog = ProgressDialog.show(activity, null, message)
            }
        }

        /**
         *@date: 11/22/2018
         *@desc: hide progress dialog
         *@param
         *@return
         */
        fun hideProgressDialog() {
            if (isShowing()) {
                sProgressDialog?.dismiss()
                sProgressDialog = null
            }
        }

        /**
         *@date: 11/22/2018
         *@desc: To check if dialog is showing
         *@return true or false based on whether dialog showing or not
         */
        fun isShowing(): Boolean {
            return sProgressDialog != null && sProgressDialog!!.isShowing()
        }

        /**
         *@date: 12/05/2018
         *@desc: To show the progress dialog for finish activity with error message
         *@return
         */
        fun showDialogWithFinishActivity(activity: Activity, message: String) {
            if (alertDialog == null) {
                val alertDialogBuilder = AlertDialog.Builder(activity)
                alertDialogBuilder.setMessage(message)
                alertDialogBuilder.setCancelable(false)
                alertDialogBuilder.setPositiveButton(android.R.string.ok, object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        activity.finish()
                    }
                })

                alertDialog = alertDialogBuilder.create()
                alertDialog!!.setOnDismissListener(object : DialogInterface.OnDismissListener, DialogInterface.OnCancelListener {
                    override fun onCancel(dialog: DialogInterface?) {
                        alertDialog = null
                        //AppLogger.d("Alert Dialog Cancle listener " + alertDialog)
                    }
                    override fun onDismiss(dialog: DialogInterface?) {
                        alertDialog = null
                        //AppLogger.d("Alert Dialog Dismiss listener " + alertDialog)
                    }

                })
                alertDialog?.show()
            }
        }

        fun showDialogWithMessage(activity: Activity, message: String) {
            if (alertDialog == null) {
                val alertDialogBuilder = AlertDialog.Builder(activity)
                alertDialogBuilder.setMessage(message)
                alertDialogBuilder.setCancelable(false)
                alertDialogBuilder.setPositiveButton(android.R.string.ok, object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        dialog?.dismiss()
                        alertDialog = null
                    }
                })

                alertDialog = alertDialogBuilder.create()
                alertDialog?.show()
            }
        }
    }
}