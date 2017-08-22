package com.payup.app.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import com.payup.R

class PillarView @JvmOverloads constructor(
        context: Context,
        attributeSet: AttributeSet? = null,
        defStyle: Int = 0
) : View(context, attributeSet, defStyle) {

    private val rect =  Rect()
    private val paint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
        color = ContextCompat.getColor(context, R.color.colorAccent)
    }

    var percentage: Int = 0
        set(value) {
            val factoredValue = value * 1.8f
            field = Math.max(Math.min(factoredValue.toInt(), 100), 0)
            invalidate()
        }

    init {
        val attrs = context.obtainStyledAttributes(attributeSet, R.styleable.PillarView, defStyle, 0)
        percentage = attrs.getInteger(R.styleable.PillarView_percentage, 0)
        attrs.recycle()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            rect.set(
                    paddingLeft,
                    paddingTop + height - ((height * percentage) / 100) - paddingBottom,
                    width - paddingRight,
                    height + paddingBottom
            )
            drawRect(rect, paint)
        }

    }
}
