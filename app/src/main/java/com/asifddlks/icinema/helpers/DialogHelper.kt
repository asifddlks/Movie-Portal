package com.asifddlks.icinema.helpers

import android.app.Dialog
import android.content.Context
import com.asifddlks.icinema.R

class DialogHelper(val context: Context) {

    fun showLoaderDialog(): Dialog {
        var dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_progress_loader)
        dialog.setCancelable(false)
        //dialog.show()
        return dialog
    }


    interface DialogButtonAction {
        fun accept()
        fun decline()
    }

}