package com.example.realxz.realxzdemo.biz

import com.example.realxz.realxzdemo.base.BaseView

/**
 * @author real xz
 * @date 2019/5/22
 */
interface BusinessFailView : BaseView {
    fun printErrorMsg(exception: String)

}