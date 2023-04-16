package com.example.littlelivesassignment.presentation.item.header

import android.content.Context
import android.util.AttributeSet
import com.example.littlelivesassignment.data.model.UserEvent
import com.example.littlelivesassignment.databinding.ItemSectionDateBinding
import com.example.littlelivesassignment.presentation.item.base.UserEventItem
import com.example.littlelivesassignment.utils.ext.toTitleDate

class SectionDateItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : UserEventItem(context, attrs, defStyleAttr) {

    private val binding by lazy {
        ItemSectionDateBinding.bind(this)
    }

    override fun bind(data: UserEvent) {
        binding.tvTitle.apply {
            text    = data.date?.toTitleDate() ?: ""
            alpha   = 0.5f
        }
    }
}