package com.payup.di.modules

import com.payup.app.home.HomeActivity
import com.payup.di.ActivityComponentBuilder
import com.payup.di.components.HomeComponent
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(subcomponents = arrayOf(HomeComponent::class))
abstract class ActivityBindModule {
    @Binds
    @IntoMap
    @ClassKey(HomeActivity::class)
    abstract fun bindsHomeActivityComponent(builder: HomeComponent.Builder): @JvmWildcard ActivityComponentBuilder<*, *>
}
