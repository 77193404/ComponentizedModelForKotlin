package cn.eatan.module_user.ui.activity

import android.content.Intent
import android.graphics.Typeface
import android.view.View
import cn.eatan.module_user.R
import cn.eatan.module_user.mvp.contract.LoginModeContract
import cn.eatan.module_user.mvp.presenter.LoginModePresenter
import cn.eatan.mvp.basemvp.BaseMvpActivity
import cn.eatan.utils.statusbarutil.StatusBarUtil
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.activity_login_mode.*
import org.jetbrains.anko.toast

@Route(path = "/user/login_mode_activity")
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

        mode_tourists_bt.setOnClickListener{
            toast("游客登录")
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
