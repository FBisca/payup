package com.payup.app

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.view.View
import com.payup.app.ui.screens.history.HistoryActivity
import com.payup.app.ui.screens.home.HomeActivity
import com.payup.app.ui.screens.payment.ConfirmationActivity
import com.payup.app.ui.screens.payment.PaymentActivity
import com.payup.data.OpenForTests
import com.payup.di.ActivityScope
import com.payup.model.Contact
import javax.inject.Inject

@OpenForTests
@ActivityScope
class Navigator @Inject constructor(
        private val activity: Activity
) {
    companion object {
        const val EXTRA_CONTACT = "e_contact"
        const val EXTRA_VALUE = "e_value"
    }

    fun returnToHome() {
        val intent = Intent(activity, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
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

    fun goToHistoryActivity() {
        startActivity(Intent(activity, HistoryActivity::class.java))
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
