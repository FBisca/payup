package com.payup.app.ui.binding

import android.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.payup.app.ui.widget.AvatarImageView

@BindingAdapter("imageUrl")
fun loadImage(view: AvatarImageView, url: String?) {
    if (url != null) {
        Glide.with(view.context).load(url).into(view)
    }
}
