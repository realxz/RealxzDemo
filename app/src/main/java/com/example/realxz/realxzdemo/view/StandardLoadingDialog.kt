package com.example.realxz.realxzdemo.view

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.example.realxz.realxzdemo.R
import com.example.realxz.realxzdemo.view.loading.Wave
import kotlinx.android.synthetic.main.new_progress_bar_text.*
import java.lang.Exception


/**
 * @author real xz
 * @date 2019/5/15
 */
class StandardLoadingDialog : ProgressDialog {
    companion object {
        const val STYLE_ONLY_BAR = 0x01
        const val STYLE_BAR_TEXT = 0x02
    }


    lateinit var wave: Wave
    var style = 0
    var content: String? = ""

    constructor(context: Context?) : super(
            context,
            R.style.StandardLoadingDialog
    )

    constructor(context: Context?, theme: Int) : super(context, theme)


    constructor(context: Context?, style: Int, content: String?) : super(context, R.style.StandardLoadingDialog) {
        this.style = style
        this.content = content
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        wave = Wave(4)
        wave.setBounds(0, 0, 60, 60)
        wave.color = ContextCompat.getColor(context, R.color.white)
        setContentView(R.layout.new_progress_only_bar)

        if (style == STYLE_BAR_TEXT) {
            setContentView(R.layout.new_progress_bar_text)
            wave.setBounds(0, 0, 40, 40)
            tv_progress_content.text = content ?: ""
        }

        iv_progress.setImageDrawable(wave)
    }

    override fun show() {
        try {
            super.show()
            window.setWindowAnimations(R.style.dialogWindowAnim)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        wave.start()
    }

    override fun dismiss() {
        try {
            super.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        wave.stop()
    }
}