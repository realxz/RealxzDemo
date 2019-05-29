package com.example.realxz.realxzdemo.server

import android.os.Bundle
import com.example.realxz.realxzdemo.R
import com.example.realxz.realxzdemo.base.BaseActivity
import kotlinx.android.synthetic.main.activity_server_request_error.*

class ServerRequestErrorActivity : BaseActivity(), ServerRequestView {
    lateinit var presenter: ServerRequestPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_server_request_error)

        presenter = ServerRequestPresenter()
        presenter.attachView(this)

        tv_send.setOnClickListener {
            presenter.loadTasks()
        }
    }

    override fun printErrorMsg(exception: String) {
        tv_response.text = exception
    }


    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}
