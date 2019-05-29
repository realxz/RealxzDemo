package com.example.realxz.realxzdemo.network.exception

import java.lang.Exception

/**
 * @author real xz
 * @date 2019/5/20
 */

class ClientException(private val errorMessage: String?) : Exception(errorMessage)