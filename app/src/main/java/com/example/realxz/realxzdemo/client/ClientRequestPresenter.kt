package com.example.realxz.realxzdemo.client

import com.example.realxz.realxzdemo.base.BasePresenter
import com.example.realxz.realxzdemo.network.ApiContainer
import com.example.realxz.realxzdemo.network.rx.ApiSubscriber
import com.example.realxz.realxzdemo.network.rx.flowableTransformer

/**
 * @author real xz
 * @date 2019/5/22
 */
class ClientRequestPresenter : BasePresenter<ClientRequestView>() {
    fun loadTasks() {
        getView()?.showLoadingDialog()
        ApiContainer.instance.apiService
                .mockClientErrorRequest()
                .compose(flowableTransformer())
                .doOnComplete { getView()?.dismissLoadingDialog() }
                .doOnError { getView()?.dismissLoadingDialog() }
                .`as`(getView()!!.bindAutoDisposable())
                .subscribe(object : ApiSubscriber<String>() {
                    override fun onRealXzSuccess(response: String) {

                    }

                    override fun onRealXzError(errorMsg: String, exception: String) {
                        super.onRealXzError(errorMsg, exception)
                        getView()?.printErrorMsg(exception)
                    }
                })
    }

}