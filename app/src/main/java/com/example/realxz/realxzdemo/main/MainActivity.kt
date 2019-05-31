package com.example.realxz.realxzdemo.main

import android.content.Intent
import android.os.Bundle
import com.example.realxz.realxzdemo.R
import com.example.realxz.realxzdemo.base.BaseActivity
import com.example.realxz.realxzdemo.biz.BusinessFailActivity
import com.example.realxz.realxzdemo.client.ClientRequestErrorActivity
import com.example.realxz.realxzdemo.format.FormatExceptionActivity
import com.example.realxz.realxzdemo.json.JsonExceptionActivity
import com.example.realxz.realxzdemo.net.NetworkErrorActivity
import com.example.realxz.realxzdemo.retry.RetryCaseActivity
import com.example.realxz.realxzdemo.server.ServerRequestErrorActivity
import com.example.realxz.realxzdemo.task.RequestSuccessActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainView {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //网络请求成功
        realxz_request_success.setOnClickListener {
            val intent = Intent(this, RequestSuccessActivity::class.java)
            startActivity(intent)
        }

        //模拟断网
        realxz_network_error.setOnClickListener {
            val intent = Intent(this, NetworkErrorActivity::class.java)
            startActivity(intent)
        }

        //模拟 4xx 错误
        realxz_client_error.setOnClickListener {
            val intent = Intent(this, ClientRequestErrorActivity::class.java)
            startActivity(intent)
        }

        //模拟 5xx 错误
        realxz_server_error.setOnClickListener {
            val intent = Intent(this, ServerRequestErrorActivity::class.java)
            startActivity(intent)
        }

        //业务错误 response 中 status != "ok"
        realxz_business_error.setOnClickListener {
            val intent = Intent(this, BusinessFailActivity::class.java)
            startActivity(intent)
        }

        //json 相关异常
        realxz_json_error.setOnClickListener {
            val intent = Intent(this, JsonExceptionActivity::class.java)
            startActivity(intent)
        }

        //格式错误
        realxz_format_error.setOnClickListener {
            val intent = Intent(this, FormatExceptionActivity::class.java)
            startActivity(intent)
        }

        //模拟请求重试
        realxz_retry_error.setOnClickListener {
            val intent = Intent(this, RetryCaseActivity::class.java)
            startActivity(intent)
        }
    }
}
