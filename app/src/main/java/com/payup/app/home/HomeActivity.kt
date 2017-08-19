package com.payup.app.home

import android.app.ActivityOptions
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import com.payup.R
import com.payup.app.ComponentActivity
import com.payup.app.payment.PaymentActivity
import com.payup.databinding.ActivityHomeBinding
import com.payup.di.components.HomeComponent
import com.payup.di.components.HomeModule
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class HomeActivity : ComponentActivity() {

    private lateinit var layoutBinding: ActivityHomeBinding

    @Inject
    lateinit var viewModel: HomeViewModel
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        bindViewModel()
        bindUser()
    }

    override fun initInjection() {
        injectionBuilder<HomeComponent.Builder>()
                .module(HomeModule(this))
                .build()
                .injectMembers(this)
    }

    private fun bindViewModel() {
        layoutBinding.viewModel = viewModel
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    private fun bindUser() {
        viewModel.user()
                .subscribe({ layoutBinding.user = it })
                .apply { disposables.add(this) }
    }

    fun sendPayment(view: View) {
        val options = ActivityOptions.makeSceneTransitionAnimation(this, layoutBinding.avatarPhotoImage,"avatarImage")
        val intent = Intent(this, PaymentActivity::class.java)
        startActivity(intent, options.toBundle())
    }
}
