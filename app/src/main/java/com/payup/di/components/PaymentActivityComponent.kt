package com.payup.di.components

import com.payup.app.ui.payment.PaymentActivity
import com.payup.app.ui.payment.contacts.ContactsAdapter
import com.payup.app.ui.payment.contacts.ContactsFragment
import com.payup.app.ui.payment.PaymentViewModel
import com.payup.app.ui.payment.valueInput.ValueInputFragment
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
class PaymentActivityModule(activity: PaymentActivity) : ActivityModule(activity) {

    @Provides
    fun providesViewState(): PaymentViewModel.ViewState {
        return PaymentViewModel.ViewState.ContactSelect
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
