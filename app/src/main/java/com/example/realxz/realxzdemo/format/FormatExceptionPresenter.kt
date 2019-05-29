package com.example.realxz.realxzdemo.format

import com.example.realxz.realxzdemo.base.BasePresenter
import com.example.realxz.realxzdemo.network.ApiContainer
import com.example.realxz.realxzdemo.network.ApiResponse
import com.example.realxz.realxzdemo.network.rx.ApiObserver
import com.example.realxz.realxzdemo.network.rx.retryIo2MainTransformer
import com.example.realxz.realxzdemo.pojo.FakeTask

/**
 * @author real xz
 * @date 2019/5/23
 */
class FormatExceptionPresenter : BasePresenter<FormatExceptionView>() {
    fun loadTasks() {
        getView()?.showLoadingDialog()
        ApiContainer.instance.apiService
                .mockFormatErrorRequest("test")
                .flowable
                .compose(retryIo2MainTransformer())
                .doOnComplete { getView()?.dismissLoadingDialog() }
                .doOnError { getView()?.dismissLoadingDialog() }
                .`as`(getView()!!.bindAutoDisposable())
                .subscribe(object : ApiObserver<List<FakeTask>>() {
                    override fun onRealXzSuccess(response: List<FakeTask>) {
                    }

                    override fun onRealXzFail(errorResponse: ApiResponse<*>) {
                        super.onRealXzFail(errorResponse)
                        getView()?.printErrorMsg(errorResponse.errorMsg ?: "")
                    }
                })

    }

}