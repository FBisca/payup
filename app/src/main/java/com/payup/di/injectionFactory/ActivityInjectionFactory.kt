package com.payup.di.injectionFactory

import android.app.Activity
import com.payup.di.ActivityComponentBuilder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActivityInjectionFactory @Inject constructor(
        private val componentBuilders: Map<Class<out Activity>, @JvmSuppressWildcards ActivityComponentBuilder<*, *>>
) {

    fun builder(kClass: Class<*>): ActivityComponentBuilder<*, *> {
        return componentBuilders[kClass] ?:
                throw IllegalArgumentException("Could not find ComponentBuilder for class ${kClass.simpleName}")
    }
}
