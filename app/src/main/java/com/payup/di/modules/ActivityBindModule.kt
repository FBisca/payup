package com.payup.di.modules

import com.payup.app.home.HomeActivity
import com.payup.app.payment.PaymentActivity
import com.payup.di.ActivityComponentBuilder
import com.payup.di.ActivityKey
import com.payup.di.components.HomeActivityComponent
import com.payup.di.components.PaymentActivityComponent
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(subcomponents = arrayOf(
        HomeActivityComponent::class,
        PaymentActivityComponent::class
))
abstract class ActivityBindModule {
    @Binds
    @IntoMap
    @ActivityKey(HomeActivity::class)
    abstract fun homeActivityComponent(builder: HomeActivityComponent.Builder): ActivityComponentBuilder<*, *>

    @Binds
    @IntoMap
    @ActivityKey(PaymentActivity::class)
    abstract fun paymentActivityComponent(builder: PaymentActivityComponent.Builder): ActivityComponentBuilder<*, *>
}
