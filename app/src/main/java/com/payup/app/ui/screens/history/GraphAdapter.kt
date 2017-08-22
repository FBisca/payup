package com.payup.app.ui.screens.history

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.payup.databinding.ListGraphItemBinding
import com.payup.model.TransactionGraph

class GraphAdapter : RecyclerView.Adapter<GraphAdapter.ViewHolder>() {

    var items = emptyList<TransactionGraph>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListGraphItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bind(items[position])
    }

    override fun getItemCount() = items.size

    class ViewHolder(private val binding: ListGraphItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(transactionGraph: TransactionGraph) {
            binding.graph = transactionGraph
            binding.executePendingBindings()
        }
    }
}
