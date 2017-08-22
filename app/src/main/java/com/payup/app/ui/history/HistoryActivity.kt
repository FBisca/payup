package com.payup.app.ui.history

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import com.payup.R
import com.payup.app.arch.ComponentActivity
import com.payup.app.ui.history.list.HistoryListEntity
import com.payup.databinding.ActivityHistoryBinding
import com.payup.di.components.HistoryActivityComponent
import com.payup.di.components.HistoryActivityModule
import javax.inject.Inject

class HistoryActivity : ComponentActivity() {

    @Inject
    lateinit var viewModel: HistoryViewModel

    private lateinit var binding: ActivityHistoryBinding
    private val historyAdapter: HistoryAdapter = HistoryAdapter()

    override fun initInjection(savedInstanceState: Bundle?) {
        injectionBuilder<HistoryActivityComponent.Builder>()
                .module(HistoryActivityModule(this))
                .build()
                .injectMembers(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_history)
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.historyList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = historyAdapter
            addItemDecoration(HistoryItemDecoration(context))
        }

        viewModel.historyList()
                .subscribe(
                        {
                            val oldList = historyAdapter.items
                            historyAdapter.items = it

                            DiffUtil.calculateDiff(HistoryDiffCalback(oldList, it))
                                    .dispatchUpdatesTo(historyAdapter)
                        },
                        { }
                )
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onSupportNavigateUp()
        onBackPressed()
        return true
    }
}
