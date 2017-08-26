package com.payup.di.components.activity

import com.payup.app.ui.screens.history.HistoryActivity
import com.payup.app.ui.screens.history.HistoryViewModel
import com.payup.di.arch.ActivityComponent
import com.payup.di.arch.ActivityModule
import com.payup.di.arch.ActivityScope
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(HistoryActivityModule::class))
interface HistoryActivityComponent : ActivityComponent<HistoryActivity> {
    @Subcomponent.Builder
    interface Builder : ActivityComponent.Builder<HistoryActivity, HistoryActivityModule>
}

@Module
@ActivityScope
class HistoryActivityModule(
        historyActivity: HistoryActivity,
        private val initialViewState: HistoryViewModel.ViewState
) : ActivityModule(historyActivity) {
    @Provides
    fun providesInitialState() = initialViewState
}
