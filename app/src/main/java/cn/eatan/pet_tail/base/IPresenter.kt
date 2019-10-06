package cn.eatan.mvp.basemvp

interface IPresenter<in V: IView>{

    /**
     * 绑定View
     */
    fun attachView(mView: V)

    /**
     * 解除View
     */
    fun deachView()
}