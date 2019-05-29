package com.example.realxz.realxzdemo.json

import android.os.Bundle
import com.example.realxz.realxzdemo.R
import com.example.realxz.realxzdemo.base.BaseActivity
import kotlinx.android.synthetic.main.activity_json_exception.*

class JsonExceptionActivity : BaseActivity(), JsonExceptionView {
    lateinit var presenter: JsonExceptionPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_json_exception)

        presenter = JsonExceptionPresenter()
        presenter.attachView(this)

        tv_send.setOnClickListener {
            presenter.loadTasks()
        }
    }

    override fun printErrorMsg(s: String) {
        tv_response.text = s
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}
