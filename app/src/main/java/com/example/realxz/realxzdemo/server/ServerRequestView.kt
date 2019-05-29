package com.example.realxz.realxzdemo.server

import com.example.realxz.realxzdemo.base.BaseView

/**
 * @author real xz
 * @date 2019/5/22
 */
interface ServerRequestView :BaseView {
    fun printErrorMsg(exception: String)
}