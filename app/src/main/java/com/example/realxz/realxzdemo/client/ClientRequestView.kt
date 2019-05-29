package com.example.realxz.realxzdemo.client

import com.example.realxz.realxzdemo.base.BaseView
import com.example.realxz.realxzdemo.pojo.Task

/**
 * @author real xz
 * @date 2019/5/22
 */
interface ClientRequestView : BaseView {
    fun showTasks(it: List<Task>): Any
    fun printErrorMsg(errorMsg: String)
}