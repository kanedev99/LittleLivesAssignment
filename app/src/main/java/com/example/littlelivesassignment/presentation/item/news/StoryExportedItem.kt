package com.example.littlelivesassignment.presentation.item.news

import android.content.Context
import android.util.AttributeSet
import com.example.littlelivesassignment.R
import com.example.littlelivesassignment.adapter.listener.ItemActionListener
import com.example.littlelivesassignment.data.model.StoryExportedEvent
import com.example.littlelivesassignment.data.model.UserEvent
import com.example.littlelivesassignment.databinding.ItemStoryExportedBinding
import com.example.littlelivesassignment.presentation.item.base.UserEventItem
import com.example.littlelivesassignment.utils.ext.layoutByTopLeft
import com.example.littlelivesassignment.utils.ext.measureBy
import com.example.littlelivesassignment.utils.ext.visible
import java.lang.Integer.max

class StoryExportedItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : UserEventItem(context, attrs, defStyleAttr) {

    private val eventIconSize   = context.resources.getDimensionPixelSize(R.dimen.event_icon_size)
    private val storyImageSize  = context.resources.getDimensionPixelSize(R.dimen.story_image_size)
    private val eventIconMargin = context.resources.getDimensionPixelSize(R.dimen.event_icon_margin)
    private val itemPadding     = context.resources.getDimensionPixelSize(R.dimen.event_item_padding)

    private val binding by lazy {
        ItemStoryExportedBinding.bind(this)
    }

    interface Callback: ItemActionListener.OnClickItem

    var callback: Callback? = null

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

        val storyExportedItem = (data.snapshot) as StoryExportedEvent

        with(binding) {
            tvTitle.text = resources.getString(R.string.portfolio_event_title)

            tvDescription.text = resources.getString(
                R.string.story_exported_event_description,
                data.description,
                storyExportedItem.url.split("/").last()
            )

            imvEventIcon.setImageResource(R.drawable.ic_event_portfolio)

            btnAction.apply {
                text = resources.getString(R.string.download)
                setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_download, 0)
                visible()
            }
        }
    }

}