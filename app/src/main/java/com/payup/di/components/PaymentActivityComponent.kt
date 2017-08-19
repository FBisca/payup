package com.payup.di.components

import com.payup.app.payment.PaymentActivity
import com.payup.app.payment.PaymentContactsAdapter
import com.payup.app.payment.PaymentContactsFragment
import com.payup.app.payment.PaymentViewModel
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
@Module(subcomponents = arrayOf(PaymentContactsFragmentComponent::class))
class PaymentActivityModule(activity: PaymentActivity) : ActivityModule(activity) {

    @Provides
    @IntoMap
    @FragmentKey(PaymentContactsFragment::class)
    fun bindsPaymentContactsComponent(builder: PaymentContactsFragmentComponent.Builder): FragmentComponentBuilder<*> {
        return builder
    }

    @Provides
    fun providesAdapterListener(viewModel: PaymentViewModel): PaymentContactsAdapter.Listener {
        return viewModel
    }
}
