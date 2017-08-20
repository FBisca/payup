package com.payup.app.ui.payment

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.payup.R
import com.payup.app.components.ComponentActivity
import com.payup.app.components.Navigator
import com.payup.databinding.ActivityConfirmationBinding
import com.payup.di.components.ConfirmationActivityComponent
import com.payup.di.components.ConfirmationActivityModule

class ConfirmationActivity : ComponentActivity() {

    private lateinit var binding: ActivityConfirmationBinding

    override fun initInjection(savedInstanceState: Bundle?) {
        injectionBuilder<ConfirmationActivityComponent.Builder>()
                .module(ConfirmationActivityModule(this))
                .build()
                .injectMembers(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_confirmation)
        binding.contact = intent.getParcelableExtra(Navigator.EXTRA_CONTACT)
        binding.value = intent.getDoubleExtra(Navigator.EXTRA_VALUE, 0.0)
    }
}
