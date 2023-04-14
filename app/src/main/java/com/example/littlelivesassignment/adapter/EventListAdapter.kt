package com.example.littlelivesassignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.littlelivesassignment.data.model.UserEvent
import com.example.littlelivesassignment.databinding.ItemEventBinding

class EventListAdapter: ListAdapter<UserEvent, EventListAdapter.EventListViewHolder>(EventsDiffCallback()) {

    override fun onBindViewHolder(holder: EventListViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventListViewHolder {
        return EventListViewHolder(
            ItemEventBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    inner class EventListViewHolder(private val binding: ItemEventBinding): RecyclerView.ViewHolder(binding.root) {
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