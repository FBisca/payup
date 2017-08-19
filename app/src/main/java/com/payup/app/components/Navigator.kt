package com.payup.app.components

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.util.Pair
import android.view.View
import com.payup.app.payment.PaymentActivity
import com.payup.di.ActivityScope
import javax.inject.Inject

@ActivityScope
class Navigator @Inject constructor(
        private val activity: Activity
) {
    fun goToPaymentContacts() {
        val intent = Intent(activity, PaymentActivity::class.java)

        val sharedElementsProvider = activity as? HasSharedElements

        sharedElementsProvider?.getSharedElementsForIntent(intent).let { sharedElements ->
            when (sharedElements) {
                null -> activity.startActivity(intent)
                else -> {
                    val options = ActivityOptions.makeSceneTransitionAnimation(activity, sharedElements)
                    activity.startActivity(intent, options.toBundle())
                }
            }
        }
    }

    interface HasSharedElements {
        fun getSharedElementsForIntent(intent: Intent): Pair<View, String>?
    }
}
