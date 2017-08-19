package com.payup.app

import android.app.Activity
import android.content.Intent
import com.payup.app.payment.PaymentActivity
import com.payup.di.ActivityScope
import javax.inject.Inject

@ActivityScope
class Navigator @Inject constructor(private val activity: Activity) {

    fun goToPaymentContacts() {
        val intent = Intent(activity, PaymentActivity::class.java)
        activity.startActivity(intent)
    }
}
