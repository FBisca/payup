package com.payup.app.ui.screens.home

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import com.payup.R
import com.payup.app.Navigator
import com.payup.app.arch.ComponentActivity
import com.payup.app.ui.screens.payment.PaymentActivity
import com.payup.databinding.ActivityHomeBinding
import com.payup.di.components.activity.HomeActivityModule
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class HomeActivity : ComponentActivity<HomeActivityModule>(), Navigator.HasSharedElements {

    @Inject
    lateinit var viewModel: HomeViewModel

    private lateinit var layoutBinding: ActivityHomeBinding

    private val disposables = CompositeDisposable()

    override fun instantiateModule(savedInstanceState: Bundle?): HomeActivityModule {
        return HomeActivityModule(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewModel.viewCreated()
        bindViewModel()
        bindUser()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.viewDestroyed()
        disposables.dispose()
    }

    override fun getSharedElementsForIntent(intent: Intent): Array<Pair<View, String>> {
        return when (intent.component.className) {
            PaymentActivity::class.java.name -> arrayOf(layoutBinding.avatarPhotoImage to "avatarImage")
            else -> emptyArray()
        }
    }

    private fun bindViewModel() {
        layoutBinding.viewModel = viewModel
    }

    private fun bindUser() {
        viewModel.user()
                .subscribe({ layoutBinding.user = it })
                .apply { disposables.add(this) }
    }
}
