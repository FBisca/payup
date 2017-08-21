package com.payup.app.arch

import com.payup.di.injectionFactory.FragmentInjectionFactory
import dagger.Lazy
import javax.inject.Inject

abstract class ComponentFragmentActivity : ComponentActivity() {
    @Inject
    lateinit var fragmentInjectionFactory: Lazy<FragmentInjectionFactory>
}
