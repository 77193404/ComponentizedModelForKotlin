package cn.eatan.mvp.basemvp

/**
 * 基本交互
 */
interface IView{

    /**
     * 显示加载动画
     */
    fun showLoading()

    /**
     * 隐藏加载动画
     */
    fun hideLoading()
}