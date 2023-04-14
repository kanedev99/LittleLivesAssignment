package com.example.littlelivesassignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.littlelivesassignment.data.model.UserEvent
import com.example.littlelivesassignment.databinding.ItemEventBinding

class EventAdapter: PagingDataAdapter<UserEvent, EventAdapter.EventViewHolder>(EventsDiffCallback()) {

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
        fun bind(item: UserEvent) {
            binding.root.bind(item)
        }
    }

    private class EventsDiffCallback : DiffUtil.ItemCallback<UserEvent>() {
        override fun areItemsTheSame(oldItem: UserEvent, newItem: UserEvent): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserEvent, newItem: UserEvent): Boolean {
            return oldItem == newItem
        }
    }


}