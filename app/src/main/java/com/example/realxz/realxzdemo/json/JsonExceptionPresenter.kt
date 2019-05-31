package com.example.realxz.realxzdemo.json

import com.example.realxz.realxzdemo.base.BasePresenter
import com.example.realxz.realxzdemo.network.ApiContainer
import com.example.realxz.realxzdemo.network.rx.ApiObserver
import com.example.realxz.realxzdemo.network.rx.observableTransformer
import com.example.realxz.realxzdemo.pojo.FakeTask

/**
 * @author real xz
 * @date 2019/5/23
 */

class JsonExceptionPresenter : BasePresenter<JsonExceptionView>() {
    fun loadTasks() {
        getView()?.showLoadingDialog()
        ApiContainer.instance.apiService
                .mockJsonErrorRequest("test")
                .compose(observableTransformer())
                .doOnComplete { getView()?.dismissLoadingDialog() }
                .doOnError { getView()?.dismissLoadingDialog() }
                .`as`(getView()!!.bindAutoDisposable())
                .subscribe(object : ApiObserver<FakeTask>() {
                    override fun onRealXzSuccess(response: FakeTask) {
                    }

                    override fun onRealXzError(errorMsg: String, exception: String) {
                        super.onRealXzError(errorMsg, exception)
                        getView()?.printErrorMsg(exception)
                    }
                })

    }
}