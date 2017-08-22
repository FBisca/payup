package com.payup.app.ui.screens.history

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import com.payup.R

class GraphItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val dividerMargin = context.resources.getDimension(R.dimen.space_medium)

    override fun getItemOffsets(outRect: Rect, view: View?, parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = dividerMargin.toInt()
        val position = parent.getChildAdapterPosition(view)
        if (position == parent.adapter.itemCount - 1) {
            outRect.right = dividerMargin.toInt()
        }
    }
}
