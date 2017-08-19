package com.payup.di.injectionFactory

import android.support.v4.app.Fragment
import com.payup.di.ActivityScope
import com.payup.di.FragmentComponentBuilder
import javax.inject.Inject

@ActivityScope
class FragmentInjectionFactory @Inject constructor(
        private val componentBuilders: Map<Class<out Fragment>, @JvmSuppressWildcards FragmentComponentBuilder<*>>
) {

    fun builder(kClass: Class<*>): FragmentComponentBuilder<*> {
        return componentBuilders[kClass] ?:
                throw IllegalArgumentException("Could not find ComponentBuilder for class ${kClass.simpleName}")
    }
}
