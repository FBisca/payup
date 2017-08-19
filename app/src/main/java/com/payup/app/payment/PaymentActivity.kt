package com.payup.app.payment

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import com.payup.R
import com.payup.app.components.ComponentFragmentActivity
import com.payup.di.components.PaymentActivityComponent
import com.payup.di.components.PaymentActivityModule
import javax.inject.Inject

class PaymentActivity : ComponentFragmentActivity() {

    @Inject
    lateinit var viewModel: PaymentViewModel

    private val toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }

    override fun initInjection() {
        injectionBuilder<PaymentActivityComponent.Builder>()
                .module(PaymentActivityModule(this))
                .build()
                .injectMembers(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, PaymentContactsFragment())
                .commit()

        bindContactClickEvents()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun bindContactClickEvents() {
        viewModel.contactClickEvents
                .subscribe {
                    Log.d("Teste", it.toString())
                }
    }

}
