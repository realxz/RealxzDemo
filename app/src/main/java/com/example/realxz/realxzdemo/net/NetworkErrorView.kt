package com.example.realxz.realxzdemo.net

import com.example.realxz.realxzdemo.base.BaseView
import com.example.realxz.realxzdemo.pojo.Task

/**
 * @author real xz
 * @date 2019/5/22
 */
interface NetworkErrorView : BaseView {
    fun showTasks(it: List<Task>)
    fun printErrorMsg(errorMsg: String)

}