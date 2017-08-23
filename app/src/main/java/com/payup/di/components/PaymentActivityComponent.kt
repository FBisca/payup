package com.payup.di.components

import com.payup.app.ui.screens.payment.PaymentActivity
import com.payup.app.ui.screens.payment.contacts.ContactsAdapter
import com.payup.app.ui.screens.payment.contacts.ContactsFragment
import com.payup.app.ui.screens.payment.PaymentViewModel
import com.payup.app.ui.screens.payment.valueInput.ValueInputFragment
import com.payup.di.*
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import dagger.multibindings.IntoMap

@ActivityScope
@Subcomponent(modules = arrayOf(PaymentActivityModule::class))
interface PaymentActivityComponent : ActivityComponent<PaymentActivity> {
    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<PaymentActivityComponent, PaymentActivityModule>
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
    fun bindsContactsComponent(builder: ContactsFragmentComponent.Builder): FragmentComponentBuilder<*> {
        return builder
    }

    @Provides
    @IntoMap
    @FragmentKey(ValueInputFragment::class)
    fun bindsValueInputComponent(builder: ValueInputFragmentComponent.Builder): FragmentComponentBuilder<*> {
        return builder
    }
}
