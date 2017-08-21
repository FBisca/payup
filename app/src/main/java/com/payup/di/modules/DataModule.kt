package com.payup.di.modules

import android.content.Context
import com.payup.data.manager.SchedulerManager
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import java.io.InputStream
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Module(includes = arrayOf(
        RepositoryModule::class,
        NetworkModule::class
))
class DataModule {
    @Provides
    fun providesMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    @Named("contactJsonFile")
    fun providesContactsJson(context: Context): InputStream {
        return context.resources.assets.open("json/contacts.json")
    }

    @Provides
    fun providesSchedulerManager() = SchedulerManager()
}
