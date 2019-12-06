package com.gexiiiii.base

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.gexiiiii.base.widget.XLoadingDialog

/**
 * author : Gexiiiii
 * date : 2019/10/22 9:50
 * description :
 */
abstract class XBaseFragment : Fragment(), XView, XBaseUI {

    open val TAG = javaClass.simpleName
    /**
     * 是否已经加载视图
     */
    private var isViewPrepared = false
    /**
     * 是否已经加载数据
     */
    private var isDataLoaded: Boolean = false

    open var mLoadingDialog: XLoadingDialog? = null

    private var mHandler = Handler(Looper.getMainLooper())
    private var uiThreadId = Thread.currentThread().id

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initVariable()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutResId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewPrepared = true
        initViews()
        setListeners()
        initLoadView()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (!isViewPrepared) {
            return
        }
        if (isVisibleToUser) {
            if (!isDataLoaded) {
                onLazyLoad()
                isDataLoaded = true
                return
            }
        }
    }

    private fun initLoadView() {
        mLoadingDialog = XLoadingDialog.Builder(context)
            .setMessage("加载中...")
            .setLoadingCallback {
                showToast("请求超时")
            }
            .setShowTime(10000).build()
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
        if (Thread.currentThread().id == uiThreadId) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            return
        }
        mHandler.post { Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() }
    }
}