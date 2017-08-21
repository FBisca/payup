package com.payup.di

import android.app.Activity
import com.payup.app.Navigator
import dagger.*
import javax.inject.Scope
import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ActivityKey(val value: KClass<out Activity>)

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
    fun providesActivity(): Activity {
        return activity
    }
}
