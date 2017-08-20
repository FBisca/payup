package com.payup.app.payment

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.support.v4.view.animation.LinearOutSlowInInterpolator
import android.view.View
import com.payup.R
import com.payup.app.components.ComponentFragmentActivity
import com.payup.app.components.Navigator
import com.payup.app.payment.PaymentViewModel.ViewState.ContactSelect
import com.payup.app.payment.PaymentViewModel.ViewState.ValueInput
import com.payup.app.payment.contacts.ContactsFragment
import com.payup.app.payment.valueInput.ValueInputFragment
import com.payup.databinding.ActivityPaymentBinding
import com.payup.di.components.PaymentActivityComponent
import com.payup.di.components.PaymentActivityModule
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class PaymentActivity : ComponentFragmentActivity(), Navigator.HasSharedElements {

    @Inject
    lateinit var viewModel: PaymentViewModel

    private lateinit var binding: ActivityPaymentBinding
    private val disposables = CompositeDisposable()

    override fun initInjection(savedInstanceState: Bundle?) {
        injectionBuilder<PaymentActivityComponent.Builder>()
                .module(PaymentActivityModule(this))
                .build()
                .injectMembers(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment)
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        bindViewState()
        bindUser()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        val intercepted = viewModel.onBackPressed()
        if (intercepted.not()) {
            super.onBackPressed()
        }
    }

    override fun getSharedElementsForIntent(intent: Intent): Array<Pair<View, String>> {
        return when (intent.component.className) {
            ConfirmationActivity::class.java.name -> arrayOf(
                    binding.avatarPhotoImage to binding.avatarPhotoImage.transitionName,
                    binding.recipientPhotoImage to binding.recipientPhotoImage.transitionName
            )
            else -> emptyArray()
        }
    }

    private fun bindUser() {
        viewModel.user()
                .subscribe {
                    binding.user = it
                }
                .apply { disposables.add(this) }
    }

    private fun bindViewState() {
        viewModel.viewState
                .subscribe {
                    when (it) {
                        ContactSelect -> showContactSelect()
                        is ValueInput -> showValueInput(it)
                    }
                }
                .apply { disposables.add(this) }
    }

    private fun showValueInput(viewState: PaymentViewModel.ViewState.ValueInput) {
        binding.contact = viewState.contact

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ValueInputFragment())
                .runOnCommit { animateValueInputShow() }
                .commit()

    }

    private fun showContactSelect() {
        binding.contact = null


        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ContactsFragment())
                .runOnCommit { animateContactSelect() }
                .commit()
    }

    private fun animateValueInputShow() {
        binding.recipientNameText.alpha = 1f
        binding.emptyRecipientPhotoImage.visibility = View.INVISIBLE
        binding.recipientPhotoImage.apply {
            scaleX = 0f
            scaleY = 0f
            alpha = 0f
            visibility = View.VISIBLE
        }

        ViewCompat.animate(binding.recipientPhotoImage)
                .scaleX(1f)
                .scaleY(1f)
                .alpha(1f)
                .setInterpolator(FastOutSlowInInterpolator())
                .setListener(object : ViewPropertyAnimatorListenerAdapter() {
                    override fun onAnimationEnd(view: View?) {

                    }
                })
                .start()
    }

    private fun animateContactSelect() {
        binding.recipientNameText.alpha = 0.7f
        binding.emptyRecipientPhotoImage.visibility = View.VISIBLE

        ViewCompat.animate(binding.recipientPhotoImage)
                .scaleX(0f)
                .scaleY(0f)
                .alpha(0f)
                .setInterpolator(LinearOutSlowInInterpolator())
                .setListener(object : ViewPropertyAnimatorListenerAdapter() {
                    override fun onAnimationEnd(view: View?) {
                        binding.recipientPhotoImage.visibility = View.INVISIBLE

                    }
                })
                .start()
    }
}
