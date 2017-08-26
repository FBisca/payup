package com.payup.di.modules

import android.app.Activity
import com.payup.app.ui.screens.history.HistoryActivity
import com.payup.app.ui.screens.home.HomeActivity
import com.payup.app.ui.screens.payment.ConfirmationActivity
import com.payup.app.ui.screens.payment.PaymentActivity
import com.payup.di.arch.ActivityComponent
import com.payup.di.arch.ActivityKey
import com.payup.di.components.activity.ConfirmationActivityComponent
import com.payup.di.components.activity.HistoryActivityComponent
import com.payup.di.components.activity.HomeActivityComponent
import com.payup.di.components.activity.PaymentActivityComponent
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
    abstract fun homeActivityComponent(builder: HomeActivityComponent.Builder): ActivityComponent.Builder<out Activity, *>

    @Binds
    @IntoMap
    @ActivityKey(PaymentActivity::class)
    abstract fun paymentActivityComponent(builder: PaymentActivityComponent.Builder): ActivityComponent.Builder<out Activity, *>

    @Binds
    @IntoMap
    @ActivityKey(ConfirmationActivity::class)
    abstract fun confirmationActivityComponent(builder: ConfirmationActivityComponent.Builder): ActivityComponent.Builder<out Activity, *>

    @Binds
    @IntoMap
    @ActivityKey(HistoryActivity::class)
    abstract fun historyActivityComponent(builder: HistoryActivityComponent.Builder): ActivityComponent.Builder<out Activity, *>
}
