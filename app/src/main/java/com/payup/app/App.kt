package com.payup.app

import android.app.Application
import com.payup.di.arch.ActivityInjectionFactory
import com.payup.di.components.singleton.DaggerApplicationComponent
import com.payup.di.modules.ApplicationModule
import javax.inject.Inject

class App : Application() {

    @Inject
    lateinit var activityInjectionFactory: ActivityInjectionFactory

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
                .injectMembers(this)
    }
}
