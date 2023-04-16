package com.example.littlelivesassignment.presentation.item

import android.content.Context
import android.util.AttributeSet
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.littlelivesassignment.R
import com.example.littlelivesassignment.adapter.listener.ItemActionListener
import com.example.littlelivesassignment.data.model.UserEvent
import com.example.littlelivesassignment.databinding.ItemStoryPublishedBinding
import com.example.littlelivesassignment.utils.ext.layoutByTopLeft
import com.example.littlelivesassignment.utils.ext.layoutByTopRight
import com.example.littlelivesassignment.utils.ext.measureBy
import com.example.littlelivesassignment.utils.ext.visible
import java.lang.Integer.max

class StoryPublishedItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : UserEventItem(context, attrs, defStyleAttr) {

    private val eventIconSize   = context.resources.getDimensionPixelSize(R.dimen.event_icon_size)
    private val storyImageSize  = context.resources.getDimensionPixelSize(R.dimen.story_image_size)
    private val eventIconMargin = context.resources.getDimensionPixelSize(R.dimen.event_icon_margin)
    private val itemPadding     = context.resources.getDimensionPixelSize(R.dimen.event_item_padding)
    private val requestManager: RequestManager by lazy { Glide.with(this) }

    private val binding by lazy {
        ItemStoryPublishedBinding.bind(this)
    }

    interface Callback: ItemActionListener.OnClickItem

    var callback: Callback? = null

    companion object {
        private const val DEMO_IMG_URL = "https://images.unsplash.com/photo-1575936123452-b67c3203c357?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8aW1hZ2V8ZW58MHx8MHx"
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

        with(binding) {
            tvTitle.text = resources.getString(R.string.portfolio_event_title)

            tvDescription.text = data.description?: ""

            imvEventIcon.setImageResource(R.drawable.ic_event_portfolio)

            imvStory.apply {
                requestManager
                    .load(DEMO_IMG_URL)
                    .placeholder(R.drawable.placeholder)
                    .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(16)))
                    .into(this)
                visible()
            }
        }
    }

}