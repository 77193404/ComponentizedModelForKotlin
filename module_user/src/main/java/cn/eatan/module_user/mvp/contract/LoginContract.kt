package cn.eatan.module_user.mvp.contract

import cn.eatan.mvp.basemvp.IPresenter
import cn.eatan.mvp.basemvp.IView

interface LoginContract{

    interface View: IView {

    }

    interface Presenter:
        IPresenter<View> {

    }
}