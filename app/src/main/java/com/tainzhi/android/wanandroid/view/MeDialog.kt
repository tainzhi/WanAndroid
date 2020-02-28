package com.tainzhi.android.wanandroid.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import com.tainzhi.android.wanandroid.R

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/2/28 下午8:53
 * @description:
 **/

class MeDialog : AppCompatDialogFragment() {
    companion object {
        fun newInstance() = MeDialog()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_me, container)
    }

}