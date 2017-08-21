package com.payup.app.ui.history

import android.os.Bundle
import com.payup.app.arch.ComponentActivity
import com.payup.di.components.HistoryActivityComponent
import com.payup.di.components.HistoryActivityModule

class HistoryActivity : ComponentActivity() {

    override fun initInjection(savedInstanceState: Bundle?) {
        injectionBuilder<HistoryActivityComponent.Builder>()
                .module(HistoryActivityModule(this))
                .build()
                .injectMembers(this)
    }
}
