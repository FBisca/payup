package com.payup.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
@Singleton
class ApplicationModule(private val application: Application) {

    @Provides
    fun providesContext(): Context {
        return application
    }
}
