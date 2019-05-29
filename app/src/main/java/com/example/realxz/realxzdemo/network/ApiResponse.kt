package com.example.realxz.realxzdemo.network

/**
 * @author real xz
 * @date 2019/5/20
 */
class ApiResponse<T> {

    /**
     * api 响应状态  ok 标识成功
     */
    var status: String? = null
    /**
     * api 业务数据
     */
    var content: T? = null
    /**
     * api 响应错误码
     */
    var errorCode: String? = null
    /**
     * errorCode 对应错误信息
     */
    var errorMsg: String? = null

    fun isOK() = OK == status


    companion object {
        val OK = "ok"
        private val UNKNOWN_ERROR = "unknown_error"

        fun <T> unknownError(error: Throwable): ApiResponse<T> {
            val apiResponse = ApiResponse<T>()
            apiResponse.status = UNKNOWN_ERROR
            apiResponse.errorMsg = error.message
            return apiResponse
        }
    }
}