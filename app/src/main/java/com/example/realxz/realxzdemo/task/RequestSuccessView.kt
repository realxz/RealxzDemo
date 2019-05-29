package com.example.realxz.realxzdemo.task

import com.example.realxz.realxzdemo.base.BaseView
import com.example.realxz.realxzdemo.pojo.Task

/**
 * @author real xz
 * @date 2019/5/22
 */

interface RequestSuccessView : BaseView {
    fun showTasks(it: List<Task>)

}
