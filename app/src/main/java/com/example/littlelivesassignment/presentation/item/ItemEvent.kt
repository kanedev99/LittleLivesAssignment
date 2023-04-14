package com.example.littlelivesassignment.presentation.item

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.FrameLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.littlelivesassignment.R
import com.example.littlelivesassignment.data.model.AttendanceTrackingEvent
import com.example.littlelivesassignment.data.model.ChildEvent
import com.example.littlelivesassignment.data.model.EventType.ACTIVITY
import com.example.littlelivesassignment.data.model.EventType.CHECK_IN
import com.example.littlelivesassignment.data.model.EventType.CHECK_OUT
import com.example.littlelivesassignment.data.model.EventType.CREATE
import com.example.littlelivesassignment.data.model.EventType.EXPORTED_STORY
import com.example.littlelivesassignment.data.model.EventType.PORTFOLIO
import com.example.littlelivesassignment.data.model.EventType.PUBLISHED_STORY
import com.example.littlelivesassignment.data.model.StoryExportedEvent
import com.example.littlelivesassignment.data.model.UserEvent
import com.example.littlelivesassignment.databinding.ItemEventBinding
import com.example.littlelivesassignment.utils.ext.*
import java.lang.Integer.max

class ItemEvent @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val eventIconSize   = context.resources.getDimensionPixelSize(R.dimen.event_icon_size)
    private val storyImageSize  = context.resources.getDimensionPixelSize(R.dimen.story_image_size)
    private val eventIconMargin = context.resources.getDimensionPixelSize(R.dimen.event_icon_margin)
    private val itemPadding     = context.resources.getDimensionPixelSize(R.dimen.event_item_padding)
    private val requestManager = Glide.with(this)

    private val binding by lazy {
        ItemEventBinding.bind(this)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width  = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)

        with(binding) {
            imvEventIcon.measureBy(
                eventIconSize, MeasureSpec.EXACTLY,
                eventIconSize, MeasureSpec.EXACTLY
            )

            tvTitle.measureBy(
                if (imvStory.isVisible)
                    width - eventIconSize - eventIconMargin - storyImageSize - itemPadding
                else
                    width - eventIconSize - eventIconMargin,
                MeasureSpec.AT_MOST,
                MeasureSpec.UNSPECIFIED,
                MeasureSpec.UNSPECIFIED
            )

            tvDescription.measureBy(
                if (imvStory.isVisible)
                    width - eventIconSize - eventIconMargin - storyImageSize - itemPadding
                else
                    width - eventIconSize - eventIconMargin,
                MeasureSpec.AT_MOST,
                MeasureSpec.UNSPECIFIED,
                MeasureSpec.UNSPECIFIED
            )

            imvStory.measureBy(
                storyImageSize, MeasureSpec.EXACTLY,
                storyImageSize, MeasureSpec.EXACTLY
            )

            if (btnAction.isVisible) {
                btnAction.measureBy(
                    MeasureSpec.UNSPECIFIED,
                    MeasureSpec.UNSPECIFIED,
                    MeasureSpec.UNSPECIFIED,
                    MeasureSpec.UNSPECIFIED
                )
            }

            setMeasuredDimension(
                width, max(
                    tvTitle.measuredHeight + itemPadding + tvDescription.measuredHeight + if (btnAction.isVisible) btnAction.measuredHeight + itemPadding else 0,
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
            if (btnAction.isVisible) {
                btnAction.layoutByTopLeft(tvDescription.bottom + itemPadding, tvTitle.left)
            }
        }
    }

    fun bind(item: UserEvent) {
        with(binding) {
            tvTitle.text = resources.getString(when (item.type) {
                CREATE, ACTIVITY                            -> R.string.create_event_title
                EXPORTED_STORY, PUBLISHED_STORY, PORTFOLIO  -> R.string.portfolio_event_title
                CHECK_IN, CHECK_OUT                         -> R.string.check_in_out_title
                else                                        -> R.string.create_event_title
            })

            imvEventIcon.setImageResource(
                when (item.type) {
                    CREATE                                      -> R.drawable.ic_event
                    EXPORTED_STORY, PUBLISHED_STORY, PORTFOLIO  -> R.drawable.ic_event_portfolio
                    ACTIVITY                                    -> R.drawable.ic_event
                    CHECK_IN, CHECK_OUT                         -> R.drawable.ic_event_check_in_out
                    else                                        -> R.drawable.ic_placeholder
                }
            )

            item.apply {
                tvDescription.text = buildDescription(this)
                loadStoryImage(this)
                bindActionButton(this)
            }
        }
    }

    private fun buildDescription(item: UserEvent): CharSequence {
        return when(item.type) {
            CHECK_IN -> resources.getString(
                R.string.user_check_in_description,
                (item.snapshot as AttendanceTrackingEvent).msgParams.childName,
                item.snapshot.msgParams.checkInDate.split(",")[1].uppercase()
            ).run {
                boldText(item.snapshot.msgParams.childName)
            }

            CHECK_OUT -> resources.getString(
                R.string.user_check_out_description,
                (item.snapshot as AttendanceTrackingEvent).msgParams.childName,
                item.snapshot.msgParams.checkInDate.split(",")[1].uppercase()
            ).run {
                boldText(item.snapshot.msgParams.childName)
            }

            CREATE -> resources.getString(
                R.string.user_create_event_description,
                (item.snapshot as ChildEvent).childName,
                item.snapshot.eventTitle,
                item.snapshot.eventDate.toDate()
            ).run {
                boldText(item.snapshot.childName, item.snapshot.eventTitle)
            }

            EXPORTED_STORY -> item.description + " " + (item.snapshot as StoryExportedEvent).url.split("/").last()

            PUBLISHED_STORY -> item.description?: ""

            else -> ""
        }
    }

    private fun loadStoryImage(item: UserEvent) {
        with(binding.imvStory) {
            when(item.type) {
                CHECK_IN, CHECK_OUT -> {
                    requestManager
                        .load((item.snapshot as AttendanceTrackingEvent).checkinUrl)
                        .placeholder(R.drawable.placeholder)
                        .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(16)))
                        .into(this)
                    visible()
                    Log.d("Kane", "loadStoryImage: ${item.snapshot.checkinUrl}")
                }

                PUBLISHED_STORY -> {
                    requestManager
                        .load("https://images.unsplash.com/photo-1575936123452-b67c3203c357?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8aW1hZ2V8ZW58MHx8MHx")
                        .placeholder(R.drawable.placeholder)
                        .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(16)))
                        .into(this)
                    visible()
                }

                else -> {
                    gone()
                }
            }
        }
    }

    private fun bindActionButton(item: UserEvent) {
        with(binding.btnAction) {
            when (item.type) {
                EXPORTED_STORY -> {
                    text = resources.getString(R.string.download)
                    setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_download, 0)
                    visible()
                }
                CREATE -> {
                    text = resources.getString(R.string.add)
                    setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_calendar, 0)
                    visible()
                }
                else -> {
                    gone()
                }
            }
        }
    }
}