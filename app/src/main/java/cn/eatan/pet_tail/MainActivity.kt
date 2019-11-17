package cn.eatan.pet_tail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.eatan.lib_base.base.constant.Constant.Companion.ROUTER_LOGIN_MODE_ACTIVITY
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ARouter.getInstance()
            .build(ROUTER_LOGIN_MODE_ACTIVITY)
            .navigation()
        finish()
    }
}
