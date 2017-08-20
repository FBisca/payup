package com.payup.app.components

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.view.View
import com.payup.app.payment.ConfirmationActivity
import com.payup.app.payment.PaymentActivity
import com.payup.di.ActivityScope
import javax.inject.Inject

@ActivityScope
class Navigator @Inject constructor(
        private val activity: Activity
) {
    fun goToPaymentContacts() {
        val intent = Intent(activity, PaymentActivity::class.java)
        startActivity(intent)
    }

    fun goToPaymentConfirmation() {
        val intent = Intent(activity, ConfirmationActivity::class.java)
        startActivity(intent)
    }

    private fun startActivity(intent: Intent) {
        val sharedElementsProvider = activity as? HasSharedElements

        sharedElementsProvider?.getSharedElementsForIntent(intent).let { sharedElements ->
            when (sharedElements) {
                null -> activity.startActivity(intent)
                else -> {
                    val elements = sharedElements.map { (view, name) -> android.util.Pair.create(view, name) }
                    val options = ActivityOptions.makeSceneTransitionAnimation(activity, *elements.toTypedArray())
                    activity.startActivity(intent, options.toBundle())
                }
            }
        }
    }


    interface HasSharedElements {
        fun getSharedElementsForIntent(intent: Intent): Array<Pair<View, String>>
    }
}
