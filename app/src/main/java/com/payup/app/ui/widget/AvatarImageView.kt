package com.payup.app.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import com.payup.R
import de.hdodenhof.circleimageview.CircleImageView

class AvatarImageView @JvmOverloads constructor(
        context: Context,
        attributeSet: AttributeSet? = null,
        defStyle: Int = 0
) : CircleImageView(context, attributeSet, defStyle) {

    companion object {
        const val DEFAULT_BORDER_NUMBER = 1
    }

    private val ringsColor: Drawable?
    private val ringsCount: Int
    private val ringsSize: Float

    init {
        val attrs = context.obtainStyledAttributes(attributeSet, R.styleable.AvatarImageView, defStyle, 0)

        ringsColor = attrs.getDrawable(R.styleable.AvatarImageView_ringsColor)
        ringsCount = attrs.getInteger(R.styleable.AvatarImageView_ringsCount, DEFAULT_BORDER_NUMBER)
        ringsSize = attrs.getDimension(R.styleable.AvatarImageView_ringsSize, context.resources.getDimension(R.dimen.space_xsmall))

        attrs.recycle()

        definePadding()
    }

    override fun onDraw(canvas: Canvas) {
        drawBorders(canvas)
        super.onDraw(canvas)
    }

    private fun drawBorders(canvas: Canvas) {
        ringsColor?.let { drawable ->
            for (i in 0 until ringsCount) {
                val size = (ringsSize * i).toInt()

                drawable.setBounds(
                        0 + size,
                        0 + size,
                        width - size,
                        height - size
                )
                drawable.draw(canvas)
            }
        }
    }

    private fun definePadding() {
        val addToPadding = (ringsSize * ringsCount).toInt()

        setPadding(
                paddingLeft + addToPadding,
                paddingTop + addToPadding,
                paddingRight + addToPadding,
                paddingBottom + addToPadding
        )
    }

}
