package com.payup.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.payup.di.ActivityComponentBuilder

abstract class ComponentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInjection()
    }

    abstract fun initInjection()

    protected inline fun <reified T: ActivityComponentBuilder<*, *>> injectionBuilder(): T {
        val app = application as App
        val builder = app.activityInjectionFactory.activityComponentBuilder(javaClass)

        return when (builder) {
            is T -> builder
            else -> throw IllegalArgumentException("Could not cast Builder to ${javaClass.simpleName}")
        }
    }
}
