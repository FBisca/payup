package com.payup.app.home

import android.app.ActivityOptions
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.payup.R
import com.payup.app.Navigator
import com.payup.app.payment.PaymentActivity
import com.payup.databinding.ActivityHomeBinding
import io.reactivex.disposables.CompositeDisposable

class HomeActivity : AppCompatActivity() {

    private lateinit var layoutBinding: ActivityHomeBinding

    private val viewModel = HomeViewModel(Navigator(this))
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        bindViewModel()
        bindUser()
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
