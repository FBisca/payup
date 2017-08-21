package com.payup.di.components

import com.payup.app.ui.payment.ConfirmationActivity
import com.payup.di.ActivityComponent
import com.payup.di.ActivityComponentBuilder
import com.payup.di.ActivityModule
import com.payup.di.ActivityScope
import com.payup.model.Contact
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(ConfirmationActivityModule::class))
interface ConfirmationActivityComponent : ActivityComponent<ConfirmationActivity> {
    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<ConfirmationActivityComponent, ConfirmationActivityModule>
}

@Module
@ActivityScope
class ConfirmationActivityModule(
        activity: ConfirmationActivity,
        val contact: Contact,
        val value: Double
) : ActivityModule(activity) {

    @Provides
    fun providesContact() = contact

    @Provides
    fun providesValue() = value
}
