package com.payup.di.modules

import android.app.Application
import android.content.Context
import com.payup.utils.locale
import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Singleton

@Module
@Singleton
class ApplicationModule(private val application: Application) {

    @Provides
    fun providesContext(): Context {
        return application
    }

    @Provides
    fun providesLocale(): Locale {
        return application.locale()
    }
}
