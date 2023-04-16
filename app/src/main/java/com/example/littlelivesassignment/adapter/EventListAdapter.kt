package com.example.littlelivesassignment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.littlelivesassignment.adapter.listener.AdapterActionListener
import com.example.littlelivesassignment.data.model.*
import com.example.littlelivesassignment.databinding.ItemEventBinding
import com.example.littlelivesassignment.databinding.ItemEventTitleSectionBinding
import com.example.littlelivesassignment.utils.ext.toTitleDate

class EventListAdapter
    : RecyclerView.Adapter<EventListAdapter.UserEventViewHolder>() {

    private var arrayType = arrayListOf<Int>()
    private var arrayData = arrayListOf<Any>()

    interface Callback :
        AdapterActionListener.OnRequestEvent,
        AdapterActionListener.OnRequestAttendanceRecord,
        AdapterActionListener.OnRequestStoryExported,
        AdapterActionListener.OnRequestStoryPublished,
        AdapterActionListener.OnRequestMedia

    var callback: Callback? = null

    companion object {
        const val TITLE_SECTION_TYPE    = 1
        const val ITEM_TYPE             = 2
    }

    fun buildDataMap(userEvents: List<UserEvent>) {
        arrayData.clear()
        arrayType.clear()

        val eventsByDate = HashMap<String, MutableList<UserEvent>>()

        for (event in userEvents) {
            val dateString = event.date?.substring(0, 10)?: ""
            if (!eventsByDate.containsKey(dateString)) {
                eventsByDate[dateString] = mutableListOf()
                arrayData.add(dateString)
                arrayType.add(TITLE_SECTION_TYPE)
            }
            eventsByDate[dateString]?.add(event)
            arrayData.add(event)
            arrayType.add(ITEM_TYPE)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserEventViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            TITLE_SECTION_TYPE -> {
                EventTitleSectionViewHolder(
                    ItemEventTitleSectionBinding.inflate(
                        inflater, parent, false
                    )
                )
            }
            ITEM_TYPE -> {
                EventListViewHolder(
                    ItemEventBinding.inflate(
                        inflater, parent, false
                    )
                )
            }
            else -> {
                throw IllegalArgumentException()
            }
        }
    }

    override fun onBindViewHolder(holder: UserEventViewHolder, position: Int) {
        holder.bind(arrayData[position])
    }

    override fun getItemViewType(position: Int) = arrayType[position]

    override fun getItemCount() = arrayData.size

    open inner class UserEventViewHolder(view: View): RecyclerView.ViewHolder(view) {
        open fun bind(item: Any) {}
    }

    inner class EventListViewHolder(private val binding: ItemEventBinding): UserEventViewHolder(binding.root) {
        override fun bind(item: Any) {
            /*binding.root.callback = object: ItemEvent.Callback {
                override fun onClickItem() {
                    when ((item as UserEvent).type) {
                        EventType.CHECK_IN, EventType.CHECK_OUT -> {
                            callback?.onRequestAttendanceRecord(item.snapshot as AttendanceRecord)
                        }
                        EventType.CREATE -> {
                            callback?.onRequestCreateEvent(item.snapshot as ChildEvent)
                        }
                        EventType.PORTFOLIO -> {
                            callback?.onRequestMedia(item.snapshot as PortfolioEvent)
                        }
                        EventType.PUBLISHED_STORY -> {
                            callback?.onRequestStoryPublished(item.snapshot as StoryPublishedEvent)
                        }
                        EventType.EXPORTED_STORY -> {
                            callback?.onRequestStoryExported(item.snapshot as StoryExportedEvent)
                        }
                    }
                }
            }
            binding.root.bind(item as UserEvent)*/
        }
    }

    inner class EventTitleSectionViewHolder(private val binding: ItemEventTitleSectionBinding): UserEventViewHolder(binding.root) {
        override fun bind(item: Any) {
            binding.root.apply {
                text    = (item as String).toTitleDate()
                alpha   = 0.5f
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