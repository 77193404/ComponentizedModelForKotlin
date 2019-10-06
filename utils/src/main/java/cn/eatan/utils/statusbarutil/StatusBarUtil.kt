package cn.eatan.utils.statusbarutil

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresApi

/**
 *
 * @ClassName: StatusBarUtil
 * @Description: 改变状态栏颜色、改变状态栏字体颜色、状态栏透明。
 * @Author: Eatan
 * @CreateDate: 2019/10/6 19:17
 */
object StatusBarUtil {

    fun setStatusBarColorAndFontColor(activity: Activity) {
        setStatusBarColorAndFontColor(
            activity,
            Color.WHITE
        )
    }

    fun setStatusBarColorAndFontColor(activity: Activity, statusBarColor: Int) {
        setStatusBarColorAndFontColor(
            activity,
            statusBarColor,
            true
        )
    }

    /**
     * 设置状态栏颜色和状态栏字体图标颜色
     */
    fun setStatusBarColorAndFontColor(
        activity: Activity,
        statusBarBackGroundColor: Int,
        settingStatusBarLightMode: Boolean
    ) {
        //大于4.4才需要进行适配
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //MIUI系统适配方法，且版本 < 9。大于miui 9则直接使用原生方法适配
            if (setMIUIStatusBarLightMode(
                    activity.window,
                    settingStatusBarLightMode
                ) && MIUIType.getMiuiVersion() < 9
            ) {
                //设置状态栏颜色
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    //5.0
                    activity.window.statusBarColor = statusBarBackGroundColor
                } else {
                    //4.4
                    setStatusBarColorByKITKAT(
                        activity,
                        statusBarBackGroundColor
                    )
                }
            }
            //Flyme系统适配
            else if (setFlymeStatusBarLightMode(
                    activity.window,
                    settingStatusBarLightMode
                )
            ) {
                //设置状态栏颜色
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    //5.0
                    activity.window.statusBarColor = statusBarBackGroundColor
                } else {
                    //4.4
                    setStatusBarColorByKITKAT(
                        activity,
                        statusBarBackGroundColor
                    )
                }
            }
            //原生适配
            else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                setStatusBarColorByCommonSystem(
                    activity,
                    statusBarBackGroundColor,
                    settingStatusBarLightMode
                )
            }
        }
    }

    /**
     * 设置MIUI系统是否为亮色主题
     * 需要MIUI版本号小于9，大于9时代码不会报错，但是没有效果。
     * return 如果不是小米系统会返回false
     */
    private fun setMIUIStatusBarLightMode(window: Window?, dark: Boolean): Boolean {
        var result = false
        if (window != null) {
            val clazz = window.javaClass
            try {
                val darkModeFlag: Int
                @SuppressLint("PrivateApi") val layoutParams =
                    Class.forName("android.view.MiuiWindowManager\$LayoutParams")
                val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                darkModeFlag = field.getInt(layoutParams)
                val extraFlagField = clazz.getMethod(
                    "setExtraFlags",
                    Int::class.javaPrimitiveType,
                    Int::class.javaPrimitiveType
                )
                if (dark) {
                    //状态栏透明且黑色字体
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag)
                } else {
                    //清除黑色字体
                    extraFlagField.invoke(window, 0, darkModeFlag)
                }
                result = true
            } catch (e: Exception) {

            }
        }
        return result
    }

    /**
     * 设置Flyme系统状态栏颜色
     * retrun 如果不是Flyme系统则返回false
     */
    private fun setFlymeStatusBarLightMode(window: Window?, dark: Boolean): Boolean {
        var result = false
        if (window != null) {
            try {
                val lp = window.attributes
                val darkFlag = WindowManager.LayoutParams::class.java
                    .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
                val meizuFlags = WindowManager.LayoutParams::class.java
                    .getDeclaredField("meizuFlags")
                darkFlag.isAccessible = true
                meizuFlags.isAccessible = true
                val bit = darkFlag.getInt(null)
                var value = meizuFlags.getInt(lp)
                value = if (dark) {
                    value or bit
                } else {
                    value and bit.inv()
                }
                meizuFlags.setInt(lp, value)
                window.attributes = lp
                result = true
            } catch (e: Exception) {

            }

        }
        return result
    }

    /**
     * 原生系统设置状态栏颜色和亮/暗色主题
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun setStatusBarColorByCommonSystem(
        activity: Activity,
        bgColor: Int,
        settingStatusBarLightMode: Boolean
    ) {
        val window = activity.window
        window.statusBarColor = bgColor
        if (settingStatusBarLightMode && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }else{
            window.decorView.systemUiVisibility = 0
        }
    }

    /**
     * 设置版本为4.4时的状态栏颜色
     */
    fun setStatusBarColorByKITKAT(activity: Activity, colorBg: Int) {
        activity.window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )
        val tintManager = SystemBarTintManager(activity)
        tintManager.isStatusBarTintEnabled = true
        tintManager.setStatusBarTintResource(colorBg)
        val rootView =
            (activity.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
        rootView.setPadding(0,
            getStatusBarHeight(activity), 0, 0)
    }

    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度
     */
    private fun getStatusBarHeight(context: Context): Int {
        // 获得状态栏高度
        val resourceId =
            context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return context.resources.getDimensionPixelSize(resourceId)
    }

    /**
     * 设置状态栏透明和状态栏亮/暗色主题
     */
    fun transparencyBar(activity: Activity, settingStatusBarLightMode: Boolean) {
        //设置状态栏亮/暗色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            //设置MIUI状态栏主题
            if (setMIUIStatusBarLightMode(
                    activity.window,
                    settingStatusBarLightMode
                ) && MIUIType.getMiuiVersion() < 9)
                //设置Flyme状态栏主题
                else if (setFlymeStatusBarLightMode(activity.window, settingStatusBarLightMode))
                //设置原生状态栏主题
                else {
                if (settingStatusBarLightMode && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }else{
                    activity.window.decorView.systemUiVisibility = 0
                }
            }

            //设置透明状态栏
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window = activity.window
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = Color.TRANSPARENT
            } else {
                val window = activity.window
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                )
            }
        }
    }
}