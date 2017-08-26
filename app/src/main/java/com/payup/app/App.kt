package com.payup.app

import android.app.Application
import android.util.Log
import com.payup.di.arch.ActivityInjectionFactory
import com.payup.di.components.singleton.DaggerApplicationComponent
import com.payup.di.modules.ApplicationModule
import javax.inject.Inject
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import java.io.IOException
import java.net.SocketException


class App : Application() {

    @Inject
    lateinit var activityInjectionFactory: ActivityInjectionFactory

    override fun onCreate() {
        super.onCreate()
        initRxJava()
        DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
                .injectMembers(this)
    }

    private fun initRxJava() {
        RxJavaPlugins.setErrorHandler { e ->
            val shouldHandle = when (e) {
                is IOException,
                is SocketException,
                is InterruptedException -> false
                else -> true
            }

            if (shouldHandle) {
                val uncaughtExceptionHandler = Thread.currentThread().uncaughtExceptionHandler
                when (e) {
                    is NullPointerException,
                    is IllegalArgumentException,
                    is IllegalStateException -> uncaughtExceptionHandler.uncaughtException(Thread.currentThread(), e)
                    else -> {
                        val exception = when (e) {
                            is UndeliverableException -> e.cause
                            else -> e
                        }
                        Log.w(javaClass.simpleName, "Uncaught RxJava exception", exception)
                    }
                }
            }
        }
    }
}
