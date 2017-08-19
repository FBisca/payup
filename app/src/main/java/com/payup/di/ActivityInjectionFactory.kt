package com.payup.di

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActivityInjectionFactory @Inject constructor(
        private val componentBuilders: Map<Class<*>, @JvmSuppressWildcards ActivityComponentBuilder<*, *>>
) {

    fun activityComponentBuilder(kClass: Class<*>): ActivityComponentBuilder<*, *> {
        return componentBuilders[kClass] ?:
                throw IllegalArgumentException("Could not find ComponentBuilder for class ${kClass.simpleName}")
    }
}
