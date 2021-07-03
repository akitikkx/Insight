package com.ahmedtikiwa.insight.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ahmedtikiwa.insight.R
import com.ahmedtikiwa.insight.databinding.SearchResultItemBinding
import com.ahmedtikiwa.insight.domain.SearchItem

class SearchResultsAdapter(val listener: SearchResultsAdapterListener) :
    PagingDataAdapter<SearchItem, SearchResultsAdapter.ViewHolder>(
        SEARCH_ITEM_COMPARATOR
    ) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.also {
            it.result = getItem(position)
            it.listener = listener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val withDataBinding = SearchResultItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(withDataBinding)
    }

    class ViewHolder(val binding: SearchResultItemBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.search_result_item
        }
    }

    interface SearchResultsAdapterListener {
        fun onResultClick(view: View, imdbId: String)
    }

    companion object {
        private val SEARCH_ITEM_COMPARATOR = object : DiffUtil.ItemCallback<SearchItem>() {
            override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem) =
                oldItem.imdbID == newItem.imdbID

            override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem) =
                oldItem == newItem
        }
    }
}