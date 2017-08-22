package com.payup.app.ui.binding

import android.databinding.BindingAdapter
import android.widget.TextView
import com.bumptech.glide.Glide
import com.payup.app.ui.widget.AvatarImageView
import com.payup.utils.locale
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("imageUrl")
fun loadImage(view: AvatarImageView, url: String?) {
    if (url != null) {
        Glide.with(view.context).load(url).into(view)
    }
}

@BindingAdapter("currencyValue")
fun currencyValue(view: TextView, value: Double?) {
    if (value != null) {
        val context = view.context
        view.text = NumberFormat.getCurrencyInstance(context.locale()).format(value)
    } else {
        view.text = null
    }
}

@BindingAdapter("date")
fun date(view: TextView, value: Date?) {
    if (value != null) {
        val locale = view.context.locale()

        // Less than a week
        val formatter = if (System.currentTimeMillis() - value.time < 604800000) {
            SimpleDateFormat("EEEE HH:mm", locale)
        } else {
            SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM, locale)
        }

        view.text = formatter.format(value)
    } else {
        view.text = null
    }
}


