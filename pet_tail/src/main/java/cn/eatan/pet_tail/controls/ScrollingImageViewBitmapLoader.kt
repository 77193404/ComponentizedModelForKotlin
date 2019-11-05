package cn.eatan.pet_tail.controls

import android.content.Context
import android.graphics.Bitmap

/**
 * 滚动View图片资源加载器
 */
interface ScrollingImageViewBitmapLoader {
    fun loadBitmap(context: Context, resourceId:Int): Bitmap
}