package com.gexiiiii.base

/**
 * author : Gexiiiii
 * date : 2019/10/22 9:51
 * description :
 */
interface XPresenter<T> {
    fun takeView(view: T)
    fun dropView()
}