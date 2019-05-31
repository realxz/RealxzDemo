package com.example.realxz.realxzdemo.network.rx

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.realxz.realxzdemo.context.RealxzApplication
import com.example.realxz.realxzdemo.network.ApiResponse
import com.example.realxz.realxzdemo.network.exception.ExceptionCenter
import com.example.realxz.realxzdemo.network.exception.ServerException

/**
 * @author real xz
 * @date 2019-05-30
 */
interface IApiProcess<T> {
    fun onRealXzSuccess(response: T)


    fun onRealXzFail(errorResponse: ApiResponse<*>) {
        // 业务失败，默认 Toast，如果需要根据 ErrorCode 做特殊处理，可以复写这个方法

        //需不需要为后端兜底，业务失败但是没有返回 ErrorMessage
        val errorMsg = errorResponse.errorMsg

        errorMsg?.let {
            toastErrorMsg(errorMsg)
        }
    }

    fun onRealXzError(errorMsg: String, exception: String = "") {
        toastErrorMsg(errorMsg)
    }

    private fun toastErrorMsg(errorMsg: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(RealxzApplication.instance, errorMsg, Toast.LENGTH_SHORT).show()
        }
    }

    fun testError(e: Throwable) {
        // ServerException 和服务器约定的业务失败 status != "ok"
        if (e is ServerException) {
            ExceptionCenter.handleGlobalErrorCode(e.errorCode) //这一步放在这里合适不合适呢，如果不合适，还可以放在流程中的哪一步进行处理?
            onRealXzFail(e.apiResponse)
        } else {
            //其他，可能是
            //1. 网络相关异常 4xx,5xx
            //2. 上游自定义异常
            //3. 解析异常...
            val (errorMsg, exception) = ExceptionCenter.handleException(e)
            onRealXzError(errorMsg, exception)
        }
    }


}
