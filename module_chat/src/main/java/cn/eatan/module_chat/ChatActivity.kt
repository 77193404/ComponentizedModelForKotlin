package cn.eatan.module_chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import cn.eatan.lib_base.base.constant.Constant.Companion.ROUTER_LOGIN_ACTIVITY
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        login_button2.setOnClickListener(View.OnClickListener {
            ARouter.getInstance()
                .build(ROUTER_LOGIN_ACTIVITY)
                .navigation()
        })
    }
}
