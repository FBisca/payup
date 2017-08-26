package com.payup.di.arch

import android.app.Activity
import android.support.v4.app.Fragment
import dagger.MembersInjector
import dagger.Module
import dagger.Provides
import dagger.multibindings.Multibinds


interface AndroidComponent<T> : MembersInjector<T> {
    interface Builder<T> {
        fun build(): AndroidComponent<T>
    }
}

interface ActivityComponent<T : Activity> : AndroidComponent<T> {
    interface Builder<T : Activity, M : ActivityModule> : AndroidComponent.Builder<T> {
        fun module(module: M): Builder<T, M>
    }
}

interface FragmentComponent<T : Fragment> : AndroidComponent<T> {
    interface Builder<T : Fragment> : AndroidComponent.Builder<T>
}

@Module(includes = arrayOf(FragmentInjectorModule::class))
@ActivityScope
abstract class ActivityModule(private val activity: Activity) {
    @Provides
    fun providesActivity(): Activity = activity
}

@Module
@ActivityScope
abstract class FragmentInjectorModule {
    @Multibinds
    abstract fun fragmentBuilders(): Map<Class<out Fragment>, FragmentComponent.Builder<out Fragment>>
}
