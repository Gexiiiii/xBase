package com.gexiiiii.base

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gexiiiii.base.widget.XLoadingDialog

/**
 * author : Gexiiiii
 * date : 2019/10/22 9:50
 * description :
 */
abstract class XBaseActivity : AppCompatActivity(), XView, XBaseUI {

    open var mLoadingDialog: XLoadingDialog? = null

    private var mHandler = Handler(Looper.getMainLooper())
    private var uiThreadId = Thread.currentThread().id

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId())
        initVariable()
        initViews()
        setListeners()
        onLazyLoad()
        initLoadView()
    }

    /**
     * LoadingDialog配置
     */
    private fun initLoadView() {
        mLoadingDialog = XLoadingDialog.Builder(this)
            .setMessage("加载中...")
            .setLoadingCallback {
                showToast("请求超时")
            }
            .setShowTime(10000)
            .build()
    }

    override fun showLoadingDialog() {
        if (Thread.currentThread().id == uiThreadId) {
            if (mLoadingDialog!!.isShowing) {
                return
            }
            mLoadingDialog!!.show()
            return
        }
        mHandler.post(Runnable {
            if (mLoadingDialog!!.isShowing) {
                return@Runnable
            }
            mLoadingDialog!!.show()
        })
    }

    override fun hideLoadingDialog() {
        if (Thread.currentThread().id == uiThreadId) {
            mLoadingDialog!!.dismiss()
            return
        }
        mHandler.post {
            mLoadingDialog!!.dismiss()
        }
    }

    override fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}