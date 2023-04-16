package com.example.littlelivesassignment.presentation.item.news

import android.content.Context
import android.util.AttributeSet
import com.example.littlelivesassignment.R
import com.example.littlelivesassignment.adapter.listener.ItemActionListener
import com.example.littlelivesassignment.data.model.ChildEvent
import com.example.littlelivesassignment.data.model.UserEvent
import com.example.littlelivesassignment.databinding.ItemEventBinding
import com.example.littlelivesassignment.presentation.item.base.UserEventItem
import com.example.littlelivesassignment.utils.ext.*
import java.lang.Integer.max

class EventItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : UserEventItem(context, attrs, defStyleAttr) {

    private val eventIconSize   = context.resources.getDimensionPixelSize(R.dimen.event_icon_size)
    private val storyImageSize  = context.resources.getDimensionPixelSize(R.dimen.story_image_size)
    private val eventIconMargin = context.resources.getDimensionPixelSize(R.dimen.event_icon_margin)
    private val itemPadding     = context.resources.getDimensionPixelSize(R.dimen.event_item_padding)

    private val binding by lazy {
        ItemEventBinding.bind(this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        this.setOnClickListener {
            callback?.onClickItem()
        }
        binding.btnAction.setOnClickListener {
            callback?.onClickAction()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width  = MeasureSpec.getSize(widthMeasureSpec)

        with(binding) {
            imvEventIcon.measureBy(
                eventIconSize, MeasureSpec.EXACTLY,
                eventIconSize, MeasureSpec.EXACTLY
            )

            tvTitle.measureBy(
                width - eventIconSize - eventIconMargin,
                MeasureSpec.AT_MOST,
                MeasureSpec.UNSPECIFIED,
                MeasureSpec.UNSPECIFIED
            )

            tvDescription.measureBy(
                width - eventIconSize - eventIconMargin,
                MeasureSpec.AT_MOST,
                MeasureSpec.UNSPECIFIED,
                MeasureSpec.UNSPECIFIED
            )

            btnAction.measureBy(
                MeasureSpec.UNSPECIFIED,
                MeasureSpec.UNSPECIFIED,
                MeasureSpec.UNSPECIFIED,
                MeasureSpec.UNSPECIFIED
            )

            setMeasuredDimension(
                width, max(
                    tvTitle.measuredHeight + itemPadding + tvDescription.measuredHeight + btnAction.measuredHeight + itemPadding,
                    storyImageSize
                ) + itemPadding
            )
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        with(binding) {
            imvEventIcon    .layoutByTopLeft(0, 0)
            tvTitle         .layoutByTopLeft((imvEventIcon.measuredHeight - tvTitle.measuredHeight) / 2, imvEventIcon.right + eventIconMargin)
            tvDescription   .layoutByTopLeft(tvTitle.bottom + itemPadding, tvTitle.left)
            btnAction       .layoutByTopLeft(tvDescription.bottom + itemPadding, tvTitle.left)
        }
    }

    override fun bind(data: UserEvent) {
        super.bind(data)

        val eventItem = (data.snapshot) as ChildEvent

        with(binding) {
            tvTitle.text = resources.getString(R.string.create_event_title)

            tvDescription.text = resources.getString(
                R.string.user_create_event_description,
                eventItem.childName,
                eventItem.eventTitle,
                eventItem.eventDate.toDate()
            ).run {
                boldText(eventItem.childName, eventItem.eventTitle)
            }

            imvEventIcon.setImageResource(R.drawable.ic_event)

            btnAction.apply {
                text = resources.getString(R.string.add)
                setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_calendar, 0)
                visible()
            }
        }
    }

}