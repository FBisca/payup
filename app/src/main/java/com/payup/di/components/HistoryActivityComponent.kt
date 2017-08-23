package com.payup.di.components

import com.payup.app.ui.screens.history.HistoryActivity
import com.payup.app.ui.screens.history.HistoryViewModel
import com.payup.di.ActivityComponent
import com.payup.di.ActivityComponentBuilder
import com.payup.di.ActivityModule
import com.payup.di.ActivityScope
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(HistoryActivityModule::class))
interface HistoryActivityComponent : ActivityComponent<HistoryActivity> {
    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<HistoryActivityComponent, HistoryActivityModule>
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
