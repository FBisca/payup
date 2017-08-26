package com.payup.di.arch

import android.app.Activity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActivityInjectionFactory @Inject constructor(
        private val componentBuilders: Map<Class<out Activity>, @JvmSuppressWildcards ActivityComponent.Builder<out Activity, *>>
) {

    fun builder(kClass: Class<*>): ActivityComponent.Builder<out Activity, *> {
        return componentBuilders[kClass] ?:
                throw IllegalArgumentException("Could not find Subcomponent.Builder for class ${kClass.simpleName}")
    }
}
