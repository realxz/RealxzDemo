package com.example.realxz.realxzdemo.retry

import com.example.realxz.realxzdemo.base.BasePresenter
import com.example.realxz.realxzdemo.network.ApiContainer
import com.example.realxz.realxzdemo.network.rx.ApiSubscriber
import com.example.realxz.realxzdemo.network.rx.flowableTransformer
import com.example.realxz.realxzdemo.pojo.Task

/**
 * @author real xz
 * @date 2019/5/23
 */
class RetryCasePresenter : BasePresenter<RetryCaseView>() {
    fun loadTasks() {
        getView()?.showLoadingDialog()
        ApiContainer.instance.apiService
                .loadRapTasks(null)
                .compose(flowableTransformer())
                .doOnComplete { getView()?.dismissLoadingDialog() }
                .doOnError { getView()?.dismissLoadingDialog() }
                .`as`(getView()!!.bindAutoDisposable())
                .subscribe(object : ApiSubscriber<List<Task>>() {
                    override fun onRealXzSuccess(response: List<Task>) {
                    }
                })
    }
}