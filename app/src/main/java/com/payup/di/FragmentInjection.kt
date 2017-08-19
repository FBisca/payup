package com.payup.di

import android.support.v4.app.Fragment
import dagger.MapKey
import dagger.MembersInjector
import javax.inject.Scope
import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class FragmentKey(val value: KClass<out Fragment>)

@Scope
annotation class FragmentScope

interface FragmentComponent<T : Fragment> : MembersInjector<T>

interface FragmentComponentBuilder<out C : FragmentComponent<*>> {
    fun build(): C
}
