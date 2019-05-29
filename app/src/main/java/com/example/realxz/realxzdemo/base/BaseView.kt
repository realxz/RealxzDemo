package com.example.realxz.realxzdemo.base

import com.example.realxz.realxzdemo.view.STYLE_DEFAULT
import com.uber.autodispose.AutoDisposeConverter

/**
 * @author real xz
 * @date 2019/5/15
 */
interface BaseView {

    fun <T> bindAutoDisposable(): AutoDisposeConverter<T>

    fun handleErrorMsg(errorMsg: String)

    fun dismissLoadingDialog()

    fun showLoadingDialog(style: Int = STYLE_DEFAULT, content: String? = "")
}