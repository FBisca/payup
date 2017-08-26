package com.payup.di.components.singleton

import com.payup.app.App
import com.payup.di.modules.ActivityBindModule
import com.payup.di.modules.ApplicationModule
import com.payup.di.modules.DataModule
import dagger.Component
import dagger.MembersInjector
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        ApplicationModule::class,
        DataModule::class,
        ActivityBindModule::class
))
interface ApplicationComponent : MembersInjector<App>
