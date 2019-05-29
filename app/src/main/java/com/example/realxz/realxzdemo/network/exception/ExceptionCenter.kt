package com.example.realxz.realxzdemo.network.exception

import com.alibaba.fastjson.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @author real xz
 * @date 2019/5/21
 */

class ExceptionCenter {
    companion object {
        fun handleGlobalErrorCode(errorCode: String?) {

        }

        fun handleException(e: Throwable): Pair<String, String> {
            var errorMsg: String = e.message ?: "未知错误 - ExceptionCenter"
            when (e) {
                is HttpException -> errorMsg = httpCodeExplain(e.code())
                is SocketTimeoutException -> errorMsg = "网络异常，请稍后再试！"
                is ConnectException -> errorMsg = "网络异常，请稍后再试！"
                is UnknownHostException -> errorMsg = "网络异常，请稍后再试！"
                is ClientException -> errorMsg = e.message ?: "客户端未知错误 - ClientException"
                is JSONException -> errorMsg = "数据解析异常"
                is NumberFormatException -> errorMsg = "数据转换异常"
            }
            return Pair(errorMsg, e.message ?: "未知错误 - ExceptionCenter")
        }

        private fun httpCodeExplain(code: Int) =
                when (code) {
                    BAD_REQUEST -> {
                        "网络异常，请稍后再试！"
                    }
                    UNAUTHORIZED -> {
                        "网络异常，请稍后再试！"
                    }
                    FORBIDDEN -> {
                        "网络异常，请稍后再试！"
                    }
                    NOT_FOUND -> {
                        "网络异常，请稍后再试！"
                    }

                    METHOD_NOT_ALLOWED -> {
                        "网络异常，请稍后再试！"
                    }
                    INTERNAL_SERVER_ERROR -> {
                        "网络异常，请稍后再试！"
                    }
                    BAD_GATEWAY -> {
                        "网络异常，请稍后再试！"
                    }
                    SERVICE_UNAVAILABLE -> {
                        "网络异常，请稍后再试！"
                    }
                    GATEWAY_TIMEOUT -> {
                        "网络异常，请稍后再试！"
                    }
                    else -> {
                        "网络异常，请稍后再试！"
                    }
                }

    }
}
