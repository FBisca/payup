package com.payup.app.components

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.view.View
import com.payup.app.ui.payment.ConfirmationActivity
import com.payup.app.ui.payment.PaymentActivity
import com.payup.di.ActivityScope
import com.payup.model.Contact
import javax.inject.Inject

@ActivityScope
class Navigator @Inject constructor(
        private val activity: Activity
) {
    companion object {
        const val EXTRA_CONTACT = "e_contact"
        const val EXTRA_VALUE = "e_value"
    }

    fun goToPaymentContacts() {
        val intent = Intent(activity, PaymentActivity::class.java)
        startActivity(intent)
    }

    fun goToPaymentConfirmation(contact: Contact, value: Double) {
        val intent = Intent(activity, ConfirmationActivity::class.java)
                .putExtra(EXTRA_CONTACT, contact)
                .putExtra(EXTRA_VALUE, value)

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
