package com.payup.di.components

import com.payup.app.payment.PaymentContactsFragment

import com.payup.app.payment.PaymentContactsViewModel
import com.payup.di.FragmentComponent
import com.payup.di.FragmentComponentBuilder
import com.payup.di.FragmentScope
import dagger.Subcomponent

@Subcomponent
@FragmentScope
interface PaymentContactsFragmentComponent : FragmentComponent<PaymentContactsFragment>{

    fun viewModel(): PaymentContactsViewModel

    @Subcomponent.Builder
    interface Builder : FragmentComponentBuilder<PaymentContactsFragmentComponent>
}
