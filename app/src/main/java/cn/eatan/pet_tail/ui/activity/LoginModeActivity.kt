package cn.eatan.pet_tail.ui.activity

import android.content.Intent
import android.graphics.Typeface
import android.view.View
import cn.eatan.pet_tail.R
import cn.eatan.pet_tail.mvp.contract.LoginModeContract
import cn.eatan.pet_tail.mvp.presenter.LoginModePresenter
import cn.eatan.mvp.basemvp.BaseMvpActivity
import cn.eatan.utils.statusbarutil.StatusBarUtil
import kotlinx.android.synthetic.main.activity_login_mode.*

class LoginModeActivity : BaseMvpActivity<LoginModeContract.View, LoginModeContract.Presenter>() {

    override fun initData() {
        StatusBarUtil.transparencyBar(this, false)
    }

    override fun initView() {
        super.initView()
        val typeFace = Typeface.createFromAsset(this.assets, "fonts/24num.ttf")
        app_name_tv.typeface = typeFace
    }

    override fun createPresenter(): LoginModeContract.Presenter =
        LoginModePresenter()

    override fun initListener() {
        mode_user_bt.setOnClickListener{
            startActivity(Intent(this@LoginModeActivity, LoginActivity::class.java))
        }
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
        return R.layout.activity_login_mode
    }


}
