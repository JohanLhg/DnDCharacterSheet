package com.jlahougue.dndcharactersheet.ui.elements

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.EditText
import kotlin.math.max


@SuppressLint("AppCompatCustomView")
class LinedEditText : EditText {
    private val mPaint = Paint()

    constructor(context: Context?) : super(context) {
        initPaint()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initPaint()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        initPaint()
    }

    private fun initPaint() {
        mPaint.style = Paint.Style.STROKE
        mPaint.color = -0x80000000
    }

    override fun onDraw(canvas: Canvas) {
        val left = left
        val right = right
        val paddingTop = paddingTop
        val paddingBottom = paddingBottom
        val paddingLeft = paddingLeft
        val paddingRight = paddingRight
        val height = height
        val lineHeight = lineHeight
        val count = max(((height - paddingTop - paddingBottom) / lineHeight), lineCount)
        for (i in 0 until count) {
            val baseline = lineHeight * (i + 1) + paddingTop
            canvas.drawLine(
                paddingLeft.toFloat(),
                baseline.toFloat(),
                (right - paddingRight).toFloat(),
                baseline.toFloat(),
                mPaint
            )
        }
        super.onDraw(canvas)
    }
}