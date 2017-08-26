package com.payup.di.components.activity

import android.support.v4.app.Fragment
import com.payup.app.ui.screens.payment.PaymentActivity
import com.payup.app.ui.screens.payment.contacts.ContactsAdapter
import com.payup.app.ui.screens.payment.contacts.ContactsFragment
import com.payup.app.ui.screens.payment.PaymentViewModel
import com.payup.app.ui.screens.payment.valueInput.ValueInputFragment
import com.payup.di.arch.*
import com.payup.di.components.fragment.ContactsFragmentComponent
import com.payup.di.components.fragment.ValueInputFragmentComponent
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import dagger.multibindings.IntoMap

@ActivityScope
@Subcomponent(modules = arrayOf(PaymentActivityModule::class))
interface PaymentActivityComponent : ActivityComponent<PaymentActivity> {
    @Subcomponent.Builder
    interface Builder : ActivityComponent.Builder<PaymentActivity, PaymentActivityModule>
}

@ActivityScope
@Module(subcomponents = arrayOf(
        ContactsFragmentComponent::class,
        ValueInputFragmentComponent::class
))
class PaymentActivityModule(
        activity: PaymentActivity,
        private val initialViewState: PaymentViewModel.ViewState
) : ActivityModule(activity) {

    @Provides
    fun providesViewState(): PaymentViewModel.ViewState {
        return initialViewState
    }

    @Provides
    fun providesAdapterListener(viewModel: PaymentViewModel): ContactsAdapter.Listener {
        return viewModel
    }

    @Provides
    @IntoMap
    @FragmentKey(ContactsFragment::class)
    fun bindsContactsComponent(builder: ContactsFragmentComponent.Builder): FragmentComponent.Builder<out Fragment> {
        return builder
    }

    @Provides
    @IntoMap
    @FragmentKey(ValueInputFragment::class)
    fun bindsValueInputComponent(builder: ValueInputFragmentComponent.Builder): FragmentComponent.Builder<out Fragment> {
        return builder
    }
}
