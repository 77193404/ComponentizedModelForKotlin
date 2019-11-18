package cn.eatan.lib_base.base

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter

open class BaseApp: Application() {

    companion object{
        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        ARouter.openLog()
        ARouter.openDebug()
        //ARouter初始化
        ARouter.init(this)
    }
}