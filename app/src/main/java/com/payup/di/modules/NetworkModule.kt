package com.payup.di.modules

import com.payup.BuildConfig
import com.payup.data.network.NetworkApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@Singleton
class NetworkModule {

    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val interceptor = when {
            BuildConfig.DEBUG -> HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            else -> HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
    }

    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl("http://processoseletivoneon.azurewebsites.net/")
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
    }

    @Provides
    fun providesNetworkApi(retrofit: Retrofit): NetworkApi {
        return retrofit.create(NetworkApi::class.java)
    }
}
