package com.payup.di.modules

import com.payup.app.ui.screens.history.HistoryActivity
import com.payup.app.ui.screens.home.HomeActivity
import com.payup.app.ui.screens.payment.ConfirmationActivity
import com.payup.app.ui.screens.payment.PaymentActivity
import com.payup.di.ActivityComponentBuilder
import com.payup.di.ActivityKey
import com.payup.di.components.ConfirmationActivityComponent
import com.payup.di.components.HistoryActivityComponent
import com.payup.di.components.HomeActivityComponent
import com.payup.di.components.PaymentActivityComponent
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(subcomponents = arrayOf(
        HomeActivityComponent::class,
        PaymentActivityComponent::class,
        HistoryActivityComponent::class,
        ConfirmationActivityComponent::class
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

    @Binds
    @IntoMap
    @ActivityKey(ConfirmationActivity::class)
    abstract fun confirmationActivityComponent(builder: ConfirmationActivityComponent.Builder): ActivityComponentBuilder<*, *>

    @Binds
    @IntoMap
    @ActivityKey(HistoryActivity::class)
    abstract fun historyActivityComponent(builder: HistoryActivityComponent.Builder): ActivityComponentBuilder<*, *>
}
