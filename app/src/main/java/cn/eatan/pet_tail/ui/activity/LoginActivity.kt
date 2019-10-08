package cn.eatan.pet_tail.ui.activity

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import cn.eatan.mvp.basemvp.BaseMvpActivity
import cn.eatan.pet_tail.R
import cn.eatan.pet_tail.mvp.contract.LoginContract
import cn.eatan.pet_tail.mvp.presenter.LoginPresenter
import cn.eatan.pet_tail.ui.fragment.PasswordLoginFragment
import cn.eatan.pet_tail.ui.fragment.PhoneLoginFragment



class LoginActivity : BaseMvpActivity<LoginContract.View, LoginContract.Presenter>()
    , PasswordLoginFragment.OnFragmentInteractionListener
    , PhoneLoginFragment.OnFragmentInteractionListener{

    lateinit var mFragmentManager: FragmentManager
    lateinit var mFragmentTransaction: FragmentTransaction
//    lateinit var mShowFragment: Fragment
    lateinit var mPasswordLoginFragment: PasswordLoginFragment
    lateinit var mPhoneFragment: PhoneLoginFragment

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initData() {
        mFragmentManager = supportFragmentManager
        mPasswordLoginFragment = PasswordLoginFragment()
        mPhoneFragment = PhoneLoginFragment()
    }

    override fun initView() {
        super.initView()
        mFragmentTransaction = mFragmentManager.beginTransaction()
        mFragmentTransaction.add(R.id.login_et_module, mPhoneFragment, "phoneLogin").commit()
    }

    override fun initListener() {

    }

    override fun onFragmentInteraction() {
        replaceFragment()
    }

    fun replaceFragment(){
        Log.d("aaa","aaa")
        var mShowFragment = getShowFragment()
        if (mShowFragment != null){
            mFragmentManager.beginTransaction().setCustomAnimations(R.anim.login_mode_in, R.anim.login_mode_exit).hide(mShowFragment).commit()
            if (mShowFragment is PhoneLoginFragment){
                if (mFragmentManager.findFragmentByTag("passwordLogin") == null){
                    mFragmentManager.beginTransaction().setCustomAnimations(R.anim.login_mode_in, R.anim.login_mode_exit).add(R.id.login_et_module, mPasswordLoginFragment, "passwordLogin").commit()
                }else{
                    mFragmentManager.beginTransaction().setCustomAnimations(R.anim.login_mode_in, R.anim.login_mode_exit).show(mPasswordLoginFragment).commit()
                }
            }else if (mShowFragment is PasswordLoginFragment){
                if (mFragmentManager.findFragmentByTag("phoneLogin") == null){
                    mFragmentManager.beginTransaction().setCustomAnimations(R.anim.login_mode_in, R.anim.login_mode_exit).add(R.id.login_et_module, mPhoneFragment, "phoneLogin").commit()
                }else{
                    mFragmentManager.beginTransaction().setCustomAnimations(R.anim.login_mode_in, R.anim.login_mode_exit).show(mPhoneFragment).commit()
                }
            }
        }
    }

    fun getShowFragment(): Fragment?{
        val fragments = mFragmentManager.fragments
        for (fragment in fragments) {
            if (fragment != null && fragment.isVisible)
                return fragment
        }
        return null
    }


    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun createPresenter(): LoginContract.Presenter {
        return LoginPresenter()
    }

}
