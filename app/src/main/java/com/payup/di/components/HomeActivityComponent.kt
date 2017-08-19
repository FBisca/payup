package com.payup.di.components

import com.payup.app.home.HomeActivity
import com.payup.app.home.HomeViewModel
import com.payup.di.ActivityComponent
import com.payup.di.ActivityComponentBuilder
import com.payup.di.ActivityModule
import com.payup.di.ActivityScope
import dagger.Module
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(HomeActivityModule::class))
interface HomeActivityComponent : ActivityComponent<HomeActivity> {
    fun viewModel(): HomeViewModel

    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<HomeActivityComponent, HomeActivityModule>
}

@Module
class HomeActivityModule(activity: HomeActivity) : ActivityModule(activity)
