package com.example.realxz.realxzdemo.retry

import android.os.Bundle
import com.example.realxz.realxzdemo.R
import com.example.realxz.realxzdemo.base.BaseActivity
import kotlinx.android.synthetic.main.activity_retry_case.*

class RetryCaseActivity : BaseActivity(), RetryCaseView {
    lateinit var presenter: RetryCasePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retry_case)

        presenter = RetryCasePresenter()
        presenter.attachView(this)

        tv_send.setOnClickListener {
            presenter.loadTasks()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}
