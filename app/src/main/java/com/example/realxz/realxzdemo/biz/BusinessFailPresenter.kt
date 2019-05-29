package com.example.realxz.realxzdemo.biz

import com.example.realxz.realxzdemo.base.BasePresenter
import com.example.realxz.realxzdemo.network.ApiContainer
import com.example.realxz.realxzdemo.network.ApiResponse
import com.example.realxz.realxzdemo.network.rx.ApiObserver
import com.example.realxz.realxzdemo.network.rx.retryIo2MainTransformer

/**
 * @author real xz
 * @date 2019/5/22
 */
class BusinessFailPresenter : BasePresenter<BusinessFailView>() {
    fun loadTasks() {
        getView()?.showLoadingDialog()
        ApiContainer.instance.apiService
                .mockBusinessErrorRequest()
                .flowable
                .compose(retryIo2MainTransformer())
                .doOnComplete { getView()?.dismissLoadingDialog() }
                .doOnError { getView()?.dismissLoadingDialog() }
                .`as`(getView()!!.bindAutoDisposable())
                .subscribe(object : ApiObserver<String>() {
                    override fun onRealXzSuccess(response: String) {

                    }

                    override fun onRealXzFail(errorResponse: ApiResponse<*>) {
                        super.onRealXzFail(errorResponse)
                        getView()?.printErrorMsg("Error Code is ${errorResponse.errorCode ?: "暂无"} ErrorMsg is ${errorResponse.errorMsg ?: ""}")
                    }
                })

    }

}