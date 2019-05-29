package com.example.realxz.realxzdemo.task

import com.example.realxz.realxzdemo.base.BasePresenter
import com.example.realxz.realxzdemo.network.ApiContainer
import com.example.realxz.realxzdemo.network.rx.ApiObserver
import com.example.realxz.realxzdemo.network.rx.retryIo2MainTransformer
import com.example.realxz.realxzdemo.pojo.Task

/**
 * @author real xz
 * @date 2019/5/22
 */
class RequestSuccessPresenter : BasePresenter<RequestSuccessView>() {

    fun loadTasks() {
        getView()?.showLoadingDialog()
        ApiContainer.instance.apiService
                .loadRapTasks("test")
                .flowable
                .compose(retryIo2MainTransformer())
                .doOnComplete { getView()?.dismissLoadingDialog() }
                .doOnError { getView()?.dismissLoadingDialog() }
                .`as`(getView()!!.bindAutoDisposable())
                .subscribe(object : ApiObserver<List<Task>>() {
                    override fun onRealXzSuccess(response: List<Task>) {
                        getView()?.showTasks(response)
                    }
                })
    }
}