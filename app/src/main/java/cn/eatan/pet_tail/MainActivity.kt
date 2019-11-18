package cn.eatan.pet_tail

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import cn.eatan.module_user.ui.activity.LoginModeActivity
import cn.eatan.utils.statusbarutil.StatusBarUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        StatusBarUtil.transparencyBar(this, false)
        MyCountDownTimer(this, countdownTv).start()
    }

    private class MyCountDownTimer(internal var content : Context, internal var countdownTv: TextView) :CountDownTimer(3000, 1000){
        override fun onFinish() {
            content.startActivity<LoginModeActivity>()
        }

        override fun onTick(p0: Long) {
            val rt:String = (p0 / 1000).toString()
            countdownTv.text = String.format("%s秒",rt)
        }
    }
}
