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
import com.payup.app.ui.screens.history.HistoryViewModel.ViewState.ListState
import com.payup.databinding.ActivityHistoryBinding
import com.payup.di.components.activity.HistoryActivityModule
import com.payup.model.Transaction
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import javax.inject.Inject

class HistoryActivity : ComponentActivity<HistoryActivityModule>() {

    companion object {
        const val SAVED_STATE = "state"
        const val SAVED_LIST = "list"
    }

    @Inject
    lateinit var viewModel: HistoryViewModel

    private val historyAdapter: HistoryAdapter = HistoryAdapter()
    private val disposable = CompositeDisposable()

    private lateinit var binding: ActivityHistoryBinding

    override fun instantiateModule(savedInstanceState: Bundle?): HistoryActivityModule {
        return HistoryActivityModule(this, restoreState(savedInstanceState))
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
                                is ListState -> showList(it.list)
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val currentState = viewModel.viewState.value
        when {
            currentState is ListState && currentState.list.isNotEmpty() -> {
                outState.putInt(SAVED_STATE, 1)
                outState.putParcelableArrayList(SAVED_LIST, ArrayList<Transaction>(currentState.list))
            }
            else -> outState.putInt(SAVED_STATE, 0)
        }
    }

    private fun restoreState(savedInstanceState: Bundle?): HistoryViewModel.ViewState {
        val state = savedInstanceState?.getInt(SAVED_STATE) ?: 0
        val list = savedInstanceState?.getParcelableArrayList<Transaction>(SAVED_LIST)

        return when {
            state == 1 && list != null -> HistoryViewModel.ViewState.ListState(list)
            else -> HistoryViewModel.ViewState.Loading
        }
    }

    private fun showList(newList: List<Transaction>) {
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

        val convertedList = HistoryListEntity.convertToListEntity(newList)
        val oldList = historyAdapter.items
        historyAdapter.items = convertedList

        DiffUtil.calculateDiff(HistoryDiffCalback(oldList, convertedList))
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
