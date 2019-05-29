package com.example.realxz.realxzdemo.network.exception

import com.example.realxz.realxzdemo.network.ApiResponse

/**
 * @author real xz
 * @date 2019/5/21
 */
class ServerException(val errorCode: String?,
                      val errorMessage: String?,
                      val apiResponse: ApiResponse<*>) : Exception(errorMessage ?: "服务端未知错误 - ServerException")