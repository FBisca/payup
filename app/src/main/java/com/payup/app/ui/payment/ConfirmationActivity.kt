package com.payup.app.ui.payment

import android.animation.Animator
import android.animation.ObjectAnimator
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.view.ViewCompat
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.view.View
import com.payup.R
import com.payup.app.components.ComponentActivity
import com.payup.app.components.Navigator
import com.payup.app.ui.payment.valueInput.ConfirmationViewModel
import com.payup.app.ui.payment.valueInput.ConfirmationViewModel.ViewState.*
import com.payup.databinding.ActivityConfirmationBinding
import com.payup.di.components.ConfirmationActivityComponent
import com.payup.di.components.ConfirmationActivityModule
import javax.inject.Inject

class ConfirmationActivity : ComponentActivity() {

    private lateinit var binding: ActivityConfirmationBinding

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var viewModel: ConfirmationViewModel

    private var animator: Animator? = null

    override fun initInjection(savedInstanceState: Bundle?) {
        val module = ConfirmationActivityModule(
                this,
                intent.getParcelableExtra(Navigator.EXTRA_CONTACT),
                intent.getDoubleExtra(Navigator.EXTRA_VALUE, 0.0)
        )
        injectionBuilder<ConfirmationActivityComponent.Builder>()
                .module(module)
                .build()
                .injectMembers(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_confirmation)
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.viewModel = viewModel

        bindState()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.viewDestroyed()
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onSupportNavigateUp()
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        if (viewModel.viewState.value == Success) {
            navigator.returnToHome()
        } else {
            super.onBackPressed()
        }
    }

    private fun bindState() {
        viewModel.viewState.subscribe {
            when (it) {
                Loading -> showLoading()
                Success -> showSuccess()
                Error -> showError()
            }
        }
    }

    private fun showLoading() {
        animator?.cancel()

        binding.confirmationButton.isEnabled = false
        binding.messageText.visibility = View.INVISIBLE
        binding.iconSuccess.visibility = View.GONE
        binding.iconError.visibility = View.GONE

        val params = binding.iconArrowForward.layoutParams as ConstraintLayout.LayoutParams
        val translation = params.marginEnd + binding.iconArrowForward.width.toFloat()

        animator = ObjectAnimator.ofFloat(binding.iconArrowForward, "translationX", -translation, translation).apply {
            interpolator = FastOutSlowInInterpolator()
            repeatMode = ObjectAnimator.RESTART
            repeatCount = ObjectAnimator.INFINITE
            duration = 1000
            start()
        }
    }

    private fun showSuccess() {
        finishLoadingAnimation()

        binding.confirmationButton.isEnabled = true
        binding.confirmationButton.setText(R.string.back)
        binding.messageText.setText(R.string.payment_success)
        binding.messageText.visibility = View.VISIBLE

        binding.iconSuccess.apply {
            alpha = 0f
            scaleX = 0f
            scaleY = 0f
            visibility = View.VISIBLE

            ViewCompat.animate(this)
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setInterpolator(FastOutSlowInInterpolator())
                    .start()
        }
    }

    private fun showError() {
        finishLoadingAnimation()

        binding.confirmationButton.isEnabled = true
        binding.confirmationButton.setText(R.string.try_again)
        binding.messageText.setText(R.string.payment_error)
        binding.messageText.visibility = View.VISIBLE

        binding.iconError.apply {
            alpha = 0f
            scaleX = 0f
            scaleY = 0f
            visibility = View.VISIBLE

            ViewCompat.animate(this)
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setInterpolator(FastOutSlowInInterpolator())
                    .start()
        }

    }

    private fun finishLoadingAnimation() {
        animator?.cancel()
        animator = ObjectAnimator.ofFloat(binding.iconArrowForward, "translationX", 0f).apply {
            interpolator = FastOutSlowInInterpolator()
            duration = 1000
            start()
        }
    }
}
