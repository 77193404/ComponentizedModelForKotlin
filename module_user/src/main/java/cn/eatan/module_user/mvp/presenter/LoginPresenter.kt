package cn.eatan.module_user.mvp.presenter

import cn.eatan.mvp.base.BasePresenter
import cn.eatan.module_user.mvp.contract.LoginContract

class LoginPresenter : BasePresenter<LoginContract.View>()
    ,LoginContract.Presenter{

}