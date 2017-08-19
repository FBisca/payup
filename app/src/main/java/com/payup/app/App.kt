package com.payup.app

import android.app.Application
import com.payup.di.injectionFactory.ActivityInjectionFactory
import com.payup.di.components.DaggerApplicationComponent
import javax.inject.Inject

class App : Application() {

    @Inject
    lateinit var activityInjectionFactory: ActivityInjectionFactory

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent.builder()
                .build()
                .injectMembers(this)
    }
}
