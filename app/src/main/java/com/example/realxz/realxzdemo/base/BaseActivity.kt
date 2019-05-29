package com.example.realxz.realxzdemo.base

import android.app.ProgressDialog
import android.arch.lifecycle.Lifecycle
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.realxz.realxzdemo.view.progressDialog
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.AutoDisposeConverter
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

/**
 * @author real xz
 * @date 2019/5/17
 */
abstract class BaseActivity : AppCompatActivity(), BaseView {
    private var loadingDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun <T> bindAutoDisposable(): AutoDisposeConverter<T> {
        return AutoDispose.autoDisposable<T>(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY))
    }

    override fun handleErrorMsg(errorMsg: String) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
    }

    override fun dismissLoadingDialog() {
        if (loadingDialog != null && loadingDialog!!.isShowing) {
            loadingDialog!!.dismiss()
        }
        loadingDialog = null
    }


    override fun showLoadingDialog(style: Int, content: String?) {
        if (loadingDialog == null) {
            loadingDialog = progressDialog(this, style, content)
        }
        loadingDialog!!.show()
    }
}