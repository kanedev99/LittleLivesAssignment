package com.example.littlelivesassignment.presentation.item.news

import android.content.Context
import android.util.AttributeSet
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.littlelivesassignment.R
import com.example.littlelivesassignment.data.model.AttendanceRecord
import com.example.littlelivesassignment.data.model.EventType
import com.example.littlelivesassignment.data.model.UserEvent
import com.example.littlelivesassignment.databinding.ItemAttendanceRecordBinding
import com.example.littlelivesassignment.presentation.item.base.UserEventItem
import com.example.littlelivesassignment.utils.ext.*
import java.lang.Integer.max

class AttendanceRecordItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : UserEventItem(context, attrs, defStyleAttr) {

    private val eventIconSize   = context.resources.getDimensionPixelSize(R.dimen.event_icon_size)
    private val storyImageSize  = context.resources.getDimensionPixelSize(R.dimen.story_image_size)
    private val eventIconMargin = context.resources.getDimensionPixelSize(R.dimen.event_icon_margin)
    private val itemPadding     = context.resources.getDimensionPixelSize(R.dimen.event_item_padding)
    private val requestManager: RequestManager by lazy { Glide.with(this) }

    private val binding by lazy {
        ItemAttendanceRecordBinding.bind(this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        this.setOnClickListener {
            callback?.onClickItem()
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
                width - eventIconSize - eventIconMargin - storyImageSize - itemPadding,
                MeasureSpec.AT_MOST,
                MeasureSpec.UNSPECIFIED,
                MeasureSpec.UNSPECIFIED
            )

            tvDescription.measureBy(
                width - eventIconSize - eventIconMargin - storyImageSize - itemPadding,
                MeasureSpec.AT_MOST,
                MeasureSpec.UNSPECIFIED,
                MeasureSpec.UNSPECIFIED
            )

            imvStory.measureBy(
                storyImageSize, MeasureSpec.EXACTLY,
                storyImageSize, MeasureSpec.EXACTLY
            )

            setMeasuredDimension(
                width, max(
                    tvTitle.measuredHeight + itemPadding + tvDescription.measuredHeight,
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
            imvStory        .layoutByTopRight(0, width)
        }
    }

    override fun bind(data: UserEvent) {
        super.bind(data)

        val atdRecordItem = (data.snapshot) as AttendanceRecord

        with(binding) {
            tvTitle.text = resources.getString(R.string.check_in_out_title)

            imvEventIcon.setImageResource(R.drawable.ic_event_check_in_out)

            tvDescription.text = resources.getString(
                if (data.type == EventType.CHECK_IN) R.string.user_check_in_description else R.string.user_check_out_description,
                atdRecordItem.msgParams.childName,
                atdRecordItem.msgParams.checkInDate.split(",")[1].uppercase()
            ).run {
                boldText(atdRecordItem.msgParams.childName)
            }

            imvStory.apply {
                requestManager
                    .load(atdRecordItem.checkinUrl)
                    .placeholder(R.drawable.placeholder)
                    .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(16)))
                    .into(this)
                visible()
            }
        }
    }

}