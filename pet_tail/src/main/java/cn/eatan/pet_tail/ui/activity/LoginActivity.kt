package cn.eatan.pet_tail.ui.activity

import android.os.Bundle
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

    private lateinit var mFragmentManager: FragmentManager
    private lateinit var mFragmentTransaction: FragmentTransaction
//    lateinit var mShowFragment: Fragment
    private lateinit var mPasswordLoginFragment: PasswordLoginFragment
    private lateinit var mPhoneFragment: PhoneLoginFragment
    val PHONE_LOGIN_TAG = "phone_login_tag"
    val PASSWORD_LOGIN_TAG = "password_login_tag"
    var fragmentType = PHONE_LOGIN_TAG

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initData() {
        mFragmentManager = supportFragmentManager
        mPasswordLoginFragment = PasswordLoginFragment()
        mPhoneFragment = PhoneLoginFragment()
        if (savedInstanceState != null){
            fragmentType = savedInstanceState!!.getString("fragment_type",PHONE_LOGIN_TAG)
            Log.d("loginActivity", "savedInstanceState != null")
            hideFragment(fragmentType)
        }
//        hideFragment(fragmentType)
//        savedInstanceState.getB
    }

    override fun initView() {
        super.initView()
        mFragmentTransaction = mFragmentManager.beginTransaction()
        mFragmentTransaction.add(R.id.login_et_module, mPhoneFragment, PHONE_LOGIN_TAG).commit()
    }

    override fun initListener() {

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.putString("fragment_type", fragmentType)
    }

    override fun onFragmentInteraction() {
        replaceFragment()
    }

    fun replaceFragment(){
//        Log.d("aaa","aaa")
//        var mShowFragment = getShowFragment()
//        if (mShowFragment != null){
//            mFragmentManager.beginTransaction().setCustomAnimations(R.anim.login_mode_in, R.anim.login_mode_exit).hide(mShowFragment).commit()
//            if (mShowFragment is PhoneLoginFragment){
//                if (mFragmentManager.findFragmentByTag("passwordLogin") == null){
//                    mFragmentManager.beginTransaction().setCustomAnimations(R.anim.login_mode_in, R.anim.login_mode_exit).add(R.id.login_et_module, mPasswordLoginFragment, "passwordLogin").commit()
//                }else{
//                    mFragmentManager.beginTransaction().setCustomAnimations(R.anim.login_mode_in, R.anim.login_mode_exit).show(mPasswordLoginFragment).commit()
//                }
//            }else if (mShowFragment is PasswordLoginFragment){
//                if (mFragmentManager.findFragmentByTag("phoneLogin") == null){
//                    mFragmentManager.beginTransaction().setCustomAnimations(R.anim.login_mode_in, R.anim.login_mode_exit).add(R.id.login_et_module, mPhoneFragment, "phoneLogin").commit()
//                }else{
//                    mFragmentManager.beginTransaction().setCustomAnimations(R.anim.login_mode_in, R.anim.login_mode_exit).show(mPhoneFragment).commit()
//                }
//            }
//        }
        hideFragment(fragmentType)
        if (fragmentType == PHONE_LOGIN_TAG){
            fragmentType = PASSWORD_LOGIN_TAG
            if (mFragmentManager.findFragmentByTag(PASSWORD_LOGIN_TAG) == null){
                mFragmentManager.beginTransaction().setCustomAnimations(R.anim.login_mode_in, R.anim.login_mode_exit).add(R.id.login_et_module, mPasswordLoginFragment, PASSWORD_LOGIN_TAG).commit()
            }else{
                mFragmentManager.beginTransaction().setCustomAnimations(R.anim.login_mode_in, R.anim.login_mode_exit).show(mPasswordLoginFragment).commit()
            }
        }else if (fragmentType == PASSWORD_LOGIN_TAG){
            fragmentType = PHONE_LOGIN_TAG
            if (mFragmentManager.findFragmentByTag(PHONE_LOGIN_TAG) == null){
                mFragmentManager.beginTransaction().setCustomAnimations(R.anim.login_mode_in, R.anim.login_mode_exit).add(R.id.login_et_module, mPhoneFragment, PHONE_LOGIN_TAG).commit()
            }else{
                mFragmentManager.beginTransaction().setCustomAnimations(R.anim.login_mode_in, R.anim.login_mode_exit).show(mPhoneFragment).commit()
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

    fun hideFragment(fragmentType: String) {
        when(fragmentType){
            PASSWORD_LOGIN_TAG -> {
                mFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.login_mode_in, R.anim.login_mode_exit)
                    .hide(mPasswordLoginFragment)
                    .commit()
            }
            PHONE_LOGIN_TAG -> {
                mFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.login_mode_in, R.anim.login_mode_exit)
                    .hide(mPhoneFragment)
                    .commit()
            }
        }
    }


    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun createPresenter(): LoginContract.Presenter {
        return LoginPresenter()
    }

}
