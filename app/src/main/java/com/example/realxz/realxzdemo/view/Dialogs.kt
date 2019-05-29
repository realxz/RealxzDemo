package com.example.realxz.realxzdemo.view

import android.app.Activity
import android.app.ProgressDialog

/**
 * @author real xz
 * @date 2019/5/15
 */
const val STYLE_DEFAULT = 0x00

private fun progressDialog(activity: Activity?, title: String?, content: String?, cancelable: Boolean, style: Int): ProgressDialog? {
    if (activity == null) {
        return null
    }
    var progressDialog = StandardLoadingDialog(activity)
    if (style == STYLE_DEFAULT) {
        progressDialog.apply {
            setTitle(title)
            setMessage(content)
            setCancelable(cancelable)
            setCanceledOnTouchOutside(false)
        }
    } else {
        progressDialog = StandardLoadingDialog(activity, style, content)
        progressDialog.setCancelable(cancelable)
        progressDialog.setCanceledOnTouchOutside(false)

    }
    return progressDialog
}

fun progressDialog(activity: Activity, title: String, content: String): ProgressDialog? {
    return progressDialog(activity, title, content, true, STYLE_DEFAULT)
}

fun progressDialog(activity: Activity): ProgressDialog? {
    return progressDialog(activity, "请稍候", "操作中...")
}

fun progressDialog(activity: Activity, cancelable: Boolean): ProgressDialog? {
    return progressDialog(activity, "请稍候", "操作中...", cancelable, STYLE_DEFAULT)
}

fun progressDialog(activity: Activity, style: Int): ProgressDialog? {
    return progressDialog(activity, style, null)
}

fun progressDialog(activity: Activity, style: Int, text: String?): ProgressDialog? {
    return progressDialog(activity, null, text, false, style)
}
