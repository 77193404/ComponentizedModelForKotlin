package cn.eatan.mvp.base

abstract class BasePresenter<V: cn.eatan.mvp.basemvp.IView>: cn.eatan.mvp.basemvp.IPresenter<V> {

    protected var mView: V? = null


    override fun attachView(mView: V) {
        this.mView = mView
    }

    override fun deachView() {
        this.mView = null
    }

}