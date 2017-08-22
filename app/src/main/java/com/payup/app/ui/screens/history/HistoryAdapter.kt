package com.payup.app.ui.screens.history

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearLayoutManager.HORIZONTAL
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.payup.app.ui.entities.HistoryListEntity
import com.payup.databinding.ListGraphsBinding
import com.payup.databinding.ListTransactionEntryBinding

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private val VIEW_TYPE_GRAPH = 1
    private val VIEW_TYPE_TRANSACTION_ENTRY = 2

    var items : List<HistoryListEntity> = emptyList()

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_TRANSACTION_ENTRY -> {
                val binding = ListTransactionEntryBinding.inflate(layoutInflater, parent, false)
                ViewHolder.TransactionViewHolder(binding)
            }
            VIEW_TYPE_GRAPH -> {
                val binding = ListGraphsBinding.inflate(layoutInflater, parent, false)
                ViewHolder.GraphViewHolder(binding)
            }
            else -> TODO("Not Implemented yet")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bind(items[position])
    }

    override fun getItemViewType(position: Int): Int {
        return when(items[position]) {
            is HistoryListEntity.GraphList -> VIEW_TYPE_GRAPH
            is HistoryListEntity.TransactionEntry -> VIEW_TYPE_TRANSACTION_ENTRY
        }
    }

    sealed class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        abstract fun bind(entity: HistoryListEntity)

        class TransactionViewHolder(private val binding: ListTransactionEntryBinding) : ViewHolder(binding.root) {
            override fun bind(entity: HistoryListEntity) {
                val entry = entity as HistoryListEntity.TransactionEntry
                binding.transaction = entry.transaction
                binding.executePendingBindings()
            }
        }

        class GraphViewHolder(private val binding: ListGraphsBinding) : ViewHolder(binding.root) {
            override fun bind(entity: HistoryListEntity) {
                if (binding.listGraph.adapter == null) {
                    val entry = entity as HistoryListEntity.GraphList
                    val context = binding.root.context

                    binding.listGraph.layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
                    binding.listGraph.adapter = GraphAdapter().apply { items = entry.items }
                    binding.listGraph.addItemDecoration(GraphItemDecoration(context))
                }
            }
        }
    }
}
