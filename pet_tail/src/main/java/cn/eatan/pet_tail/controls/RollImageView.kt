package cn.eatan.pet_tail.controls

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View


import java.util.ArrayList
import java.util.Random

import cn.eatan.pet_tail.R

import java.lang.Math.abs
import kotlin.math.max

/**
 *  纵向滚动的View
 */
class RollImageView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var top: Float = 0.toFloat()
    private var bitmap: Bitmap? = null
    private var mHeight: Int = 0

    private var bitmaps: MutableList<Bitmap>? = null
    private var speed: Float = 0.toFloat()
    private var scene: IntArray? = null
    private var arrayIndex = 0
    private var maxBitmapHeight = 0

    private val mClipBounds = Rect()
    private var offset = 0f

    private var isStarted: Boolean = false

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.RollImageView, 0, 0)
        val initialState: Int
        try {
            initialState = ta.getInt(R.styleable.RollImageView_initialState, 0)
            speed = ta.getDimension(R.styleable.RollImageView_speed, 10f)
            val sceneLength = ta.getInt(R.styleable.RollImageView_sceneLength, 1000)
            val randomnessResourceId = ta.getResourceId(R.styleable.RollImageView_randomness, 0)
            var randomness = IntArray(0)
            if (randomnessResourceId > 0) {
                randomness = resources.getIntArray(randomnessResourceId)
            }

            val type =
                if (isInEditMode) TypedValue.TYPE_STRING else ta.peekValue(R.styleable.RollImageView_src).type
            if (type == TypedValue.TYPE_REFERENCE) {
                val resourceId = ta.getResourceId(R.styleable.RollImageView_src, 0)
                val typedArray = resources.obtainTypedArray(resourceId)
                try {
                    var bitmapsSize = 0
                    for (r in randomness) {
                        bitmapsSize += r
                    }

                    bitmaps = ArrayList(max(typedArray.length(), bitmapsSize))

                    for (i in 0 until typedArray.length()) {
                        var multiplier = 1
                        if (randomness.isNotEmpty() && i < randomness.size) {
                            multiplier = max(1, randomness[i])
                        }

                        val bitmap =
                            BITMAP_LOADER.loadBitmap(getContext(), typedArray.getResourceId(i, 0))
                        for (m in 0 until multiplier) {
                            bitmaps!!.add(bitmap)
                        }

                        maxBitmapHeight = max(bitmap.height, maxBitmapHeight)
                    }

                    val random = Random()
                    this.scene = IntArray(sceneLength)
                    for (i in this.scene!!.indices) {
                        this.scene!![i] = random.nextInt(bitmaps!!.size)
                    }
                } finally {
                    typedArray.recycle()
                }
            } else if (type == TypedValue.TYPE_STRING) {
                val bitmap = BITMAP_LOADER.loadBitmap(
                    getContext(),
                    ta.getResourceId(R.styleable.RollImageView_src, 0)
                )
                bitmaps = mutableListOf(bitmap)
                scene = intArrayOf(0)
                maxBitmapHeight = bitmaps!![0].height
            }
        } finally {
            ta.recycle()
        }

        if (initialState == 0) {
            start()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), maxBitmapHeight)
    }

    public override fun onDraw(canvas: Canvas?) {
        if (!isInEditMode) {
            super.onDraw(canvas)
            if (canvas == null || bitmaps!!.isEmpty()) {
                return
            }

            canvas.getClipBounds(mClipBounds)

            while (offset <= -getBitmap(arrayIndex).height) {
                offset += getBitmap(arrayIndex).height.toFloat()
                arrayIndex = (arrayIndex + 1) % scene!!.size
            }

            top = offset
            var i = 0
            while (top < mClipBounds.height()) {
                bitmap = getBitmap((arrayIndex + i) % scene!!.size)
                mHeight = bitmap!!.height
                canvas.drawBitmap(bitmap!!, 0f, getBitmapLeft(mHeight.toFloat(), top), null)
                top += mHeight.toFloat()
                i++
            }

            if (isStarted && speed != 0f) {
                offset -= abs(speed)
                postInvalidateOnAnimation()
            }
        }
    }

    private fun getBitmap(sceneIndex: Int): Bitmap {
        return bitmaps!![scene!![sceneIndex]]
    }

    private fun getBitmapLeft(layerWidth: Float, left: Float): Float {
        return if (speed < 0) {
            mClipBounds.height().toFloat() - layerWidth - left
        } else {
            left
        }
    }

    /**
     * Start the animation
     */
    fun start() {
        if (!isStarted) {
            isStarted = true
            postInvalidateOnAnimation()
        }
    }

    /**
     * Stop the animation
     */
    fun stop() {
        if (isStarted) {
            isStarted = false
            invalidate()
        }
    }

    fun setSpeed(speed: Float) {
        this.speed = speed
        if (isStarted) {
            postInvalidateOnAnimation()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stop()
        if (bitmap != null && !bitmap!!.isRecycled) {
            bitmap!!.recycle()
            bitmap = null
        }
    }

    companion object {

        var BITMAP_LOADER: ScrollingImageViewBitmapLoader =
            object : ScrollingImageViewBitmapLoader {
                override fun loadBitmap(context: Context, resourceId: Int): Bitmap {
                    return BitmapFactory.decodeResource(context.resources, resourceId)
                }
            }
    }
}
