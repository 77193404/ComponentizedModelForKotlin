package cn.eatan.mvp.base

import android.app.Application

open class BaseApp: Application() {

    companion object{
        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}