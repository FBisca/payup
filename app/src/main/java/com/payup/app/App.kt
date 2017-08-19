package com.payup.app

import android.app.Application
import com.payup.di.ActivityInjectionFactory
import com.payup.di.DaggerApplicationComponent
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
