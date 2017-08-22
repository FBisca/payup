package com.payup.app.ui.screens.history

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import com.payup.R
import com.payup.app.arch.ComponentActivity
import com.payup.app.ui.entities.HistoryListEntity
import com.payup.databinding.ActivityHistoryBinding
import com.payup.di.components.HistoryActivityComponent
import com.payup.di.components.HistoryActivityModule
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class HistoryActivity : ComponentActivity() {

    @Inject
    lateinit var viewModel: HistoryViewModel

    private lateinit var binding: ActivityHistoryBinding
    private val historyAdapter: HistoryAdapter = HistoryAdapter()
    private val disposable = CompositeDisposable()

    override fun initInjection(savedInstanceState: Bundle?) {
        injectionBuilder<HistoryActivityComponent.Builder>()
                .module(HistoryActivityModule(this))
                .build()
                .injectMembers(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_history)
        viewModel.viewCreated()

        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.viewModel = viewModel

        binding.historyList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = historyAdapter
            addItemDecoration(HistoryItemDecoration(context))
        }

        viewModel.viewState
                .subscribe(
                        {
                            when (it) {
                                is HistoryViewModel.ViewState.ListState -> showList(it.list)
                                HistoryViewModel.ViewState.Error -> showError()
                                HistoryViewModel.ViewState.Loading -> showLoading()
                            }

                        }
                )
                .apply { disposable.add(this) }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.viewDestroyed()
        disposable.dispose()
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onSupportNavigateUp()
        onBackPressed()
        return true
    }

    private fun showList(newList: List<HistoryListEntity>) {
        TransitionManager.beginDelayedTransition(binding.root as ViewGroup)
        binding.historyList.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
        binding.tryAgainButton.visibility = View.GONE

        if (newList.isEmpty()) {
            binding.iconError.setImageResource(R.drawable.ic_library_books_black_24dp)
            binding.messageText.setText(R.string.no_content)
            binding.iconError.visibility = View.VISIBLE
            binding.messageText.visibility = View.VISIBLE
        } else {
            binding.iconError.visibility = View.GONE
            binding.messageText.visibility = View.GONE

        }

        val oldList = historyAdapter.items
        historyAdapter.items = newList

        DiffUtil.calculateDiff(HistoryDiffCalback(oldList, newList))
                .dispatchUpdatesTo(historyAdapter)
    }

    private fun showError() {
        TransitionManager.beginDelayedTransition(binding.root as ViewGroup)
        binding.iconError.setImageResource(R.drawable.ic_signal_wifi_off_black_24dp)
        binding.messageText.setText(R.string.no_internet)
        binding.iconError.visibility = View.VISIBLE
        binding.messageText.visibility = View.VISIBLE
        binding.tryAgainButton.visibility = View.VISIBLE
        binding.historyList.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
    }

    private fun showLoading() {
        TransitionManager.beginDelayedTransition(binding.root as ViewGroup)
        binding.progressBar.visibility = View.VISIBLE
        binding.iconError.visibility = View.GONE
        binding.messageText.visibility = View.GONE
        binding.historyList.visibility = View.GONE
        binding.tryAgainButton.visibility = View.GONE
    }
}
