package com.example.realxz.realxzdemo.client

import android.os.Bundle
import com.example.realxz.realxzdemo.R
import com.example.realxz.realxzdemo.base.BaseActivity
import com.example.realxz.realxzdemo.pojo.Task
import kotlinx.android.synthetic.main.activity_network_error.*

class ClientRequestErrorActivity : BaseActivity(), ClientRequestView {
    lateinit var presenter: ClientRequestPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_request_error)

        presenter = ClientRequestPresenter()
        presenter.attachView(this)

        tv_send.setOnClickListener {
            presenter.loadTasks()
        }
    }

    override fun showTasks(it: List<Task>) {
        tv_response.text = it.toString()
    }

    override fun printErrorMsg(errorMsg: String) {
        tv_response.text = errorMsg
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}
