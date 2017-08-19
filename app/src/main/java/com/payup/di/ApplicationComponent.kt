package com.payup.di

import com.payup.app.App
import com.payup.di.modules.ActivityBindModule
import dagger.Component
import dagger.MembersInjector
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ActivityBindModule::class))
interface ApplicationComponent : MembersInjector<App>
