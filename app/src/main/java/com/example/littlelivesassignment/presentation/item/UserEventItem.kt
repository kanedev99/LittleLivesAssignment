package com.example.littlelivesassignment.presentation.item

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.example.littlelivesassignment.data.model.UserEvent

open class UserEventItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    open fun bind(data: UserEvent) {}
}