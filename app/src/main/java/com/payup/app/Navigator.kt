package com.payup.app

import android.app.Activity
import android.content.Intent
import com.payup.app.payment.PaymentActivity

class Navigator(private val activity: Activity) {

    fun goToPaymentContacts() {
        val intent = Intent(activity, PaymentActivity::class.java)
        activity.startActivity(intent)
    }
}
