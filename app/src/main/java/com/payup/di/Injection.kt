package com.payup.di

import android.app.Activity
import com.payup.app.Navigator
import dagger.MembersInjector
import dagger.Module
import dagger.Provides
import javax.inject.Scope

@Scope
annotation class ActivityScope

interface ActivityComponent<T : Activity> : MembersInjector<T> {
    fun navigator(): Navigator
}

interface ActivityComponentBuilder<out C : ActivityComponent<*>, in M: ActivityModule> {
    fun module(module: M): ActivityComponentBuilder<C, M>
    fun build(): C
}

@Module
abstract class ActivityModule(private val activity: Activity) {
    @Provides
    @ActivityScope
    open fun providesActivity(): Activity {
        return activity
    }
}
