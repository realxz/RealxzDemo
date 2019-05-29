package com.example.realxz.realxzdemo.base

import java.lang.ref.WeakReference

/**
 * @author real xz
 * @date 2019/5/17
 */
abstract class BasePresenter<V : BaseView>() {

    private var viewRef: WeakReference<V>? = null
    fun attachView(view: V) {
        viewRef = WeakReference(view)
    }

    fun detachView() {
        if (viewRef != null) {
            viewRef!!.clear()
            viewRef = null

        }
    }

    protected fun getView(): V? {
        return viewRef?.get()
    }
}

