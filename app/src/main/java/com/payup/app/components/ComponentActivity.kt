package com.payup.app.components

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.payup.app.App
import com.payup.di.ActivityComponentBuilder

abstract class ComponentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInjection(savedInstanceState)
    }

    abstract fun initInjection(savedInstanceState: Bundle?)

    protected inline fun <reified T : Fragment> findFragment(): T? {
        return supportFragmentManager.findFragmentByTag(T::class.java.simpleName) as? T
    }

    protected inline fun <reified T : ActivityComponentBuilder<*, *>> injectionBuilder(): T {
        val app = application as App
        val builder = app.activityInjectionFactory.builder(javaClass)

        return when (builder) {
            is T -> builder
            else -> throw IllegalArgumentException("Could not cast Builder to ${javaClass.simpleName}")
        }
    }
}
