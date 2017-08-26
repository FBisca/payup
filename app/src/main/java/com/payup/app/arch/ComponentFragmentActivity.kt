package com.payup.app.arch

import com.payup.di.arch.ActivityModule
import com.payup.di.arch.FragmentInjectionFactory
import dagger.Lazy
import javax.inject.Inject

abstract class ComponentFragmentActivity<out M : ActivityModule> : ComponentActivity<M>() {
}
