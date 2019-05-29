package com.example.realxz.realxzdemo.biz

import android.os.Bundle
import com.example.realxz.realxzdemo.R
import com.example.realxz.realxzdemo.base.BaseActivity
import kotlinx.android.synthetic.main.activity_business_fail.*

class BusinessFailActivity : BaseActivity(), BusinessFailView {
    lateinit var presenter: BusinessFailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_fail)

        presenter = BusinessFailPresenter()
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
