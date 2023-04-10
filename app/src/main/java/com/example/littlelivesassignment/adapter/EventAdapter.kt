package com.example.littlelivesassignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.littlelivesassignment.data.model.Event
import com.example.littlelivesassignment.databinding.ItemEventBinding

class EventAdapter: PagingDataAdapter<Event, EventAdapter.EventViewHolder>(EventsDiffCallback()) {

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(
            ItemEventBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    inner class EventViewHolder(private val binding: ItemEventBinding): ViewHolder(binding.root) {
        fun bind(item: Event) {
            binding.tvTitle.setText("${item.id} $ ${item.date}")
        }
    }

    private class EventsDiffCallback : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem == newItem
        }
    }


}