package cn.eatan.mvp.base

import android.os.Bundle
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import cn.eatan.utils.statusbarutil.StatusBarUtil

abstract class BaseActivity: AppCompatActivity(){

    /**
     * 布局文件Id
     */
    @LayoutRes
    abstract fun getLayoutId() : Int
    abstract fun initView()
    abstract fun initData()
    abstract fun initListener()
    var savedInstanceState: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("base","onCreate")
        setContentView(getLayoutId())
        StatusBarUtil.setStatusBarColorAndFontColor(this)
        if (savedInstanceState != null){
            this.savedInstanceState = savedInstanceState
        }
        initData()
        initView()
        initListener()
    }

}