package com.example.realxz.realxzdemo.format

import android.os.Bundle
import com.example.realxz.realxzdemo.R
import com.example.realxz.realxzdemo.base.BaseActivity
import kotlinx.android.synthetic.main.activity_format_exception.*

class FormatExceptionActivity : BaseActivity(), FormatExceptionView {
    lateinit var presenter: FormatExceptionPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_format_exception)

        presenter = FormatExceptionPresenter()
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
