package com.payup.app.payment

import android.content.Context
import android.graphics.*
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import com.payup.R

class PaymentContactsItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val dividerHeight = context.resources.displayMetrics.density * 0.5f
    private val dividerMargin = context.resources.getDimension(R.dimen.space_medium)
    private val dividerPaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.parseColor("#506574")
    }

    override fun getItemOffsets(outRect: Rect, view: View?, parent: RecyclerView, state: RecyclerView.State) {
        outRect.top = dividerHeight.toInt()
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        val childCount = parent.childCount
        for (position in 0 until childCount) {
            val child = parent.getChildAt(position)
            val params = child.layoutParams as RecyclerView.LayoutParams

            addDivider(parent, child, params, canvas)
        }
    }

    private fun addDivider(
            parent: RecyclerView,
            child: View,
            params: RecyclerView.LayoutParams,
            canvas: Canvas
    ) {

        val dividerLeft = parent.paddingLeft + dividerMargin
        val dividerRight = parent.width - parent.paddingRight - dividerMargin
        val dividerTop = child.bottom + params.bottomMargin
        val dividerBottom = dividerTop + dividerHeight

        val rect = RectF(dividerLeft, dividerTop.toFloat(), dividerRight, dividerBottom)
        canvas.drawRect(rect, dividerPaint)
    }
}
