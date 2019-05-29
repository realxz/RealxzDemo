package com.example.realxz.realxzdemo.task

import android.os.Bundle
import com.example.realxz.realxzdemo.R
import com.example.realxz.realxzdemo.base.BaseActivity
import com.example.realxz.realxzdemo.pojo.Task
import kotlinx.android.synthetic.main.activity_request_success.*

class RequestSuccessActivity : BaseActivity(), RequestSuccessView {

    lateinit var presenter: RequestSuccessPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_success)


        presenter = RequestSuccessPresenter()
        presenter.attachView(this)

        tv_send.setOnClickListener {
            presenter.loadTasks()
        }

    }

    override fun showTasks(it: List<Task>) {
        tv_response.text = it.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}
