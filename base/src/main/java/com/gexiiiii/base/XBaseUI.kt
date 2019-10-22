package com.gexiiiii.base

/**
 * author : Gexiiiii
 * date : 2019/10/22 9:51
 * description :
 */
interface XBaseUI {
    fun layoutResId(): Int
    fun initVariable()
    fun initViews()
    fun setListeners()
    fun onLazyLoad()
}