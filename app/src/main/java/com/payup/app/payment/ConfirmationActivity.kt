package com.payup.app.payment

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.payup.R
import com.payup.app.components.ComponentActivity
import com.payup.databinding.ActivityConfirmationBinding

class ConfirmationActivity : ComponentActivity() {

    private lateinit var binding: ActivityConfirmationBinding

    override fun initInjection(savedInstanceState: Bundle?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_confirmation)
    }
}
