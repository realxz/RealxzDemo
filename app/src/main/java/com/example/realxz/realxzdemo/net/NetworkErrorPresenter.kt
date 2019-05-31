package com.example.realxz.realxzdemo.net

import com.example.realxz.realxzdemo.base.BasePresenter
import com.example.realxz.realxzdemo.network.ApiContainer
import com.example.realxz.realxzdemo.network.rx.ApiSubscriber
import com.example.realxz.realxzdemo.network.rx.flowableTransformer
import com.example.realxz.realxzdemo.pojo.Task

/**
 * @author real xz
 * @date 2019/5/22
 */
class NetworkErrorPresenter : BasePresenter<NetworkErrorView>() {
    fun loadTasks() {
        getView()?.showLoadingDialog()
        ApiContainer.instance.apiService
                .loadRapTasks("test")
                .compose(flowableTransformer())
                .doOnComplete { getView()?.dismissLoadingDialog() }
                .doOnError { getView()?.dismissLoadingDialog() }
                .`as`(getView()!!.bindAutoDisposable())
                .subscribe(object : ApiSubscriber<List<Task>>() {
                    override fun onRealXzSuccess(response: List<Task>) {
                        response?.let {
                            getView()?.showTasks(it)
                        }
                    }

                    override fun onRealXzError(errorMsg: String, exception: String) {
                        super.onRealXzError(errorMsg, exception)
                        getView()?.printErrorMsg(exception)
                    }
                })
    }

}