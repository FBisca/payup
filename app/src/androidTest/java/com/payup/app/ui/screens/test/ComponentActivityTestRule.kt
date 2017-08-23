package com.payup.app.ui.screens.test

import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import com.payup.app.App
import com.payup.app.arch.ComponentActivity
import com.payup.di.ActivityComponent
import com.payup.di.ActivityComponentBuilder
import com.payup.di.ActivityModule
import com.payup.di.injectionFactory.ActivityInjectionFactory
import com.payup.test.anyNonNull
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import kotlin.reflect.KClass

open class ComponentActivityTestRule<T : ComponentActivity, out C: ActivityComponent<T>, out B : ActivityComponentBuilder<C, M>, in M: ActivityModule>
constructor(
        private val activityClass: KClass<T>,
        private val builderClass: KClass<B>,
        private val componentClass: KClass<C>,
        private val onInjectMembersCalled: (T) -> Unit
) : ActivityTestRule<T>(activityClass.java, true, false) {

    override fun beforeActivityLaunched() {
        val app = InstrumentationRegistry.getTargetContext().applicationContext as App
        app.activityInjectionFactory = ActivityInjectionFactory(mapOf(activityClass.java to componentBuilder()))
    }

    open fun componentBuilder(): B {
        val component = component()
        val builderMock = mock(builderClass.java)

        `when`(builderMock.module(anyNonNull())).thenReturn(builderMock)
        `when`(builderMock.build()).thenReturn(component)

        return builderMock
    }

    open fun component(): C {
        val componentMock = mock(componentClass.java)

        `when`(componentMock.injectMembers(anyNonNull())).thenAnswer { invocation ->
            val activity: T = invocation.getArgument(0)
            onInjectMembersCalled(activity)
        }

        return componentMock
    }

}
