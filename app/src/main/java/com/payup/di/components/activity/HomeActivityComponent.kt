package com.payup.di.components.activity

import com.payup.app.ui.screens.home.HomeActivity
import com.payup.di.arch.ActivityComponent
import com.payup.di.arch.ActivityModule
import com.payup.di.arch.ActivityScope
import dagger.Module
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(HomeActivityModule::class))
interface HomeActivityComponent : ActivityComponent<HomeActivity> {
    @Subcomponent.Builder
    interface Builder : ActivityComponent.Builder<HomeActivity, HomeActivityModule>
}

@Module
class HomeActivityModule(activity: HomeActivity) : ActivityModule(activity)
