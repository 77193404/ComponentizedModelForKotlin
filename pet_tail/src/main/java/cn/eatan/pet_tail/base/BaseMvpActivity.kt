package cn.eatan.mvp.basemvp

import cn.eatan.mvp.base.BaseActivity

@Suppress("UNCHECKED_CAST")
abstract class BaseMvpActivity<in V: IView, P: IPresenter<V>>: BaseActivity(), IView {

    protected var mPresenter: P? = null

    protected abstract fun createPresenter(): P

    override fun initView() {
        mPresenter = createPresenter()
        mPresenter?.attachView(this as V)
    }


    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.deachView()
        this.mPresenter = null
    }
}