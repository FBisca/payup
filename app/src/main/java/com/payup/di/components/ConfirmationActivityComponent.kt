package com.payup.di.components

import com.payup.app.ui.payment.ConfirmationActivity
import com.payup.di.ActivityComponent
import com.payup.di.ActivityComponentBuilder
import com.payup.di.ActivityModule
import com.payup.di.ActivityScope
import dagger.Module
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(ConfirmationActivityModule::class))
interface ConfirmationActivityComponent : ActivityComponent<ConfirmationActivity> {
    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<ConfirmationActivityComponent, ConfirmationActivityModule>
}

@Module
class ConfirmationActivityModule(activity: ConfirmationActivity) : ActivityModule(activity)
