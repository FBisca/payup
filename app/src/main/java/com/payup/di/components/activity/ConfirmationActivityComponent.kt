package com.payup.di.components.activity

import com.payup.app.ui.screens.payment.ConfirmationActivity
import com.payup.di.arch.ActivityComponent
import com.payup.di.arch.ActivityModule
import com.payup.di.arch.ActivityScope
import com.payup.model.Contact
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(ConfirmationActivityModule::class))
interface ConfirmationActivityComponent : ActivityComponent<ConfirmationActivity> {
    @Subcomponent.Builder
    interface Builder : ActivityComponent.Builder<ConfirmationActivity, ConfirmationActivityModule>
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
