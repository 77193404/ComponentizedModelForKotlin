package cn.eatan.pet_tail.mvp.contract

import cn.eatan.mvp.basemvp.IPresenter
import cn.eatan.mvp.basemvp.IView

interface LoginModeContract{

    interface View: IView {

    }

    interface Presenter:
        IPresenter<View> {

    }
}