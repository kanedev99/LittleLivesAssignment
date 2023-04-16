package com.example.littlelivesassignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.littlelivesassignment.R
import com.example.littlelivesassignment.data.model.EventType
import com.example.littlelivesassignment.data.model.UserEvent
import com.example.littlelivesassignment.databinding.ItemUserEventBinding
import com.example.littlelivesassignment.presentation.item.*

class EventAdapter: PagingDataAdapter<UserEvent, EventAdapter.UserEventViewHolder>(EventsDiffCallback()) {

    companion object {
        const val TYPE_HEADER_DATE      = 0
        const val TYPE_CHECK_IN_OUT     = 1
        const val TYPE_EVENT            = 2
        const val TYPE_MEDIA            = 3
        const val TYPE_STORY_EXPORTED   = 4
        const val TYPE_STORY_PUBLISHED  = 5
    }

    override fun onBindViewHolder(holder: UserEventViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun getItemViewType(position: Int) = when (getItem(position)?.type?: "") {
        EventType.CHECK_IN, EventType.CHECK_OUT -> TYPE_CHECK_IN_OUT
        EventType.CREATE                        -> TYPE_EVENT
        EventType.PORTFOLIO                     -> TYPE_MEDIA
        EventType.EXPORTED_STORY                -> TYPE_STORY_EXPORTED
        EventType.PUBLISHED_STORY               -> TYPE_STORY_PUBLISHED
        else                                    -> 1000
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserEventViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            /*0 -> {
                HeaderDateViewHolder(
                    ItemEventTitleSectionBinding.inflate(
                        inflater, parent, false
                    )
                )
            }*/
            TYPE_CHECK_IN_OUT -> {
                EventViewHolder(
                    inflater.inflate(R.layout.item_attendance_record, parent, false) as AttendanceRecordItem
                )
            }

            TYPE_EVENT -> {
                EventViewHolder(
                    inflater.inflate(R.layout.item_event, parent, false) as EventItem
                )
            }

            TYPE_MEDIA -> {
                EventViewHolder(
                    inflater.inflate(R.layout.item_attendance_record, parent, false) as AttendanceRecordItem
                )
            }

            TYPE_STORY_EXPORTED -> {
                EventViewHolder(
                    inflater.inflate(R.layout.item_story_exported, parent, false) as StoryExportedItem
                )
            }

            TYPE_STORY_PUBLISHED -> {
                EventViewHolder(
                    inflater.inflate(R.layout.item_story_published, parent, false) as StoryPublishedItem
                )
            }

            else -> {
                EventViewHolder(
                    inflater.inflate(R.layout.item_attendance_record, parent, false) as AttendanceRecordItem
                )
            }

            /*else -> {
                throw IllegalArgumentException()
            }*/
        }
    }

    open inner class UserEventViewHolder(view: UserEventItem): ViewHolder(view) {
        open fun bind(data: UserEvent) {}
    }

    inner class HeaderDateViewHolder(private val binding: ItemUserEventBinding): UserEventViewHolder(binding.root) {

        override fun bind(data: UserEvent) {}
    }

    inner class EventViewHolder(private val item: UserEventItem): UserEventViewHolder(item) {
        override fun bind(data: UserEvent) {
            when (data.type) {
                EventType.CHECK_IN, EventType.CHECK_OUT -> (item as AttendanceRecordItem).bind(data)
                EventType.CREATE                        -> (item as EventItem).bind(data)
                EventType.EXPORTED_STORY                -> (item as StoryExportedItem).bind(data)
                EventType.PUBLISHED_STORY               -> (item as StoryPublishedItem).bind(data)
                else -> {}
            }
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