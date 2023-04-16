package com.example.littlelivesassignment.adapter.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.littlelivesassignment.R
import com.example.littlelivesassignment.adapter.listener.AdapterActionListener
import com.example.littlelivesassignment.data.model.*
import com.example.littlelivesassignment.presentation.item.base.UserEventItem
import com.example.littlelivesassignment.presentation.item.header.SectionDateItem
import com.example.littlelivesassignment.presentation.item.news.*

class EventAdapter: PagingDataAdapter<UserEvent, EventAdapter.UserEventViewHolder>(
    EventsDiffCallback()
) {

    companion object {
        const val TYPE_HEADER_DATE      = 0
        const val TYPE_CHECK_IN_OUT     = 1
        const val TYPE_EVENT            = 2
        const val TYPE_MEDIA            = 3
        const val TYPE_STORY_EXPORTED   = 4
        const val TYPE_STORY_PUBLISHED  = 5
    }

    interface Callback :
        AdapterActionListener.OnRequestEvent,
        AdapterActionListener.OnRequestAttendanceRecord,
        AdapterActionListener.OnRequestStoryExported,
        AdapterActionListener.OnRequestStoryPublished,
        AdapterActionListener.OnRequestMedia

    var callback: Callback? = null

    override fun onBindViewHolder(holder: UserEventViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun getItemViewType(position: Int) = when (getItem(position)?.type?: "") {
        EventType.HEADER                        -> TYPE_HEADER_DATE
        EventType.CHECK_IN, EventType.CHECK_OUT -> TYPE_CHECK_IN_OUT
        EventType.CREATE                        -> TYPE_EVENT
        EventType.EXPORTED_STORY                -> TYPE_STORY_EXPORTED
        EventType.PUBLISHED_STORY               -> TYPE_STORY_PUBLISHED
        else                                    -> 1000
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserEventViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            TYPE_HEADER_DATE -> {
                SectionDateViewHolder(
                    inflater.inflate(R.layout.item_section_date, parent, false) as SectionDateItem
                )
            }
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
                throw IllegalArgumentException()
            }
        }
    }

    open inner class UserEventViewHolder(view: UserEventItem): ViewHolder(view) {
        open fun bind(data: UserEvent) {}
    }

    inner class SectionDateViewHolder(private val item: SectionDateItem): UserEventViewHolder(item) {
        override fun bind(data: UserEvent) {
            item.bind(data)
        }
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

            item.callback = object: UserEventItem.Callback {
                override fun onClickItem() {
                    when (data.type) {
                        EventType.CHECK_IN, EventType.CHECK_OUT -> {
                            callback?.onRequestAtdRecordDetail(data.snapshot as AttendanceRecord)
                        }
                        EventType.CREATE -> {
                            callback?.onRequestEventDetail(data.snapshot as ChildEvent)
                        }
                        EventType.PUBLISHED_STORY -> {
                            callback?.onRequestStoryPublishedDetail(data.snapshot as StoryPublishedEvent)
                        }
                        EventType.EXPORTED_STORY -> {
                            callback?.onRequestStoryExportedDetail(data.snapshot as StoryExportedEvent)
                        }
                    }
                }

                override fun onClickAction() {
                    when (data.type) {
                        EventType.CREATE -> {
                            callback?.onRequestCreateEvent(data.snapshot as ChildEvent)
                        }
                        EventType.EXPORTED_STORY -> {
                            callback?.onRequestDownloadStory(data.snapshot as StoryExportedEvent)
                        }
                    }
                }
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