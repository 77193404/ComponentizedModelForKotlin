package cn.eatan.pet_tail

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import cn.eatan.lib_base.base.constant.Constant.Companion.ROUTER_LOGIN_ACTIVITY
import cn.eatan.utils.statusbarutil.StatusBarUtil
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.info

class MainActivity : AppCompatActivity(), AnkoLogger {

    private lateinit var myCountDownTimer: MyCountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        StatusBarUtil.transparencyBar(this, false)
        myCountDownTimer = MyCountDownTimer()
    }

    override fun onResume() {
        super.onResume()
        myCountDownTimer.start()
    }

    override fun onPause() {
        super.onPause()
        myCountDownTimer.cancel()
    }



    inner class MyCountDownTimer:CountDownTimer(3000, 1000){
        override fun onFinish() {
            ARouter.getInstance().build(ROUTER_LOGIN_ACTIVITY).navigation()
        }

        override fun onTick(p0: Long) {
            val rt:String = (p0 / 1000).toString()
            countdownTv.text = String.format("%s秒",rt)
        }
    }
}
