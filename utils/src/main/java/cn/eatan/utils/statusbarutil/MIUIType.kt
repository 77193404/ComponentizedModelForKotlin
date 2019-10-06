package cn.eatan.utils.statusbarutil

import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 *
 * @ClassName: MIUIType
 * @Description: 获取MIUI版本号工具类
 * @Author: Eatan
 * @CreateDate: 2019/9/23 22:39
 */
object MIUIType {

    private val TAG = "MIUITypeUtils"

    /**
     * 获取小米 rom 版本号，获取失败返回 -1
     *
     * @return miui rom version code, if fail , return -1
     */
    fun getMiuiVersion(): Int {
        val version =
            getSystemProperty("ro.miui.ui.version.name")
        if (version != null) {
            try {
                return Integer.parseInt(version.substring(1))
            } catch (e: Exception) {
                Log.e(TAG, "get miui version code error, version : $version")
                Log.e(TAG, Log.getStackTraceString(e))
            }

        }
        return -1
    }

    /**
     * 获取小米系统的版本号
     *
     * @param propName
     * @return
     */
    fun getSystemProperty(propName: String): String? {
        val line: String
        var input: BufferedReader? = null
        try {
            val p = Runtime.getRuntime().exec("getprop $propName")
            input = BufferedReader(InputStreamReader(p.inputStream), 1024)
            line = input.readLine()
            input.close()
        } catch (ex: IOException) {
            Log.e(TAG, "Unable to read sysprop $propName", ex)
            return null
        } finally {
            if (input != null) {
                try {
                    input.close()
                } catch (e: IOException) {
                    Log.e(TAG, "Exception while closing InputStream", e)
                }

            }
        }
        return line
    }
}