package cn.eatan.pet_tail.ui.activity

import android.util.Log
import cn.eatan.pet_tail.R
import cn.eatan.pet_tail.mvp.contract.LoginContract
import cn.eatan.pet_tail.mvp.presenter.LoginPresenter
import cn.eatan.mvp.basemvp.BaseMvpActivity
import cn.eatan.utils.statusbarutil.StatusBarUtil

class LoginActivity : BaseMvpActivity<LoginContract.View, LoginContract.Presenter>() {

    override fun initData() {
        StatusBarUtil.transparencyBar(this, false)
    }

    override fun initView() {
        super.initView()

    }

    override fun createPresenter(): LoginContract.Presenter =
        LoginPresenter()

    override fun initListener() {

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    /**
     * lazy延迟加载
     */
    private val mDialog by lazy {

    }

    override fun getLayoutId(): Int {
        Log.d("login","getLayoutId")
        return R.layout.activity_login
    }


}
